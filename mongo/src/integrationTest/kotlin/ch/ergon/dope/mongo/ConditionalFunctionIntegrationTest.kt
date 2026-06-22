package ch.ergon.dope.mongo

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.mongo.integrationTest.BaseIntegrationTest
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.age
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.name
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.users
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.function.conditional.coalesce
import ch.ergon.dope.resolvable.expression.type.function.conditional.decode
import ch.ergon.dope.resolvable.expression.type.function.conditional.ifMissing
import ch.ergon.dope.resolvable.expression.type.function.conditional.ifMissingOrNull
import ch.ergon.dope.resolvable.expression.type.function.conditional.ifNull
import ch.ergon.dope.resolvable.expression.type.function.conditional.nvl
import ch.ergon.dope.resolvable.expression.type.function.conditional.nvl2
import ch.ergon.dope.resolvable.expression.type.function.conditional.resultsIn
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import kotlin.test.Test
import kotlin.test.assertEquals

class ConditionalFunctionIntegrationTest : BaseIntegrationTest() {
    private val missingNumber = Field<NumberType>("missingNumberField", users)
    private val missingString = Field<StringType>("missingStringField", users)

    @Test
    fun `nvl returns the present value when the initial expression is present`() {
        val query = QueryBuilder
            .select(nvl(age, 0).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(30, result[0]["result"])
    }

    @Test
    fun `nvl returns the substitute when the initial expression is missing`() {
        val query = QueryBuilder
            .select(nvl(missingNumber, 99).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(99, result[0]["result"])
    }

    @Test
    fun `ifNull returns the first present argument`() {
        val query = QueryBuilder
            .select(ifNull(age, missingNumber).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(25, result[0]["result"])
    }

    @Test
    fun `ifNull skips missing arguments and returns the fallback`() {
        val query = QueryBuilder
            .select(ifNull(missingNumber, missingNumber, age).alias("result"))
            .from(users)
            .where(name.isEqualTo("Mike Taylor"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(35, result[0]["result"])
    }

    @Test
    fun `ifMissingOrNull returns the first present non-missing argument`() {
        val query = QueryBuilder
            .select(ifMissingOrNull(missingString, name).alias("result"))
            .from(users)
            .where(name.isEqualTo("Mike Taylor"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("Mike Taylor", result[0]["result"])
    }

    @Test
    fun `coalesce returns the first present non-missing argument`() {
        val query = QueryBuilder
            .select(coalesce(missingString, missingString, name).alias("result"))
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("Alice Brown", result[0]["result"])
    }

    @Test
    fun `nvl2 returns the value-if-exists when the initial expression is present`() {
        val query = QueryBuilder
            .select(nvl2(age, "hasVal", "noVal").alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("hasVal", result[0]["result"])
    }

    @Test
    fun `nvl2 returns the value-if-not-exists when the initial expression is missing`() {
        val query = QueryBuilder
            .select(nvl2(missingNumber, "hasVal", "noVal").alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("noVal", result[0]["result"])
    }

    @Test
    fun `ifMissing returns the first present argument`() {
        val query = QueryBuilder
            .select(ifMissing(age, missingNumber).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(30, result[0]["result"])
    }

    @Test
    fun `ifMissing skips missing arguments and returns the first present one`() {
        val query = QueryBuilder
            .select(ifMissing(missingNumber, missingNumber, age).alias("result"))
            .from(users)
            .where(name.isEqualTo("Mike Taylor"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(35, result[0]["result"])
    }

    @Test
    fun `decode returns the result of the matching branch`() {
        val query = QueryBuilder
            .select(
                decode(
                    age,
                    25.resultsIn("twentyFive"),
                    35.resultsIn("thirtyFive"),
                    default = "other".toDopeType(),
                ).alias("result"),
            )
            .from(users)
            .where(name.isEqualTo("Alice Brown"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("twentyFive", result[0]["result"])
    }

    @Test
    fun `decode returns the default when no branch matches`() {
        val query = QueryBuilder
            .select(
                decode(
                    age,
                    25.resultsIn("twentyFive"),
                    35.resultsIn("thirtyFive"),
                    default = "other".toDopeType(),
                ).alias("result"),
            )
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("other", result[0]["result"])
    }

    @Test
    fun `decode without default yields null when no branch matches`() {
        val query = QueryBuilder
            .select(
                decode(
                    age,
                    25.resultsIn("twentyFive"),
                    35.resultsIn("thirtyFive"),
                ).alias("result"),
            )
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(null, result[0]["result"])
    }
}
