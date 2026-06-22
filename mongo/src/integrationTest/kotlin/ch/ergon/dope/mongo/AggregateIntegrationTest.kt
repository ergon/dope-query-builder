package ch.ergon.dope.mongo

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.mongo.integrationTest.BaseIntegrationTest
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.amount
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.orders
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.role
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.users
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.avg
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.countAsterisk
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.max
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.min
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.sum
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.variance
import ch.ergon.dope.resolvable.expression.rowscope.alias
import kotlin.test.Test
import kotlin.test.assertEquals

class AggregateIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `count all documents`() {
        val query = QueryBuilder.select(countAsterisk().alias("total")).from(users).buildMongo(resolver)
        val result = executeQuery(query)
        assertEquals(1, result.size)
        assertEquals(3, result[0]["total"])
    }

    @Test
    fun `sum of a numeric field`() {
        val query = QueryBuilder.select(sum(amount).alias("total")).from(orders).buildMongo(resolver)
        val result = executeQuery(query)
        assertEquals(2000, result[0]["total"])
    }

    @Test
    fun `average of a numeric field`() {
        val query = QueryBuilder.select(avg(amount).alias("average")).from(orders).buildMongo(resolver)
        val result = executeQuery(query)
        assertEquals(1000.0, result[0]["average"])
    }

    @Test
    fun `minimum of a numeric field`() {
        val query = QueryBuilder.select(min(amount).alias("minimum")).from(orders).buildMongo(resolver)
        val result = executeQuery(query)
        assertEquals(800, result[0]["minimum"])
    }

    @Test
    fun `maximum of a numeric field`() {
        val query = QueryBuilder.select(max(amount).alias("maximum")).from(orders).buildMongo(resolver)
        val result = executeQuery(query)
        assertEquals(1200, result[0]["maximum"])
    }

    @Test
    fun `sample variance of a numeric field`() {
        val query = QueryBuilder.select(variance(amount).alias("variance")).from(orders).buildMongo(resolver)
        val result = executeQuery(query)
        assertEquals(80000.0, result[0]["variance"])
    }

    @Test
    fun `count grouped by a field`() {
        val query = QueryBuilder
            .select(role, countAsterisk().alias("count"))
            .from(users)
            .groupBy(role)
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(2, result.size)
        val countsByRole = result.associate { it["role"] to it["count"] }
        assertEquals(1, countsByRole["admin"])
        assertEquals(2, countsByRole["user"])
    }
}
