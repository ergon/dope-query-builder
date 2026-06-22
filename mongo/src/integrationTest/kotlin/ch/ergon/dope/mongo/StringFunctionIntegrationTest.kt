package ch.ergon.dope.mongo

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.mongo.integrationTest.BaseIntegrationTest
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.city
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.name
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.role
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.users
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.function.string.concat
import ch.ergon.dope.resolvable.expression.type.function.string.concat2
import ch.ergon.dope.resolvable.expression.type.function.string.contains
import ch.ergon.dope.resolvable.expression.type.function.string.length
import ch.ergon.dope.resolvable.expression.type.function.string.lower
import ch.ergon.dope.resolvable.expression.type.function.string.lpad
import ch.ergon.dope.resolvable.expression.type.function.string.ltrim
import ch.ergon.dope.resolvable.expression.type.function.string.mbLength
import ch.ergon.dope.resolvable.expression.type.function.string.mbLpad
import ch.ergon.dope.resolvable.expression.type.function.string.mbPosition
import ch.ergon.dope.resolvable.expression.type.function.string.mbPosition1
import ch.ergon.dope.resolvable.expression.type.function.string.mbRpad
import ch.ergon.dope.resolvable.expression.type.function.string.mbSubstring
import ch.ergon.dope.resolvable.expression.type.function.string.mbSubstring1
import ch.ergon.dope.resolvable.expression.type.function.string.position
import ch.ergon.dope.resolvable.expression.type.function.string.position1
import ch.ergon.dope.resolvable.expression.type.function.string.repeat
import ch.ergon.dope.resolvable.expression.type.function.string.replace
import ch.ergon.dope.resolvable.expression.type.function.string.rpad
import ch.ergon.dope.resolvable.expression.type.function.string.rtrim
import ch.ergon.dope.resolvable.expression.type.function.string.split
import ch.ergon.dope.resolvable.expression.type.function.string.substring
import ch.ergon.dope.resolvable.expression.type.function.string.substring1
import ch.ergon.dope.resolvable.expression.type.function.string.suffixes
import ch.ergon.dope.resolvable.expression.type.function.string.trim
import ch.ergon.dope.resolvable.expression.type.function.string.upper
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import kotlin.test.Test
import kotlin.test.assertEquals

class StringFunctionIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `concat field with literal and field`() {
        val query = QueryBuilder
            .select(name.concat(" - ", role).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("John Smith - admin", result[0]["result"])
    }

    @Test
    fun `contains returns true when substring present`() {
        val query = QueryBuilder
            .select(name.contains("Smith").alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(true, result[0]["result"])
    }

    @Test
    fun `contains returns false when substring absent`() {
        val query = QueryBuilder
            .select(name.contains("xyz").alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(false, result[0]["result"])
    }

    @Test
    fun `length counts code points of field`() {
        val query = QueryBuilder
            .select(city.length().alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(6, result[0]["result"])
    }

    @Test
    fun `mbLength counts code points of multibyte literal`() {
        val query = QueryBuilder
            .select("héllo".mbLength().alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(5, result[0]["result"])
    }

    @Test
    fun `lower lowercases field`() {
        val query = QueryBuilder
            .select(city.lower().alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("zurich", result[0]["result"])
    }

    @Test
    fun `upper uppercases field`() {
        val query = QueryBuilder
            .select(city.upper().alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("ZURICH", result[0]["result"])
    }

    @Test
    fun `trim removes surrounding whitespace`() {
        val query = QueryBuilder
            .select("  hi  ".trim().alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("hi", result[0]["result"])
    }

    @Test
    fun `trim removes given characters`() {
        val query = QueryBuilder
            .select("xxhixx".trim("x").alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("hi", result[0]["result"])
    }

    @Test
    fun `ltrim removes leading whitespace only`() {
        val query = QueryBuilder
            .select("  hi  ".ltrim().alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("hi  ", result[0]["result"])
    }

    @Test
    fun `rtrim removes trailing whitespace only`() {
        val query = QueryBuilder
            .select("  hi  ".rtrim().alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("  hi", result[0]["result"])
    }

    @Test
    fun `substring with explicit length is zero based`() {
        val query = QueryBuilder
            .select(city.substring(1, 3).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("uri", result[0]["result"])
    }

    @Test
    fun `substring without length takes remainder`() {
        val query = QueryBuilder
            .select(city.substring(2).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("rich", result[0]["result"])
    }

    @Test
    fun `substring1 is one based`() {
        val query = QueryBuilder
            .select("hello".substring1(1, 2).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("he", result[0]["result"])
    }

    @Test
    fun `mbSubstring is zero based on multibyte literal`() {
        val query = QueryBuilder
            .select("héllo".mbSubstring(1, 3).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("éll", result[0]["result"])
    }

    @Test
    fun `mbSubstring1 is one based on multibyte literal`() {
        val query = QueryBuilder
            .select("héllo".mbSubstring1(2, 3).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("éll", result[0]["result"])
    }

    @Test
    fun `position is zero based and finds substring`() {
        val query = QueryBuilder
            .select("hello".position("ll").alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(2, result[0]["result"])
    }

    @Test
    fun `position returns minus one when absent`() {
        val query = QueryBuilder
            .select("hello".position("z").alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(-1, result[0]["result"])
    }

    @Test
    fun `position1 is one based`() {
        val query = QueryBuilder
            .select("hello".position1("h").alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(1, result[0]["result"])
    }

    @Test
    fun `position1 returns zero when absent`() {
        val query = QueryBuilder
            .select("hello".position1("z").alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(0, result[0]["result"])
    }

    @Test
    fun `mbPosition is zero based on multibyte literal`() {
        val query = QueryBuilder
            .select("aébc".mbPosition("b").alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(2, result[0]["result"])
    }

    @Test
    fun `mbPosition1 is one based on multibyte literal`() {
        val query = QueryBuilder
            .select("aébc".mbPosition1("é").alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(2, result[0]["result"])
    }

    @Test
    fun `mbPosition1 returns zero when absent`() {
        val query = QueryBuilder
            .select("aébc".mbPosition1("z").alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(0, result[0]["result"])
    }

    @Test
    fun `replace without count replaces all occurrences`() {
        val query = QueryBuilder
            .select("a.b.c".replace(".", "-").alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("a-b-c", result[0]["result"])
    }

    @Test
    fun `replace with count one replaces first occurrence only`() {
        val query = QueryBuilder
            .select("a.b.c".replace(".", "-", 1).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("a-b.c", result[0]["result"])
    }

    @Test
    fun `split on delimiter yields array`() {
        val query = QueryBuilder
            .select("a,b,c".split(",").alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(listOf("a", "b", "c"), result[0]["result"])
    }

    @Test
    fun `concat2 joins values with separator`() {
        val query = QueryBuilder
            .select("-".concat2("a", "b", "c").alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("a-b-c", result[0]["result"])
    }

    @Test
    fun `lpad pads left with default space`() {
        val query = QueryBuilder
            .select("hi".lpad(5).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("   hi", result[0]["result"])
    }

    @Test
    fun `lpad pads left with cycled pad string`() {
        val query = QueryBuilder
            .select("hi".lpad(5, "ab").alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("abahi", result[0]["result"])
    }

    @Test
    fun `lpad truncates when longer than size`() {
        val query = QueryBuilder
            .select("hello".lpad(3).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("hel", result[0]["result"])
    }

    @Test
    fun `rpad pads right with cycled pad string`() {
        val query = QueryBuilder
            .select("hi".rpad(5, "ab").alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("hiaba", result[0]["result"])
    }

    @Test
    fun `repeat concatenates string n times`() {
        val query = QueryBuilder
            .select("ab".repeat(3).alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("ababab", result[0]["result"])
    }

    @Test
    fun `suffixes returns all suffixes`() {
        val query = QueryBuilder
            .select("abc".suffixes().alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals(listOf("abc", "bc", "c"), result[0]["result"])
    }

    @Test
    fun `mbLpad pads left on multibyte literal`() {
        val query = QueryBuilder
            .select("é".mbLpad(3, "x").alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("xxé", result[0]["result"])
    }

    @Test
    fun `mbRpad pads right on multibyte literal`() {
        val query = QueryBuilder
            .select("é".mbRpad(3, "x").alias("result"))
            .from(users)
            .where(name.isEqualTo("John Smith"))
            .buildMongo(resolver)

        val result = executeQuery(query)

        assertEquals("éxx", result[0]["result"])
    }
}
