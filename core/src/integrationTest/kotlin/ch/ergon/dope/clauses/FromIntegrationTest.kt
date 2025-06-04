package ch.ergon.dope.clauses

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.integrationTest.BaseIntegrationTest
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.testBucket
import ch.ergon.dope.integrationTest.toMapValues
import ch.ergon.dope.integrationTest.tryUntil
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint.NESTED_LOOP
import ch.ergon.dope.resolvable.clause.joinHint.indexHint
import ch.ergon.dope.resolvable.clause.model.OrderType.ASC
import ch.ergon.dope.resolvable.clause.model.OrderType.DESC
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.logic.and
import ch.ergon.dope.resolvable.expression.type.meta
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import kotlin.test.Test
import kotlin.test.assertEquals

class FromIntegrationTest : BaseIntegrationTest() {
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

    @Test
    fun `use multiple from terms`() {
        val e = testBucket.alias("e")
        val eIdField = Field<NumberType>("id", e.alias)
        val eTypeField = Field<StringType>("type", e.alias)
        val eIsActiveField = Field<BooleanType>("isActive", e.alias)
        val c = testBucket.alias("c")
        val cIdField = Field<NumberType>("id", c.alias)
        val cTypeField = Field<StringType>("type", c.alias)
        val cIsActiveField = Field<BooleanType>("isActive", c.alias)
        val o = testBucket.alias("o")
        val oClientField = Field<StringType>("client", o.alias)
        val oTypeField = Field<StringType>("type", o.alias)
        val oOrderNumberField = Field<NumberType>("orderNumber", o.alias)
        val oQuantitiesField = Field<ArrayType<NumberType>>("quantities", o.alias)
        val nestedOrders = testBucket.alias("nestedOrders")
        val nestedOrdersEmployeeField = Field<StringType>("employee", nestedOrders.alias)
        val nestedOrdersTypeField = Field<StringType>("type", nestedOrders.alias)

        val dopeQuery = QueryBuilder()
            .selectDistinct(
                eIdField.alias("employeeId"),
                cIdField.alias("clientId"),
                oOrderNumberField.alias("orderNumber"),
            )
            .from(
                e,
            )
            .join(
                c,
                onCondition = eIdField.isEqualTo(cIdField).and(eTypeField.isEqualTo("employee")).and(cTypeField.isEqualTo("client")),
            )
            .join(
                o,
                onCondition = meta(c).id.isEqualTo(oClientField).and(oTypeField.isEqualTo("order")),
            )
            .nest(
                nestedOrders,
                onCondition = meta(e).id.isEqualTo(nestedOrdersEmployeeField).and(nestedOrdersTypeField.isEqualTo("order")),
            )
            .unnest(
                oQuantitiesField.alias("unnestedQuantities"),
            )
            .where(
                eIsActiveField.and(cIsActiveField),
            ).build()

        tryUntil {
            val queryResult = queryWithoutParameters(dopeQuery)

            assertEquals(2, queryResult.rows.size)
            assertEquals(
                mapOf(
                    "employeeId" to 2,
                    "clientId" to 2,
                    "orderNumber" to "order2",
                ),
                queryResult.toMapValues(rowNumber = 0),
            )
            assertEquals(
                mapOf(
                    "employeeId" to 4,
                    "clientId" to 4,
                    "orderNumber" to "order4",
                ),
                queryResult.toMapValues(rowNumber = 1),
            )
        }
    }
}
