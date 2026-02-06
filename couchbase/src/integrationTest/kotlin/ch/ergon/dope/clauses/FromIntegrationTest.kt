package ch.ergon.dope.clauses

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.couchbase.resolvable.expression.type.meta
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.integrationTest.BaseIntegrationTest
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.testAppOrderAuditKeyspace
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.testKeyspace
import ch.ergon.dope.integrationTest.toMapValues
import ch.ergon.dope.integrationTest.tryUntil
import ch.ergon.dope.resolvable.clause.joinHint.HashOrNestedLoopHint.NESTED_LOOP
import ch.ergon.dope.resolvable.clause.joinHint.indexHint
import ch.ergon.dope.resolvable.clause.model.OrderType.ASC
import ch.ergon.dope.resolvable.clause.model.OrderType.DESC
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.logic.and
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
        val employeeAlias = testKeyspace.alias("e")
        val orderAlias = testKeyspace.alias("o")
        val employeeNameField = Field<StringType>("name", employeeAlias)
        val orderNumberField = Field<StringType>("orderNumber", orderAlias)
        val orderEmployeeIdField = Field<StringType>("employee", orderAlias)
        val dopeQuery = QueryBuilder
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
            ).build(CouchbaseResolver())

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
    fun `join default orders with order_audit collection`() {
        val orderAlias = testKeyspace.scope("_default").collection("_default").alias("o")
        val orderNumberField = Field<StringType>("orderNumber", orderAlias)

        val auditAlias = testAppOrderAuditKeyspace.alias("a")
        val auditOrderRefField = Field<StringType>("orderRef", auditAlias)
        val auditStatusField = Field<StringType>("status", auditAlias)

        val dopeQuery = QueryBuilder
            .select(
                orderNumberField,
                auditStatusField,
            )
            .from(orderAlias)
            .join(
                auditAlias,
                auditOrderRefField.isEqualTo(meta(orderAlias).id),
            )
            .orderBy(orderNumberField, ASC)
            .build(CouchbaseResolver())

        tryUntil {
            val queryResult = queryWithoutParameters(dopeQuery)

            assertEquals(5, queryResult.rows.size)

            assertEquals(mapOf("orderNumber" to "order1", "status" to "CREATED"), queryResult.toMapValues(rowNumber = 0))
            assertEquals(mapOf("orderNumber" to "order2", "status" to "CREATED"), queryResult.toMapValues(rowNumber = 1))
            assertEquals(mapOf("orderNumber" to "order3", "status" to "CREATED"), queryResult.toMapValues(rowNumber = 2))
            assertEquals(mapOf("orderNumber" to "order4", "status" to "CREATED"), queryResult.toMapValues(rowNumber = 3))
            assertEquals(mapOf("orderNumber" to "order5", "status" to "CREATED"), queryResult.toMapValues(rowNumber = 4))
        }
    }

    @Test
    fun `join documents with multiple joins`() {
        val employeeAlias = testKeyspace.alias("e")
        val clientAlias = testKeyspace.alias("c")
        val orderAlias = testKeyspace.alias("o")
        val employeeNameField = Field<StringType>("name", employeeAlias)
        val clientNameField = Field<StringType>("name", clientAlias)
        val orderNumberField = Field<StringType>("orderNumber", orderAlias)
        val orderEmployeeIdField = Field<StringType>("employee", orderAlias)
        val orderClientIdField = Field<StringType>("client", orderAlias)
        val dopeQuery = QueryBuilder
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
            .build(CouchbaseResolver())

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
        val e = testKeyspace.alias("e")
        val eIdField = Field<NumberType>("id", e)
        val eTypeField = Field<StringType>("type", e)
        val eIsActiveField = Field<BooleanType>("isActive", e)
        val c = testKeyspace.alias("c")
        val cIdField = Field<NumberType>("id", c)
        val cTypeField = Field<StringType>("type", c)
        val cIsActiveField = Field<BooleanType>("isActive", c)
        val o = testKeyspace.alias("o")
        val oClientField = Field<StringType>("client", o)
        val oTypeField = Field<StringType>("type", o)
        val oOrderNumberField = Field<NumberType>("orderNumber", o)
        val oQuantitiesField = Field<ArrayType<NumberType>>("quantities", o)
        val nestedOrders = testKeyspace.alias("nestedOrders")
        val nestedOrdersEmployeeField = Field<StringType>("employee", nestedOrders)
        val nestedOrdersTypeField = Field<StringType>("type", nestedOrders)

        val dopeQuery = QueryBuilder
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
                condition = eIdField.isEqualTo(cIdField).and(eTypeField.isEqualTo("employee")).and(cTypeField.isEqualTo("client")),
            )
            .join(
                o,
                condition = meta(c).id.isEqualTo(oClientField).and(oTypeField.isEqualTo("order")),
            )
            .nest(
                nestedOrders,
                condition = meta(e).id.isEqualTo(nestedOrdersEmployeeField).and(nestedOrdersTypeField.isEqualTo("order")),
            )
            .unnest(
                oQuantitiesField.alias("unnestedQuantities"),
            )
            .where(
                eIsActiveField.and(cIsActiveField),
            ).build(CouchbaseResolver())

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
