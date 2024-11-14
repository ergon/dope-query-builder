package ch.ergon.dope.functions

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.integrationTest.BaseIntegrationTest
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.deliveryDateField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.orderNumberField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.testBucket
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.typeField
import ch.ergon.dope.resolvable.expression.alias
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.decode
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.ifMissingOrNull
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.nvl
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.nvl2
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.resultsIn
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.StringType
import kotlin.test.Test
import kotlin.test.assertContentEquals
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
                ),
            ).from(
                testBucket,
            ).where(
                typeField.isEqualTo("client"),
            ).build()

        val actual = queryWithoutParameters(dopeQuery)

        assertContentEquals("{\"$1\":1}".toByteArray(), actual.rows[0].content)
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
                ),
            ).from(
                testBucket,
            ).build()

        val actual = queryWithoutParameters(dopeQuery)

        assertContentEquals("{\"$1\":\"value\"}".toByteArray(), actual.rows[0].content)
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
            ).from(
                testBucket,
            ).where(
                typeField.isEqualTo("order"),
            ).build()

        val actual = queryWithoutParameters(dopeQuery)
        val actualQueryRow = actual.rows[0].contentAs<Map<String, String>>()

        assertEquals("noDeliveryDate", actualQueryRow["nvl"])
        assertEquals("exists", actualQueryRow["nvl2"])
    }
}
