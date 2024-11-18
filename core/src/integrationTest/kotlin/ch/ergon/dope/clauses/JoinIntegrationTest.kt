package ch.ergon.dope.clauses

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.integrationTest.BaseIntegrationTest
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.testBucket
import ch.ergon.dope.integrationTest.toMapValues
import ch.ergon.dope.integrationTest.tryUntil
import ch.ergon.dope.resolvable.clause.model.joinHint.HashOrNestedLoopHint.NESTED_LOOP
import ch.ergon.dope.resolvable.clause.model.joinHint.indexHint
import ch.ergon.dope.resolvable.expression.alias
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.expression.unaliased.type.meta.meta
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.validtype.StringType
import kotlin.test.Test
import kotlin.test.assertEquals

class JoinIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `join all orders on employee id`() {
        val employeeAlias = testBucket.alias("e")
        val orderAlias = testBucket.alias("o")
        val employeeNameField = Field<StringType>("name", employeeAlias.alias)
        val orderNumberField = Field<StringType>("orderNumber", orderAlias.alias)
        val orderEmployeeIdField = Field<StringType>("employee", orderAlias.alias)
        val dopeQuery = QueryBuilder()
            .select(
                employeeNameField,
                orderNumberField,
            )
            .from(
                employeeAlias,
            )
            .join(
                orderAlias,
                orderEmployeeIdField.isEqualTo(meta(employeeAlias).id),
            )
            .build()

        tryUntil {
            val queryResult = queryWithoutParameters(dopeQuery)
            val result = queryResult.toMapValues()

            assertEquals(5, queryResult.rows.size)
            assertEquals("employee1", result["name"])
            assertEquals("order1", result["orderNumber"])
        }
    }

    @Test
    fun `join documents with multiple joins`() {
        val employeeAlias = testBucket.alias("e")
        val clientAlias = testBucket.alias("c")
        val orderAlias = testBucket.alias("o")
        val employeeNameField = Field<StringType>("name", employeeAlias.alias)
        val clientNameField = Field<StringType>("name", clientAlias.alias)
        val orderNumberField = Field<StringType>("orderNumber", orderAlias.alias)
        val orderEmployeeIdField = Field<StringType>("employee", orderAlias.alias)
        val orderClientIdField = Field<StringType>("client", orderAlias.alias)
        val dopeQuery = QueryBuilder()
            .select(
                orderNumberField,
                employeeNameField.alias("employeeName"),
                clientNameField.alias("clientName"),
            )
            .from(
                orderAlias,
            )
            .join(
                employeeAlias,
                orderEmployeeIdField.isEqualTo(meta(employeeAlias).id),
                hashOrNestedLoopHint = NESTED_LOOP,
            ).innerJoin(
                clientAlias,
                orderClientIdField.isEqualTo(meta(clientAlias).id),
                keysOrIndexHint = indexHint(),
            )
            .build()

        tryUntil {
            val queryResult = queryWithoutParameters(dopeQuery)
            val result = queryResult.toMapValues()

            assertEquals(5, queryResult.rows.size)
            assertEquals("order1", result["orderNumber"])
            assertEquals("employee1", result["employeeName"])
            assertEquals("client1", result["clientName"])
        }
    }
}
