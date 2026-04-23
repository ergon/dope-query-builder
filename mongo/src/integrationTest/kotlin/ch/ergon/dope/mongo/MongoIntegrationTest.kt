package ch.ergon.dope.mongo

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.mongo.integrationTest.BaseIntegrationTest
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.age
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.city
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.email
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.id
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.name
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.orders
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.role
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.status
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.userId
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.users
import ch.ergon.dope.resolvable.clause.model.OrderType
import ch.ergon.dope.resolvable.clause.model.toNewValue
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.assignTo
import ch.ergon.dope.resolvable.expression.type.logic.and
import ch.ergon.dope.resolvable.expression.type.logic.or
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.relational.isLike
import ch.ergon.dope.resolvable.expression.type.relational.isNotEqualTo
import ch.ergon.dope.resolvable.expression.type.relational.isNotNull
import ch.ergon.dope.resolvable.expression.type.relational.isNull
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class MongoIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `select all users`() {
        val query = QueryBuilder
            .select(name, age)
            .from(users)
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(3, result.size)
    }

    @Test
    fun `select with aliased field`() {
        val query = QueryBuilder
            .select(name.alias("alias"), age)
            .from(users)
            .where(age.isEqualTo(25))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(1, result.size)
        assertEquals("Alice Brown", result[0]["alias"])
    }

    @Test
    fun `select raw single field`() {
        val query = QueryBuilder
            .selectRaw(name)
            .from(users)
            .where(age.isEqualTo(30))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(1, result.size)
        assertEquals("John Smith", result[0]["name"])
    }

    @Test
    fun `select distinct roles`() {
        val query = QueryBuilder
            .selectDistinct(role)
            .from(users)
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(2, result.size)
        assertEquals(setOf("admin", "user"), result.map { it["role"] }.toSet())
    }

    @Test
    fun `select distinct city and role`() {
        val query = QueryBuilder
            .selectDistinct(city, role)
            .from(users)
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(3, result.size)
    }

    @Test
    fun `select with where equals`() {
        val query = QueryBuilder
            .select(name, age)
            .from(users)
            .where(age.isEqualTo(25))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(1, result.size)
        assertEquals("Alice Brown", result[0]["name"])
    }

    @Test
    fun `select with where or`() {
        val query = QueryBuilder
            .select(name, age)
            .from(users)
            .where(age.isEqualTo(25).or(name.isEqualTo("Mike Taylor")))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(2, result.size)
        assertEquals(setOf("Alice Brown", "Mike Taylor"), result.map { it["name"] }.toSet())
    }

    @Test
    fun `select with where and`() {
        val query = QueryBuilder
            .select(name)
            .from(users)
            .where(role.isEqualTo("user").and(city.isEqualTo("Berlin")))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(1, result.size)
        assertEquals("Alice Brown", result[0]["name"])
    }

    @Test
    fun `select with where not equals`() {
        val query = QueryBuilder
            .select(name, age)
            .from(users)
            .where(age.isNotEqualTo(25))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(2, result.size)
        assertEquals(setOf("John Smith", "Mike Taylor"), result.map { it["name"] }.toSet())
    }

    @Test
    fun `select with where like`() {
        val query = QueryBuilder
            .select(name, email)
            .from(users)
            .where(email.isLike(".*@example\\.com"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(2, result.size)
        assertEquals(setOf("alice@example.com", "mike@example.com"), result.map { it["email"] }.toSet())
    }

    @Test
    fun `select with where is null`() {
        val query = QueryBuilder
            .select(status)
            .from(orders)
            .where(status.isNull())
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(1, result.size)
        assertNull(result[0]["status"])
    }

    @Test
    fun `select with where is not null`() {
        val query = QueryBuilder
            .select(status)
            .from(orders)
            .where(status.isNotNull())
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(1, result.size)
        assertEquals("shipped", result[0]["status"])
    }

    @Test
    fun `select with simple join`() {
        val query = QueryBuilder
            .select(id, name, age)
            .from(users)
            .join(orders, condition = id.isEqualTo(userId))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(2, result.size)
        assertEquals(setOf("Alice Brown", "Mike Taylor"), result.map { it["name"] }.toSet())
    }

    @Test
    fun `select with pipeline join using compound condition`() {
        val query = QueryBuilder
            .select(id, name, age)
            .from(users)
            .join(orders, condition = id.isEqualTo(userId).and(name.isEqualTo("Alice Brown")))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(1, result.size)
        assertEquals("Alice Brown", result[0]["name"])
    }

    @Test
    fun `select with order by ascending`() {
        val query = QueryBuilder
            .select(name, age)
            .from(users)
            .orderBy(age, OrderType.ASC)
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(listOf("Alice Brown", "John Smith", "Mike Taylor"), result.map { it["name"] })
    }

    @Test
    fun `select with order by descending`() {
        val query = QueryBuilder
            .select(name, age)
            .from(users)
            .orderBy(age, OrderType.DESC)
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(listOf("Mike Taylor", "John Smith", "Alice Brown"), result.map { it["name"] })
    }

    @Test
    fun `select with limit`() {
        val query = QueryBuilder
            .select(name, age)
            .from(users)
            .orderBy(age, OrderType.ASC)
            .limit(2)
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(2, result.size)
        assertEquals("Alice Brown", result[0]["name"])
    }

    @Test
    fun `select with limit and offset`() {
        val query = QueryBuilder
            .select(name, age)
            .from(users)
            .orderBy(age, OrderType.ASC)
            .limit(2)
            .offset(1)
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(1, result.size)
        assertEquals("John Smith", result[0]["name"])
    }

    @Test
    fun `select with group by single field`() {
        val query = QueryBuilder
            .select(role)
            .from(users)
            .groupBy(role)
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(2, result.size)
        assertEquals(setOf("admin", "user"), result.map { it["_id"] }.toSet())
    }

    @Test
    fun `select with group by multiple fields`() {
        val query = QueryBuilder
            .select(role, city)
            .from(users)
            .groupBy(role, city)
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(3, result.size)
    }

    @Test
    fun `select with let variable for a field`() {
        val query = QueryBuilder
            .select(name, age)
            .from(users)
            .withVariables("var".assignTo(age))
            .where(age.isEqualTo(25))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(1, result.size)
        assertEquals("Alice Brown", result[0]["name"])
        assertEquals(25, result[0]["var"])
    }

    @Test
    fun `select with let variable for a value`() {
        val query = QueryBuilder
            .select(name, age)
            .from(users)
            .withVariables("var".assignTo("test"))
            .where(age.isEqualTo(25))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(1, result.size)
        assertEquals("test", result[0]["var"])
    }

    @Test
    fun `update single field with where`() {
        val query = QueryBuilder
            .update(users)
            .set(city.toNewValue("London"))
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        assertEquals(1, executeUpdate(query).modifiedCount)

        val result = executeQuery(
            QueryBuilder
                .select(city)
                .from(users)
                .where(name.isEqualTo("John Smith"))
                .buildMongo(resolver),
        )
        assertEquals("London", result[0]["city"])
        TestMongoDatabase.resetDatabase()
    }

    @Test
    fun `update multiple fields`() {
        val query = QueryBuilder
            .update(users)
            .set(city.toNewValue("London"), role.toNewValue("manager"))
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        executeUpdate(query)

        val result = executeQuery(
            QueryBuilder
                .select(city, role)
                .from(users)
                .where(name.isEqualTo("John Smith"))
                .buildMongo(resolver),
        )
        assertEquals("London", result[0]["city"])
        assertEquals("manager", result[0]["role"])
        TestMongoDatabase.resetDatabase()
    }

    @Test
    fun `update without where updates all documents`() {
        val query = QueryBuilder
            .update(users)
            .set(role.toNewValue("guest"))
            .buildMongo(resolver)

        assertEquals(3, executeUpdate(query).matchedCount)

        TestMongoDatabase.resetDatabase()
    }

    @Test
    fun `unset field`() {
        val query = QueryBuilder
            .update(users)
            .set(role.toNewValue("placeholder"))
            .unset(city)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        executeUpdate(query)

        val result = executeQuery(
            QueryBuilder
                .select(name, city, role)
                .from(users)
                .where(name.isEqualTo("John Smith"))
                .buildMongo(resolver),
        )
        assertEquals("placeholder", result[0]["role"])
        assertNull(result[0]["city"])
        TestMongoDatabase.resetDatabase()
    }

    @Test
    fun `delete with where`() {
        val query = QueryBuilder
            .deleteFrom(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        assertEquals(1, executeDelete(query).deletedCount)

        val remaining = executeQuery(
            QueryBuilder
                .select(name)
                .from(users)
                .buildMongo(resolver),
        )
        assertEquals(2, remaining.size)
        TestMongoDatabase.resetDatabase()
    }

    @Test
    fun `delete all matching documents`() {
        val query = QueryBuilder
            .deleteFrom(users)
            .where(role.isEqualTo("user"))
            .buildMongo(resolver)

        assertEquals(2, executeDelete(query).deletedCount)

        val remaining = executeQuery(
            QueryBuilder
                .select(name)
                .from(users)
                .buildMongo(resolver),
        )
        assertEquals(1, remaining.size)
        TestMongoDatabase.resetDatabase()
    }
}
