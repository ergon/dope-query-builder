package ch.ergon.dope.mongo

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.mongo.integrationTest.BaseIntegrationTest
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.age
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.name
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.users
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.type.arithmetic.div
import ch.ergon.dope.resolvable.expression.type.arithmetic.mod
import ch.ergon.dope.resolvable.expression.type.arithmetic.mul
import ch.ergon.dope.resolvable.expression.type.arithmetic.neg
import ch.ergon.dope.resolvable.expression.type.arithmetic.sub
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import kotlin.test.Test
import kotlin.test.assertEquals

class ArithmeticIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `addition of field and number`() {
        val query = QueryBuilder
            .select(age.add(5).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(35, result[0]["result"])
    }

    @Test
    fun `addition of two fields`() {
        val query = QueryBuilder
            .select(age.add(age).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(60, result[0]["result"])
    }

    @Test
    fun `subtraction of field and number`() {
        val query = QueryBuilder
            .select(age.sub(5).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(25, result[0]["result"])
    }

    @Test
    fun `subtraction of two fields yields zero`() {
        val query = QueryBuilder
            .select(age.sub(age).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(0, result[0]["result"])
    }

    @Test
    fun `multiplication of field and number`() {
        val query = QueryBuilder
            .select(age.mul(2).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(60, result[0]["result"])
    }

    @Test
    fun `multiplication of two fields`() {
        val query = QueryBuilder
            .select(age.mul(age).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(625, result[0]["result"])
    }

    @Test
    fun `division yields a double`() {
        val query = QueryBuilder
            .select(age.div(4).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(7.5, result[0]["result"])
    }

    @Test
    fun `division of field by field yields one`() {
        val query = QueryBuilder
            .select(age.div(age).alias("result"))
            .from(users)
            .where(name.isEqualTo("Mike Taylor"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(1.0, result[0]["result"])
    }

    @Test
    fun `modulo of field and number`() {
        val query = QueryBuilder
            .select(age.mod(7).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(2, result[0]["result"])
    }

    @Test
    fun `modulo of two fields yields zero`() {
        val query = QueryBuilder
            .select(age.mod(age).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(0, result[0]["result"])
    }

    @Test
    fun `negation of a field`() {
        val query = QueryBuilder
            .select(neg(age).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(-30, result[0]["result"])
    }

    @Test
    fun `negation of a negative literal yields positive`() {
        val query = QueryBuilder
            .select(neg(-3).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(3, result[0]["result"])
    }
}
