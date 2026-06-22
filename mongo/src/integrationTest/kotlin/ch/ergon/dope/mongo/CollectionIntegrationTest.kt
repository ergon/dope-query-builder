package ch.ergon.dope.mongo

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.mongo.integrationTest.BaseIntegrationTest
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.name
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.users
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.collection.any
import ch.ergon.dope.resolvable.expression.type.collection.anyAndEvery
import ch.ergon.dope.resolvable.expression.type.collection.every
import ch.ergon.dope.resolvable.expression.type.collection.exists
import ch.ergon.dope.resolvable.expression.type.collection.inArray
import ch.ergon.dope.resolvable.expression.type.collection.notInArray
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.relational.isGreaterThan
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import kotlin.test.Test
import kotlin.test.assertEquals

class CollectionIntegrationTest : BaseIntegrationTest() {

    @Test
    fun `exists on non-empty literal array is true`() {
        val query = QueryBuilder
            .select(exists(listOf("a".toDopeType(), "b".toDopeType())).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(true, result[0]["result"])
    }

    @Test
    fun `exists on empty literal array is false`() {
        val query = QueryBuilder
            .select(exists(emptyList<TypeExpression<StringType>>()).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(false, result[0]["result"])
    }

    @Test
    fun `in array returns true when value present`() {
        val query = QueryBuilder
            .select("open".inArray(listOf("open", "closed")).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(true, result[0]["result"])
    }

    @Test
    fun `in array returns false when value absent`() {
        val query = QueryBuilder
            .select("missing".inArray(listOf("open", "closed")).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(false, result[0]["result"])
    }

    @Test
    fun `not in array returns true when value absent`() {
        val query = QueryBuilder
            .select("archived".notInArray(listOf("open", "closed")).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(true, result[0]["result"])
    }

    @Test
    fun `not in array returns false when value present`() {
        val query = QueryBuilder
            .select("open".notInArray(listOf("open", "closed")).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(false, result[0]["result"])
    }

    @Test
    fun `any satisfies is true when at least one element matches`() {
        val query = QueryBuilder
            .select(
                listOf(1.toDopeType(), 2.toDopeType(), 9.toDopeType())
                    .any("element") { it.isGreaterThan(5) }
                    .alias("result"),
            )
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(true, result[0]["result"])
    }

    @Test
    fun `any satisfies is false when no element matches`() {
        val query = QueryBuilder
            .select(
                listOf(1.toDopeType(), 2.toDopeType(), 3.toDopeType())
                    .any("element") { it.isGreaterThan(5) }
                    .alias("result"),
            )
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(false, result[0]["result"])
    }

    @Test
    fun `every satisfies is true when all elements match`() {
        val query = QueryBuilder
            .select(
                listOf(6.toDopeType(), 7.toDopeType(), 8.toDopeType())
                    .every("element") { it.isGreaterThan(5) }
                    .alias("result"),
            )
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(true, result[0]["result"])
    }

    @Test
    fun `every satisfies is false when one element does not match`() {
        val query = QueryBuilder
            .select(
                listOf(6.toDopeType(), 4.toDopeType(), 8.toDopeType())
                    .every("element") { it.isGreaterThan(5) }
                    .alias("result"),
            )
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(false, result[0]["result"])
    }

    @Test
    fun `any and every satisfies is true for non-empty all-matching array`() {
        val query = QueryBuilder
            .select(
                listOf(6.toDopeType(), 7.toDopeType())
                    .anyAndEvery("element") { it.isGreaterThan(5) }
                    .alias("result"),
            )
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(true, result[0]["result"])
    }

    @Test
    fun `any and every satisfies is false for empty array`() {
        val query = QueryBuilder
            .select(
                emptyList<TypeExpression<NumberType>>()
                    .anyAndEvery("element") { it.isGreaterThan(5) }
                    .alias("result"),
            )
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(false, result[0]["result"])
    }
}
