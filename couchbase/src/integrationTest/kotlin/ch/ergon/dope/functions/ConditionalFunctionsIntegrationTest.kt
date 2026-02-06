package ch.ergon.dope.functions

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.integrationTest.BaseIntegrationTest
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.deliveryDateField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.orderNumberField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.testKeyspace
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.typeField
import ch.ergon.dope.integrationTest.toMapValues
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.function.conditional.decode
import ch.ergon.dope.resolvable.expression.type.function.conditional.ifMissingOrNull
import ch.ergon.dope.resolvable.expression.type.function.conditional.nvl
import ch.ergon.dope.resolvable.expression.type.function.conditional.nvl2
import ch.ergon.dope.resolvable.expression.type.function.conditional.resultsIn
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType
import kotlin.test.Test
import kotlin.test.assertEquals

class ConditionalFunctionsIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `decode the type field`() {
        val dopeQuery = QueryBuilder
            .select(
                decode(
                    typeField,
                    "client".resultsIn(1),
                    default = 0.toDopeType(),
                ).alias("decoded"),
            )
            .from(
                testKeyspace,
            )
            .where(
                typeField.isEqualTo("client"),
            ).build(CouchbaseResolver())

        val queryResult = queryWithoutParameters(dopeQuery)
        val result = queryResult.toMapValues()

        assertEquals(1, result["decoded"])
    }

    @Test
    fun `return first non null, non missing value`() {
        val missingField = Field<StringType>("nonexistent", testKeyspace)
        val dopeQuery = QueryBuilder
            .select(
                ifMissingOrNull(
                    deliveryDateField,
                    missingField,
                    "value".toDopeType(),
                ).alias("conditional"),
            )
            .from(
                testKeyspace,
            ).build(CouchbaseResolver())

        val queryResult = queryWithoutParameters(dopeQuery)
        val result = queryResult.toMapValues()

        assertEquals("value", result["conditional"])
    }

    @Test
    fun `return null value logic substitute values`() {
        val dopeQuery = QueryBuilder
            .select(
                nvl(
                    deliveryDateField,
                    "noDeliveryDate",
                ).alias("nvl"),
                nvl2(
                    orderNumberField,
                    "exists",
                    "doesn't exist",
                ).alias("nvl2"),
            )
            .from(
                testKeyspace,
            )
            .where(
                typeField.isEqualTo("order"),
            ).build(CouchbaseResolver())

        val queryResult = queryWithoutParameters(dopeQuery)
        val result = queryResult.toMapValues()

        assertEquals("noDeliveryDate", result["nvl"])
        assertEquals("exists", result["nvl2"])
    }
}
