package ch.ergon.dope.mongo

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.mongo.integrationTest.BaseIntegrationTest
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.name
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.users
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.function.array.append
import ch.ergon.dope.resolvable.expression.type.function.array.arrayRange
import ch.ergon.dope.resolvable.expression.type.function.array.arrayRepeat
import ch.ergon.dope.resolvable.expression.type.function.array.average
import ch.ergon.dope.resolvable.expression.type.function.array.concat
import ch.ergon.dope.resolvable.expression.type.function.array.contains
import ch.ergon.dope.resolvable.expression.type.function.array.count
import ch.ergon.dope.resolvable.expression.type.function.array.distinct
import ch.ergon.dope.resolvable.expression.type.function.array.except
import ch.ergon.dope.resolvable.expression.type.function.array.ifNull
import ch.ergon.dope.resolvable.expression.type.function.array.insert
import ch.ergon.dope.resolvable.expression.type.function.array.intersect
import ch.ergon.dope.resolvable.expression.type.function.array.length
import ch.ergon.dope.resolvable.expression.type.function.array.max
import ch.ergon.dope.resolvable.expression.type.function.array.min
import ch.ergon.dope.resolvable.expression.type.function.array.move
import ch.ergon.dope.resolvable.expression.type.function.array.position
import ch.ergon.dope.resolvable.expression.type.function.array.prepend
import ch.ergon.dope.resolvable.expression.type.function.array.put
import ch.ergon.dope.resolvable.expression.type.function.array.remove
import ch.ergon.dope.resolvable.expression.type.function.array.replace
import ch.ergon.dope.resolvable.expression.type.function.array.reverse
import ch.ergon.dope.resolvable.expression.type.function.array.sort
import ch.ergon.dope.resolvable.expression.type.function.array.sum
import ch.ergon.dope.resolvable.expression.type.function.array.symDiff
import ch.ergon.dope.resolvable.expression.type.function.array.symDiff1
import ch.ergon.dope.resolvable.expression.type.function.array.symDiffN
import ch.ergon.dope.resolvable.expression.type.function.array.union
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayFunctionIntegrationTest : BaseIntegrationTest() {

    private fun numberArray(vararg values: Number) = values.toList().toDopeType()

    private fun stringArray(vararg values: String) = values.toList().toDopeType()

    @Test
    fun `array append`() {
        val query = QueryBuilder
            .select(numberArray(1, 2).append(3, 4).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(listOf(1, 2, 3, 4), result[0]["result"])
    }

    @Test
    fun `array prepend`() {
        val query = QueryBuilder
            .select(numberArray(3, 4).prepend(1, 2).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(listOf(1, 2, 3, 4), result[0]["result"])
    }

    @Test
    fun `array concat`() {
        val query = QueryBuilder
            .select(numberArray(1, 2).concat(numberArray(3)).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(listOf(1, 2, 3), result[0]["result"])
    }

    @Test
    fun `array union`() {
        val query = QueryBuilder
            .select(numberArray(1, 2).union(numberArray(2, 3)).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        @Suppress("UNCHECKED_CAST")
        val resultSet = (result[0]["result"] as List<Int>).toSet()
        assertEquals(setOf(1, 2, 3), resultSet)
    }

    @Test
    fun `array intersect`() {
        val query = QueryBuilder
            .select(numberArray(1, 2, 3).intersect(numberArray(2, 3, 4)).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        @Suppress("UNCHECKED_CAST")
        val resultSet = (result[0]["result"] as List<Int>).toSet()
        assertEquals(setOf(2, 3), resultSet)
    }

    @Test
    fun `array except`() {
        val query = QueryBuilder
            .select(numberArray(1, 2, 3).except(numberArray(2)).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        @Suppress("UNCHECKED_CAST")
        val resultSet = (result[0]["result"] as List<Int>).toSet()
        assertEquals(setOf(1, 3), resultSet)
    }

    @Test
    fun `array distinct`() {
        val query = QueryBuilder
            .select(numberArray(1, 2, 2, 3, 1).distinct().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        @Suppress("UNCHECKED_CAST")
        val resultSet = (result[0]["result"] as List<Int>).toSet()
        assertEquals(setOf(1, 2, 3), resultSet)
    }

    @Test
    fun `array average`() {
        val query = QueryBuilder
            .select(numberArray(1, 2, 3, 4).average().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(2.5, result[0]["result"])
    }

    @Test
    fun `array sum`() {
        val query = QueryBuilder
            .select(numberArray(1, 2, 3, 4).sum().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(10, result[0]["result"])
    }

    @Test
    fun `array max`() {
        val query = QueryBuilder
            .select(numberArray(3, 1, 2).max().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(3, result[0]["result"])
    }

    @Test
    fun `array min`() {
        val query = QueryBuilder
            .select(numberArray(3, 1, 2).min().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(1, result[0]["result"])
    }

    @Test
    fun `array count`() {
        val query = QueryBuilder
            .select(numberArray(1, 2, 3).count().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(3, result[0]["result"])
    }

    @Test
    fun `array length`() {
        val query = QueryBuilder
            .select(numberArray(1, 2, 3).length().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(3, result[0]["result"])
    }

    @Test
    fun `array contains true`() {
        val query = QueryBuilder
            .select(numberArray(1, 2, 3).contains(2).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(true, result[0]["result"])
    }

    @Test
    fun `array contains false`() {
        val query = QueryBuilder
            .select(numberArray(1, 2, 3).contains(9).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(false, result[0]["result"])
    }

    @Test
    fun `array position found`() {
        val query = QueryBuilder
            .select(stringArray("x", "y", "z").position("y".toDopeType()).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(1, result[0]["result"])
    }

    @Test
    fun `array position not found`() {
        val query = QueryBuilder
            .select(stringArray("x", "y", "z").position("q".toDopeType()).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(-1, result[0]["result"])
    }

    @Test
    fun `array reverse`() {
        val query = QueryBuilder
            .select(numberArray(1, 2, 3).reverse().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(listOf(3, 2, 1), result[0]["result"])
    }

    @Test
    fun `array sort`() {
        val query = QueryBuilder
            .select(numberArray(3, 1, 2).sort().alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(listOf(1, 2, 3), result[0]["result"])
    }

    @Test
    fun `array range without step`() {
        val query = QueryBuilder
            .select(arrayRange(0, 5).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(listOf(0, 1, 2, 3, 4), result[0]["result"])
    }

    @Test
    fun `array range with step`() {
        val query = QueryBuilder
            .select(arrayRange(0, 10, 2).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(listOf(0, 2, 4, 6, 8), result[0]["result"])
    }

    @Test
    fun `array remove`() {
        val query = QueryBuilder
            .select(numberArray(1, 2, 2, 3).remove(2).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(listOf(1, 3), result[0]["result"])
    }

    @Test
    fun `array if null`() {
        val query = QueryBuilder
            .select(numberArray(1, 2, 3).ifNull().alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(1, result[0]["result"])
    }

    @Test
    fun `array insert`() {
        val query = QueryBuilder
            .select(numberArray(1, 2, 3).insert(1, 9).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(listOf(1, 9, 2, 3), result[0]["result"])
    }

    @Test
    fun `array move`() {
        val query = QueryBuilder
            .select(numberArray(1, 2, 3).move(1, 2).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(listOf(1, 3, 2), result[0]["result"])
    }

    @Test
    fun `array put`() {
        val query = QueryBuilder
            .select(numberArray(1, 2).put(2, 3).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(listOf(1, 2, 3), result[0]["result"])
    }

    @Test
    fun `array repeat`() {
        val query = QueryBuilder
            .select(arrayRepeat(7.toDopeType(), 3).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(listOf(7, 7, 7), result[0]["result"])
    }

    @Test
    fun `array replace without max`() {
        val query = QueryBuilder
            .select(numberArray(1, 2, 2, 3).replace(2, 9).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(listOf(1, 9, 9, 3), result[0]["result"])
    }

    @Test
    fun `array replace with max`() {
        val query = QueryBuilder
            .select(numberArray(2, 2, 2).replace(2, 9, 2).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(listOf(9, 9, 2), result[0]["result"])
    }

    @Test
    fun `array sym diff`() {
        val query = QueryBuilder
            .select(numberArray(1, 2).symDiff(numberArray(2, 3)).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        @Suppress("UNCHECKED_CAST")
        val resultSet = (result[0]["result"] as List<Int>).toSet()
        assertEquals(setOf(1, 3), resultSet)
    }

    @Test
    fun `array sym diff one`() {
        val query = QueryBuilder
            .select(numberArray(1, 2).symDiff1(numberArray(2, 3)).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        @Suppress("UNCHECKED_CAST")
        val resultSet = (result[0]["result"] as List<Int>).toSet()
        assertEquals(setOf(1, 3), resultSet)
    }

    @Test
    fun `array sym diff n`() {
        val query = QueryBuilder
            .select(numberArray(1, 2).symDiffN(numberArray(2, 3), numberArray(3, 4)).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        @Suppress("UNCHECKED_CAST")
        val resultSet = (result[0]["result"] as List<Int>).toSet()
        assertEquals(setOf(1, 4), resultSet)
    }
}
