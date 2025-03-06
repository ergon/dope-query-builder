package ch.ergon.dope.clauses

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.integrationTest.BaseIntegrationTest
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.testBucket
import ch.ergon.dope.integrationTest.toMapValues
import ch.ergon.dope.integrationTest.tryUntil
import ch.ergon.dope.resolvable.clause.model.OrderType.ASC
import ch.ergon.dope.resolvable.clause.model.OrderType.DESC
import ch.ergon.dope.resolvable.clause.model.joinHint.HashOrNestedLoopHint.NESTED_LOOP
import ch.ergon.dope.resolvable.clause.model.joinHint.indexHint
import ch.ergon.dope.resolvable.expression.alias
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.expression.unaliased.type.meta.meta
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint.NESTED_LOOP
import ch.ergon.dope.resolvable.clause.joinHint.indexHint
import ch.ergon.dope.resolvable.clause.model.OrderByType.ASC
import ch.ergon.dope.resolvable.clause.model.OrderByType.DESC
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.meta
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
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
            ).orderBy(
                orderNumberField,
                ASC,
            ).build()

        tryUntil {
            val queryResult = queryWithoutParameters(dopeQuery)

            assertEquals(5, queryResult.rows.size)
            assertEquals(mapOf("name" to "employee1", "orderNumber" to "order1"), queryResult.toMapValues(rowNumber = 0))
            assertEquals(mapOf("name" to "employee2", "orderNumber" to "order2"), queryResult.toMapValues(rowNumber = 1))
            assertEquals(mapOf("name" to "employee3", "orderNumber" to "order3"), queryResult.toMapValues(rowNumber = 2))
            assertEquals(mapOf("name" to "employee4", "orderNumber" to "order4"), queryResult.toMapValues(rowNumber = 3))
            assertEquals(mapOf("name" to "employee5", "orderNumber" to "order5"), queryResult.toMapValues(rowNumber = 4))
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
            )
            .innerJoin(
                clientAlias,
                orderClientIdField.isEqualTo(meta(clientAlias).id),
                keysOrIndexHint = indexHint(),
            ).orderBy(
                orderNumberField,
                DESC,
            )
            .build()

        tryUntil {
            val queryResult = queryWithoutParameters(dopeQuery)

            assertEquals(5, queryResult.rows.size)
            assertEquals(
                mapOf("employeeName" to "employee5", "orderNumber" to "order5", "clientName" to "client5"),
                queryResult.toMapValues(rowNumber = 0),
            )
            assertEquals(
                mapOf("employeeName" to "employee4", "orderNumber" to "order4", "clientName" to "client4"),
                queryResult.toMapValues(rowNumber = 1),
            )
            assertEquals(
                mapOf("employeeName" to "employee3", "orderNumber" to "order3", "clientName" to "client3"),
                queryResult.toMapValues(rowNumber = 2),
            )
            assertEquals(
                mapOf("employeeName" to "employee2", "orderNumber" to "order2", "clientName" to "client2"),
                queryResult.toMapValues(rowNumber = 3),
            )
            assertEquals(
                mapOf("employeeName" to "employee1", "orderNumber" to "order1", "clientName" to "client1"),
                queryResult.toMapValues(rowNumber = 4),
            )
        }
    }
}
