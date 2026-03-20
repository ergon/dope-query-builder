package ch.ergon.dope.mongo

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.mongo.integrationTest.BaseIntegrationTest
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
            .build(resolver)

        val result = executeQuery(query)

        assertEquals(3, result.size)
    }

    @Test
    fun `select with aliased field`() {
        val query = QueryBuilder
            .select(name.alias("alias"), age)
            .from(users)
            .where(age.isEqualTo(25))
            .build(resolver)

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
            .build(resolver)

        val result = executeQuery(query)

        assertEquals(1, result.size)
        assertEquals("John Smith", result[0]["name"])
    }

    @Test
    fun `select distinct roles`() {
        val query = QueryBuilder
            .selectDistinct(role)
            .from(users)
            .build(resolver)

        val result = executeQuery(query)

        assertEquals(2, result.size)
        val roles = result.map { it["role"] }.toSet()
        assertEquals(setOf("admin", "user"), roles)
    }

    @Test
    fun `select distinct city and role`() {
        val query = QueryBuilder
            .selectDistinct(city, role)
            .from(users)
            .build(resolver)

        val result = executeQuery(query)

        assertEquals(3, result.size)
    }

    @Test
    fun `select with where equals`() {
        val query = QueryBuilder
            .select(name, age)
            .from(users)
            .where(age.isEqualTo(25))
            .build(resolver)

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
            .build(resolver)

        val result = executeQuery(query)

        assertEquals(2, result.size)
        val names = result.map { it["name"] }.toSet()
        assertEquals(setOf("Alice Brown", "Mike Taylor"), names)
    }

    @Test
    fun `select with where and`() {
        val query = QueryBuilder
            .select(name)
            .from(users)
            .where(role.isEqualTo("user").and(city.isEqualTo("Berlin")))
            .build(resolver)

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
            .build(resolver)

        val result = executeQuery(query)

        assertEquals(2, result.size)
        val names = result.map { it["name"] }.toSet()
        assertEquals(setOf("John Smith", "Mike Taylor"), names)
    }

    @Test
    fun `select with where like`() {
        val query = QueryBuilder
            .select(name, email)
            .from(users)
            .where(email.isLike(".*@example\\.com"))
            .build(resolver)

        val result = executeQuery(query)

        assertEquals(2, result.size)
        val emails = result.map { it["email"] }.toSet()
        assertEquals(setOf("alice@example.com", "mike@example.com"), emails)
    }

    @Test
    fun `select with where is null`() {
        val query = QueryBuilder
            .select(status)
            .from(orders)
            .where(status.isNull())
            .build(resolver)

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
            .build(resolver)

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
            .build(resolver)

        val result = executeQuery(query)

        assertEquals(2, result.size)
        val names = result.map { it["name"] }.toSet()
        assertEquals(setOf("Alice Brown", "Mike Taylor"), names)
    }

    @Test
    fun `select with pipeline join using compound condition`() {
        val query = QueryBuilder
            .select(id, name, age)
            .from(users)
            .join(orders, condition = id.isEqualTo(userId).and(name.isEqualTo("Alice Brown")))
            .build(resolver)

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
            .build(resolver)

        val result = executeQuery(query)

        assertEquals(3, result.size)
        assertEquals("Alice Brown", result[0]["name"])
        assertEquals("John Smith", result[1]["name"])
        assertEquals("Mike Taylor", result[2]["name"])
    }

    @Test
    fun `select with order by descending`() {
        val query = QueryBuilder
            .select(name, age)
            .from(users)
            .orderBy(age, OrderType.DESC)
            .build(resolver)

        val result = executeQuery(query)

        assertEquals(3, result.size)
        assertEquals("Mike Taylor", result[0]["name"])
        assertEquals("John Smith", result[1]["name"])
        assertEquals("Alice Brown", result[2]["name"])
    }

    @Test
    fun `select with limit`() {
        val query = QueryBuilder
            .select(name, age)
            .from(users)
            .orderBy(age, OrderType.ASC)
            .limit(2)
            .build(resolver)

        val result = executeQuery(query)

        assertEquals(2, result.size)
        assertEquals("Alice Brown", result[0]["name"])
        assertEquals("John Smith", result[1]["name"])
    }

    @Test
    fun `select with limit and offset`() {
        val query = QueryBuilder
            .select(name, age)
            .from(users)
            .orderBy(age, OrderType.ASC)
            .limit(2)
            .offset(1)
            .build(resolver)

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
            .build(resolver)

        val result = executeQuery(query)

        assertEquals(2, result.size)
        val ids = result.map { it["_id"] }.toSet()
        assertEquals(setOf("admin", "user"), ids)
    }

    @Test
    fun `select with group by multiple fields`() {
        val query = QueryBuilder
            .select(role, city)
            .from(users)
            .groupBy(role, city)
            .build(resolver)

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
            .build(resolver)

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
            .build(resolver)

        val result = executeQuery(query)

        assertEquals(1, result.size)
        assertEquals("Alice Brown", result[0]["name"])
        assertEquals("test", result[0]["var"])
    }
}
