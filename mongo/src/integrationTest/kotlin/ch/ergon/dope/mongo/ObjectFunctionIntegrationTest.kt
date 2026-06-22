package ch.ergon.dope.mongo

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.mongo.integrationTest.BaseIntegrationTest
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.name
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.users
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.function.objects.addAttribute
import ch.ergon.dope.resolvable.expression.type.function.objects.concat
import ch.ergon.dope.resolvable.expression.type.function.objects.getField
import ch.ergon.dope.resolvable.expression.type.function.objects.getLength
import ch.ergon.dope.resolvable.expression.type.function.objects.getNames
import ch.ergon.dope.resolvable.expression.type.function.objects.getPairs
import ch.ergon.dope.resolvable.expression.type.function.objects.getValues
import ch.ergon.dope.resolvable.expression.type.function.objects.putAttribute
import ch.ergon.dope.resolvable.expression.type.function.objects.removeAttribute
import ch.ergon.dope.resolvable.expression.type.function.objects.renameAttribute
import ch.ergon.dope.resolvable.expression.type.function.objects.replace
import ch.ergon.dope.resolvable.expression.type.function.objects.unwrap
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.toDopeType
import org.bson.Document
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectFunctionIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `object add attribute`() {
        val source = mapOf("a" to 1).toDopeType()
        val query = QueryBuilder
            .select(source.addAttribute("b", 2.toDopeType()).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(Document(mapOf("a" to 1, "b" to 2)), result[0]["result"])
    }

    @Test
    fun `object concat`() {
        val first = mapOf("x" to 1).toDopeType()
        val second = mapOf("x" to 2, "y" to 3).toDopeType()
        val query = QueryBuilder
            .select(first.concat(second).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(Document(mapOf("x" to 2, "y" to 3)), result[0]["result"])
    }

    @Test
    fun `object field`() {
        val source = mapOf("color" to "red").toDopeType()
        val query = QueryBuilder
            .select(source.getField("color").alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("red", result[0]["result"])
    }

    @Test
    fun `object length`() {
        val source = mapOf("a" to 1, "b" to 2, "c" to 3).toDopeType()
        val query = QueryBuilder
            .select(source.getLength().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(3, result[0]["result"])
    }

    @Test
    fun `object names sorted ascending`() {
        val source = mapOf("b" to 1, "a" to 2).toDopeType()
        val query = QueryBuilder
            .select(source.getNames().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(listOf("a", "b"), result[0]["result"])
    }

    @Test
    fun `object pairs sorted ascending by name`() {
        val source = mapOf("b" to 2, "a" to 1).toDopeType()
        val query = QueryBuilder
            .select(source.getPairs().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(
            listOf(
                Document(mapOf("name" to "a", "val" to 1)),
                Document(mapOf("name" to "b", "val" to 2)),
            ),
            result[0]["result"],
        )
    }

    @Test
    fun `object put overwrites existing attribute`() {
        val source = mapOf("a" to 1, "b" to 2).toDopeType()
        val query = QueryBuilder
            .select(source.putAttribute("b", 9.toDopeType()).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(Document(mapOf("a" to 1, "b" to 9)), result[0]["result"])
    }

    @Test
    fun `object remove attribute`() {
        val source = mapOf("a" to 1, "b" to 2).toDopeType()
        val query = QueryBuilder
            .select(source.removeAttribute("b").alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(Document(mapOf("a" to 1)), result[0]["result"])
    }

    @Test
    fun `object rename attribute`() {
        val source = mapOf("a" to 1, "b" to 2).toDopeType()
        val query = QueryBuilder
            .select(source.renameAttribute("a", "z").alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(Document(mapOf("b" to 2, "z" to 1)), result[0]["result"])
    }

    @Test
    fun `object replace value`() {
        val source = mapOf("a" to 1, "b" to 2, "c" to 1).toDopeType()
        val query = QueryBuilder
            .select(source.replace(1.toDopeType(), 9.toDopeType()).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(Document(mapOf("a" to 9, "b" to 2, "c" to 9)), result[0]["result"])
    }

    @Test
    fun `object unwrap single field value`() {
        val source = mapOf("only" to 42).toDopeType()
        val query = QueryBuilder
            .select(source.unwrap().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(42, result[0]["result"])
    }

    @Test
    fun `object values sorted by key ascending`() {
        val source = mapOf("b" to 2, "a" to 1).toDopeType()
        val query = QueryBuilder
            .select(source.getValues().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(listOf(1, 2), result[0]["result"])
    }
}
