package ch.ergon.dope.clauses

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.integrationTest.BaseIntegrationTest
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.nameField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.resetDatabase
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.testBucket
import ch.ergon.dope.integrationTest.toMapValues
import ch.ergon.dope.integrationTest.tryUntil
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.fromable.useKeys
import ch.ergon.dope.validtype.StringType
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class UpdateIntegrationTest : BaseIntegrationTest() {
    @AfterTest
    fun reset() {
        resetDatabase()
    }

    @Test
    fun `update to set and unset single attribute`() {
        val newField = Field<StringType>("newField", testBucket.name)
        val dopeQuery = QueryBuilder()
            .update(
                testBucket.useKeys("client:1"),
            )
            .set(
                newField,
                "newName",
            )
            .unset(
                nameField,
            )
            .returning(
                newField,
                nameField,
            )
            .build()

        tryUntil {
            val queryResult = queryWithoutParameters(dopeQuery)
            val result = queryResult.toMapValues()

            assertEquals("newName", result["newField"])
            assertNull(result["nameField"])
        }
    }
}
