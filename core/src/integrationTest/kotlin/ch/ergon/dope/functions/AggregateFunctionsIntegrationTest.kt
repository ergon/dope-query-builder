package ch.ergon.dope.functions

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.integrationTest.BaseIntegrationTest
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.idField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.nameField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.orderNumberField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.testBucket
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.typeField
import ch.ergon.dope.integrationTest.tryUntil
import ch.ergon.dope.resolvable.expression.alias
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.DISTINCT
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.arrayAggregate
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.countAsterisk
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.max
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.min
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.sum
import kotlin.test.Test
import kotlin.test.assertEquals

class AggregateFunctionsIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `select aggregate functions`() {
        val dopeQuery = QueryBuilder()
            .select(
                min(orderNumberField).alias("min"),
                max(nameField).alias("max"),
                sum(idField).alias("sum"),
                arrayAggregate(typeField, DISTINCT).alias("arrayAggregate"),
                countAsterisk().alias("count"),
            ).from(
                testBucket,
            ).build()

        tryUntil {
            val actual = queryWithoutParameters(dopeQuery)
            val actualRow = actual.rows[0].contentAs<Map<String, Any>>()

            assertEquals(1, actual.rows.size)
            assertEquals("order1", actualRow["min"])
            assertEquals("employee5", actualRow["max"])
            assertEquals(45, actualRow["sum"])
            assertEquals(listOf("client", "employee", "order"), actualRow["arrayAggregate"])
            assertEquals(15, actualRow["count"])
        }
    }
}
