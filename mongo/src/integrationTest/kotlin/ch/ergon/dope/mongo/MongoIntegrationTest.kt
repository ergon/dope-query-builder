package ch.ergon.dope.mongo

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.mongo.integrationTest.BaseIntegrationTest
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.age
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.email
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.id
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.name
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.orders
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.status
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.userId
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.users
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
    fun `select users with email like pattern`() {
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
    fun `select orders where status is not null`() {
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
    fun `select orders where status is null`() {
        val query = QueryBuilder
            .select(status)
            .from(orders)
            .where(status.isNull())
            .build(resolver)

        val result = executeQuery(query)

        assertEquals(1, result.size)
        assertNull(result[0]["status"])
    }
}
