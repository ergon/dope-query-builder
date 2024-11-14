package ch.ergon.dope.clauses

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.integrationTest.BaseIntegrationTest
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.idField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.resetDatabase
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.testBucket
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.fromable.useKeys
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DeleteIntegrationTest : BaseIntegrationTest() {
    @AfterTest
    fun reset() {
        resetDatabase()
    }

    @Test
    fun `delete single document and return id field`() {
        val dopeQuery = QueryBuilder()
            .deleteFrom(
                testBucket.useKeys("employee:1"),
            )
            .returning(
                idField,
            ).build()

        val actual = queryWithoutParameters(dopeQuery)
        val actualQueryResult = actual.rows[0].contentAs<Map<String, Number>>()

        assertEquals(mapOf("id" to 1), actualQueryResult)
    }

    @Test
    fun `delete every document in bucket`() {
        val deleteEverythingDopeQuery = QueryBuilder()
            .deleteFrom(
                testBucket,
            )
            .where(
                1.add(1).isEqualTo(2),
            ).returning(
                idField,
            ).build()

        val selectEverythingDopeQuery = QueryBuilder()
            .selectFrom(
                testBucket,
            ).build()

        val selectBeforeDeleteQueryResult = queryWithoutParameters(selectEverythingDopeQuery)
        val deleteQueryResult = queryWithoutParameters(deleteEverythingDopeQuery)
        val selectAfterDeleteQueryResult = queryWithoutParameters(selectEverythingDopeQuery)

        assertEquals(15, selectBeforeDeleteQueryResult.rows.size)
        assertEquals(15, deleteQueryResult.rows.size)
        assertEquals(0, selectAfterDeleteQueryResult.rows.size)
    }
}
