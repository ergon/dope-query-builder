package ch.ergon.dope.functions

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.integrationTest.BaseIntegrationTest
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.idField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.nameField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.orderNumberField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.testBucket
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.typeField
import ch.ergon.dope.integrationTest.toMapValues
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
            val queryResult = queryWithoutParameters(dopeQuery)
            val result = queryResult.toMapValues()

            assertEquals(1, queryResult.rows.size)
            assertEquals("order1", result["min"])
            assertEquals("employee5", result["max"])
            assertEquals(45, result["sum"])
            assertEquals(listOf("client", "employee", "order"), result["arrayAggregate"])
            assertEquals(15, result["count"])
        }
    }
}
