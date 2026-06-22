package ch.ergon.dope.mongo

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.mongo.integrationTest.BaseIntegrationTest
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.age
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.name
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.users
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.function.type.isArray
import ch.ergon.dope.resolvable.expression.type.function.type.isAtom
import ch.ergon.dope.resolvable.expression.type.function.type.isBoolean
import ch.ergon.dope.resolvable.expression.type.function.type.isNumber
import ch.ergon.dope.resolvable.expression.type.function.type.isObject
import ch.ergon.dope.resolvable.expression.type.function.type.isString
import ch.ergon.dope.resolvable.expression.type.function.type.toArray
import ch.ergon.dope.resolvable.expression.type.function.type.toBool
import ch.ergon.dope.resolvable.expression.type.function.type.toNumber
import ch.ergon.dope.resolvable.expression.type.function.type.toObject
import ch.ergon.dope.resolvable.expression.type.function.type.toStr
import ch.ergon.dope.resolvable.expression.type.function.type.typeOf
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.toDopeType
import org.bson.Document
import kotlin.test.Test
import kotlin.test.assertEquals

class TypeFunctionIntegrationTest : BaseIntegrationTest() {

    @Test
    fun `isArray returns true for an array literal`() {
        val query = QueryBuilder
            .select(listOf(1, 2, 3).toDopeType().isArray().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(true, result[0]["result"])
    }

    @Test
    fun `isArray returns false for a number field`() {
        val query = QueryBuilder
            .select(age.isArray().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(false, result[0]["result"])
    }

    @Test
    fun `isNumber returns true for a number field`() {
        val query = QueryBuilder
            .select(age.isNumber().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(true, result[0]["result"])
    }

    @Test
    fun `isNumber returns false for a string field`() {
        val query = QueryBuilder
            .select(name.isNumber().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(false, result[0]["result"])
    }

    @Test
    fun `isBoolean returns true for a boolean literal`() {
        val query = QueryBuilder
            .select(true.toDopeType().isBoolean().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(true, result[0]["result"])
    }

    @Test
    fun `isBoolean returns false for a number field`() {
        val query = QueryBuilder
            .select(age.isBoolean().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(false, result[0]["result"])
    }

    @Test
    fun `isObject returns true for an object literal`() {
        val query = QueryBuilder
            .select(mapOf("x" to 1).toDopeType().isObject().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(true, result[0]["result"])
    }

    @Test
    fun `isObject returns false for an array literal`() {
        val query = QueryBuilder
            .select(listOf(1, 2).toDopeType().isObject().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(false, result[0]["result"])
    }

    @Test
    fun `isString returns true for a string field`() {
        val query = QueryBuilder
            .select(name.isString().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(true, result[0]["result"])
    }

    @Test
    fun `isString returns false for a number field`() {
        val query = QueryBuilder
            .select(age.isString().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(false, result[0]["result"])
    }

    @Test
    fun `toBool returns false for zero`() {
        val query = QueryBuilder
            .select(0.toDopeType().toBool().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(false, result[0]["result"])
    }

    @Test
    fun `toBool returns true for a non-zero number`() {
        val query = QueryBuilder
            .select(5.toDopeType().toBool().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(true, result[0]["result"])
    }

    @Test
    fun `toNumber parses a numeric string to a double`() {
        val query = QueryBuilder
            .select("3.5".toDopeType().toNumber().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(3.5, result[0]["result"])
    }

    @Test
    fun `toNumber converts a number field to a double`() {
        val query = QueryBuilder
            .select(age.toNumber().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(25.0, result[0]["result"])
    }

    @Test
    fun `toStr converts a number field to its string form`() {
        val query = QueryBuilder
            .select(age.toStr().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("25", result[0]["result"])
    }

    @Test
    fun `toStr converts a boolean literal to its string form`() {
        val query = QueryBuilder
            .select(true.toDopeType().toStr().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("true", result[0]["result"])
    }

    @Test
    fun `typeOf returns the bson type name of a number field`() {
        val query = QueryBuilder
            .select(typeOf(age).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("int", result[0]["result"])
    }

    @Test
    fun `typeOf returns the bson type name of a string field`() {
        val query = QueryBuilder
            .select(typeOf(name).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("string", result[0]["result"])
    }

    @Test
    fun `isAtom returns true for a number field`() {
        val query = QueryBuilder
            .select(age.isAtom().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(true, result[0]["result"])
    }

    @Test
    fun `isAtom returns false for an array literal`() {
        val query = QueryBuilder
            .select(listOf(1, 2, 3).toDopeType().isAtom().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(false, result[0]["result"])
    }

    @Test
    fun `toArray wraps a number field in a single element array`() {
        val query = QueryBuilder
            .select(age.toArray().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(listOf(25), result[0]["result"])
    }

    @Test
    fun `toObject returns an object literal unchanged`() {
        val query = QueryBuilder
            .select(mapOf("x" to 1).toDopeType().toObject().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(Document("x", 1), result[0]["result"])
    }

    @Test
    fun `toObject returns an empty object for a number`() {
        val query = QueryBuilder
            .select(5.toObject().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(Document(), result[0]["result"])
    }
}
