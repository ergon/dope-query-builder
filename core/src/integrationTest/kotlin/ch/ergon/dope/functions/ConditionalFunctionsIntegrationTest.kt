package ch.ergon.dope.functions

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.integrationTest.BaseIntegrationTest
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.deliveryDateField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.orderNumberField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.testBucket
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.typeField
import ch.ergon.dope.integrationTest.toMapValues
import ch.ergon.dope.resolvable.expression.single.type.Field
import ch.ergon.dope.resolvable.expression.single.type.alias
import ch.ergon.dope.resolvable.expression.single.type.function.conditional.decode
import ch.ergon.dope.resolvable.expression.single.type.function.conditional.ifMissingOrNull
import ch.ergon.dope.resolvable.expression.single.type.function.conditional.nvl
import ch.ergon.dope.resolvable.expression.single.type.function.conditional.nvl2
import ch.ergon.dope.resolvable.expression.single.type.function.conditional.resultsIn
import ch.ergon.dope.resolvable.expression.single.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.StringType
import kotlin.test.Test
import kotlin.test.assertEquals

class ConditionalFunctionsIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `decode the type field`() {
        val dopeQuery = QueryBuilder()
            .select(
                decode(
                    typeField,
                    "client".resultsIn(1),
                    default = 0.toDopeType(),
                ).alias("decoded"),
            )
            .from(
                testBucket,
            )
            .where(
                typeField.isEqualTo("client"),
            ).build()

        val queryResult = queryWithoutParameters(dopeQuery)
        val result = queryResult.toMapValues()

        assertEquals(1, result["decoded"])
    }

    @Test
    fun `return first non null, non missing value`() {
        val missingField = Field<StringType>("nonexistent", testBucket.name)
        val dopeQuery = QueryBuilder()
            .select(
                ifMissingOrNull(
                    deliveryDateField,
                    missingField,
                    "value".toDopeType(),
                ).alias("conditional"),
            )
            .from(
                testBucket,
            ).build()

        val queryResult = queryWithoutParameters(dopeQuery)
        val result = queryResult.toMapValues()

        assertEquals("value", result["conditional"])
    }

    @Test
    fun `return null value logic substitute values`() {
        val dopeQuery = QueryBuilder()
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
                testBucket,
            )
            .where(
                typeField.isEqualTo("order"),
            ).build()

        val queryResult = queryWithoutParameters(dopeQuery)
        val result = queryResult.toMapValues()

        assertEquals("noDeliveryDate", result["nvl"])
        assertEquals("exists", result["nvl2"])
    }
}
