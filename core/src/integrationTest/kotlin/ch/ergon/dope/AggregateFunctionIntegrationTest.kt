package ch.ergon.dope

import ch.ergon.dope.helper.BaseIntegrationTest
import ch.ergon.dope.resolvable.expression.alias
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.max
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.min
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.sum
import kotlin.test.Test
import kotlin.test.assertEquals

class AggregateFunctionIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `select aggregate function`() {
        val dopeQuery = QueryBuilder()
            .select(
                min(orderNumberField).alias("min"),
                max(nameField).alias("max"),
                sum(idField).alias("sum"),
            ).from(
                testBucket,
            ).build()

        val actual = queryWithoutParameters(dopeQuery)
        val actualRow = actual.rows[0].contentAs<Map<String, Any>>()

        tryUntil { assertEquals(1, actual.rows.size) }
        tryUntil { assertEquals("order1", actualRow["min"]) }
        tryUntil { assertEquals("employee5", actualRow["max"]) }
        tryUntil { assertEquals(45, actualRow["sum"]) }
    }
}
