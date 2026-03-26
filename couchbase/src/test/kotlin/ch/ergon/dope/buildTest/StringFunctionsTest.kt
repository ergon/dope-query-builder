package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.unifyString
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.type.function.string.concat
import ch.ergon.dope.resolvable.expression.type.function.string.concat2
import ch.ergon.dope.resolvable.expression.type.function.string.contains
import ch.ergon.dope.resolvable.expression.type.function.string.initCap
import ch.ergon.dope.resolvable.expression.type.function.string.length
import ch.ergon.dope.resolvable.expression.type.function.string.lower
import ch.ergon.dope.resolvable.expression.type.function.string.lpad
import ch.ergon.dope.resolvable.expression.type.function.string.ltrim
import ch.ergon.dope.resolvable.expression.type.function.string.mask
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
import ch.ergon.dope.resolvable.expression.type.function.string.reverse
import ch.ergon.dope.resolvable.expression.type.function.string.rpad
import ch.ergon.dope.resolvable.expression.type.function.string.rtrim
import ch.ergon.dope.resolvable.expression.type.function.string.split
import ch.ergon.dope.resolvable.expression.type.function.string.substring
import ch.ergon.dope.resolvable.expression.type.function.string.substring1
import ch.ergon.dope.resolvable.expression.type.function.string.suffixes
import ch.ergon.dope.resolvable.expression.type.function.string.title
import ch.ergon.dope.resolvable.expression.type.function.string.trim
import ch.ergon.dope.resolvable.expression.type.function.string.upper
import ch.ergon.dope.resolvable.expression.type.function.string.urlDecode
import ch.ergon.dope.resolvable.expression.type.function.string.urlEncode
import ch.ergon.dope.resolvable.expression.type.function.token.TokenCases
import ch.ergon.dope.resolvable.expression.type.function.token.customTokenOptions
import ch.ergon.dope.resolvable.expression.type.function.token.tokens
import ch.ergon.dope.resolvable.expression.type.get
import ch.ergon.dope.resolvable.expression.type.logic.and
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.relational.isGreaterThan
import ch.ergon.dope.resolvable.expression.type.relational.isLessThan
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class StringFunctionsTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should Support Concat With StringTypes`() {
        val expected = "SELECT CONCAT(\"abc\", \"def\", \"ghi\") AS `concat`"

        val actual: String = QueryBuilder
            .select(
                "abc".toDopeType().concat("def".toDopeType(), "ghi".toDopeType()).alias("concat"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Concat With Strings`() {
        val expected = "SELECT CONCAT(\"abc\", \"def\", \"ghi\") AS `concat`"

        val actual: String = QueryBuilder
            .select(
                "abc".toDopeType().concat("def", "ghi").alias("concat"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Mixed Concat`() {
        val expected = "SELECT CONCAT(\"abc\", \"def\", \"ghi\", `stringField`) AS `concat`"

        val actual: String = QueryBuilder
            .select(
                "abc".toDopeType().concat("def".toDopeType(), "ghi".toDopeType(), someStringField()).alias("concat"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Concat2 With StringTypes`() {
        val expected = "SELECT CONCAT2(\"-\", \"a\", \"d\") AS `c1`"

        val actual: String = QueryBuilder
            .select(
                "-".concat2(
                    "a".toDopeType(),
                    "d".toDopeType(),
                ).alias("c1"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Concat2 With Strings`() {
        val expected = "SELECT CONCAT2(\"-\", \"a\", \"d\") AS `c1`"

        val actual: String = QueryBuilder
            .select(
                "-".concat2(
                    "a",
                    "d",
                ).alias("c1"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Concat2 With Strings And StringType As separator`() {
        val expected = "SELECT CONCAT2(\"-\", \"a\", \"d\") AS `c1`"

        val actual: String = QueryBuilder
            .select(
                "-".toDopeType().concat2(
                    "a",
                    "d",
                ).alias("c1"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Concat2 One Argument`() {
        val expected = "CONCAT2(\"-\", \"a\") AS `c2`"

        val actual: String = "-".concat2("a".toDopeType()).alias("c2").toDopeQuery(resolver).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Contains`() {
        val expected = "SELECT CONTAINS(\"N1QL is awesome\", \"N1QL\") AS `n1ql`\n"

        val actual: String = QueryBuilder
            .select(
                "N1QL is awesome".toDopeType().contains("N1QL".toDopeType())
                    .alias("n1ql"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Contains With String`() {
        val expected = "SELECT CONTAINS(\"N1QL is awesome\", \"N1QL\") AS `n1ql`\n"

        val actual: String = QueryBuilder
            .select(
                "N1QL is awesome".toDopeType().contains("N1QL".toDopeType())
                    .alias("n1ql"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Contains Chained`() {
        val expected = "SELECT CONTAINS(\"N1QL is awesome\", \"N1QL\") AS `n1ql`,\n" +
            "       CONTAINS(\"N1QL is awesome\", \"SQL\") AS `no_sql`"

        val actual: String =
            QueryBuilder
                .select(
                    "N1QL is awesome".toDopeType().contains("N1QL".toDopeType()).alias("n1ql"),
                    "N1QL is awesome".toDopeType().contains("SQL").alias("no_sql"),
                ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Initcap`() {
        val expected = "SELECT INITCAP(\"N1QL is awesome\") AS `n1ql`"

        val actual: String = QueryBuilder
            .select("N1QL is awesome".initCap().alias("n1ql")).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Length`() {
        val expected = "SELECT LENGTH(\"N1QL is awesome\") AS `ascii`,\n" +
            "       LENGTH(\"Café\") AS `diacritic`,\n" +
            "       LENGTH(\"\") AS `zero`"

        val actual: String =
            QueryBuilder
                .select(
                    "N1QL is awesome".toDopeType().length().alias("ascii"),
                    "Café".toDopeType().length().alias("diacritic"),
                    "".toDopeType().length().alias("zero"),
                ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Lower`() {
        val expected = "SELECT LOWER(\"N1QL is awesome\") AS `n1ql`"

        val actual: String = QueryBuilder
            .select("N1QL is awesome".toDopeType().lower().alias("n1ql")).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Lpad`() {
        val expected = "SELECT LPAD(\"N1QL is awesome\", 20) AS `implicit_padding`,\n" +
            "       LPAD(\"N1QL is awesome\", 20, \"-*\") AS `repeated_padding`,\n" +
            "       LPAD(\"N1QL is awesome\", 20, \"987654321\") AS `truncate_padding`,\n" +
            "       LPAD(\"N1QL is awesome\", 4, \"987654321\") AS `truncate_string`"

        val actual: String = QueryBuilder
            .select(
                "N1QL is awesome".lpad(20).alias("implicit_padding"),
                "N1QL is awesome".lpad(20, "-*").alias("repeated_padding"),
                "N1QL is awesome".lpad(20, "987654321").alias("truncate_padding"),
                "N1QL is awesome".lpad(4, "987654321").alias("truncate_string"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Lpad With String`() {
        val expected = "SELECT LPAD(\"N1QL is awesome\", 20) AS `implicit_padding`,\n" +
            "       LPAD(\"N1QL is awesome\", 20, \"-*\") AS `repeated_padding`,\n" +
            "       LPAD(\"N1QL is awesome\", 20, \"987654321\") AS `truncate_padding`,\n" +
            "       LPAD(\"N1QL is awesome\", 20),\n" +
            "       LPAD(\"N1QL is awesome\", 20, \"987654321\"),\n" +
            "       LPAD(\"N1QL is awesome\", 20, \"987654321\"),\n" +
            "       LPAD(\"N1QL is awesome\", 4, \"987654321\") AS `truncate_string`"

        val actual: String = QueryBuilder
            .select(
                "N1QL is awesome".toDopeType().lpad(20).alias("implicit_padding"),
                "N1QL is awesome".toDopeType().lpad(20, "-*").alias("repeated_padding"),
                "N1QL is awesome".toDopeType().lpad(20.toDopeType(), "987654321").alias("truncate_padding"),
                "N1QL is awesome".lpad(20.toDopeType()),
                "N1QL is awesome".lpad(20.toDopeType(), "987654321".toDopeType()),
                "N1QL is awesome".lpad(20.toDopeType(), "987654321"),
                "N1QL is awesome".toDopeType().lpad(4, "987654321".toDopeType()).alias("truncate_string"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Lpad With String And StringTypes`() {
        val expected = "SELECT LPAD(\"N1QL is awesome\", 20, \"1234\") AS `implicit_padding`"

        val actual: String = QueryBuilder
            .select(
                "N1QL is awesome".lpad(20, "1234".toDopeType()).alias("implicit_padding"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Ltrim`() {
        val expected = "SELECT LTRIM(\"...N1QL is awesome\", \".\") AS `dots`, LTRIM(\"    N1QL is awesome\", \" \") AS " +
            "`explicit_spaces`, LTRIM(\"      N1QL is awesome\") AS `implicit_spaces`, LTRIM(\"N1QL is awesome\") AS `no_dots`"

        val actual: String = QueryBuilder
            .select(
                "...N1QL is awesome".ltrim(".").alias("dots"),
                "    N1QL is awesome".ltrim(" ").alias("explicit_spaces"),
                "      N1QL is awesome".ltrim().alias("implicit_spaces"),
                "N1QL is awesome".toDopeType().ltrim().alias("no_dots"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Ltrim With A String`() {
        val expected = "SELECT LTRIM(\"...N1QL is awesome\", \"...\") AS `dots`"

        val actual: String = QueryBuilder
            .select(
                "...N1QL is awesome".ltrim("...").alias("dots"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Ltrim With A StringType And Char`() {
        val expected = "SELECT LTRIM(\"...N1QL is awesome\", \".\") AS `dots`, LTRIM(\"...N1QL is awesome\", \".\")"

        val actual: String = QueryBuilder
            .select(
                "...N1QL is awesome".toDopeType().ltrim(".").alias("dots"),
                "...N1QL is awesome".ltrim(".".toDopeType()),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Mask`() {
        val expected = "SELECT MASK(\"SomeTextToMask\", {}) AS `mask`,\n" +
            "       MASK(\"SomeTextToMask\", {\"mask\": \"++++\"}) AS `mask_custom`,\n" +
            "       MASK(\"SomeTextToMask\", {\"mask\": \"++++ ++++\"}) AS `mask_hole`"

        val actual: String = QueryBuilder
            .select(
                "SomeTextToMask".mask().alias("mask"),
                "SomeTextToMask".mask(mapOf("mask" to "++++")).alias("mask_custom"),
                "SomeTextToMask".mask(mapOf("mask" to "++++ ++++")).alias("mask_hole"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Mask With StringType`() {
        val expected = "SELECT MASK(\"SomeTextToMask\", {}) AS `mask`,\n" +
            "       MASK(\"SomeTextToMask\", {\"mask\": \"++++\"}) AS `mask_custom`,\n" +
            "       MASK(\"SomeTextToMask\", {\"mask\": \"++++ ++++\"}) AS `mask_hole`"

        val actual: String = QueryBuilder
            .select(
                "SomeTextToMask".toDopeType().mask().alias("mask"),
                "SomeTextToMask".toDopeType().mask(mapOf("mask" to "++++")).alias("mask_custom"),
                "SomeTextToMask".toDopeType().mask(mapOf("mask" to "++++ ++++")).alias("mask_hole"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Mask 2`() {
        val expected = "SELECT MASK(\"SomeTextToMask\", {}) AS `mask`,\n" +
            "       MASK(\"SomeTextToMask\", {\"mask\": \"++++\"}) AS `mask_custom`,\n" +
            "       MASK(\"SomeTextToMask\", {\"mask\": \"++++ ++++\"}) AS `mask_hole`"

        val actual: String = QueryBuilder
            .select(
                "SomeTextToMask".mask()
                    .alias("mask"),
                "SomeTextToMask".mask(mapOf("mask" to "++++"))
                    .alias("mask_custom"),
                "SomeTextToMask".mask(mapOf("mask" to "++++ ++++"))
                    .alias("mask_hole"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Position`() {
        val expected = "SELECT POSITION(\"N1QL is awesome\", \"awesome\") AS `awesome`,\n" +
            "       POSITION(\"N1QL is awesome\", \"N1QL\") AS `n1ql`,\n" +
            "       POSITION(\"N1QL is awesome\", \"SQL\") AS `sql`"

        val actual: String = QueryBuilder
            .select(
                "N1QL is awesome".position("awesome").alias("awesome"),
                "N1QL is awesome".position("N1QL").alias("n1ql"),
                "N1QL is awesome".position("SQL").alias("sql"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Position With As StringType And String`() {
        val expected = "SELECT POSITION(\"N1QL is awesome\", \"awesome\") AS `awesome`,\n" +
            "       POSITION(\"N1QL is awesome\", \"N1QL\") AS `n1ql`,\n" +
            "       POSITION(\"N1QL is awesome\", \"SQL\") AS `sql`"

        val actual: String = QueryBuilder
            .select(
                "N1QL is awesome".toDopeType().position("awesome").alias("awesome"),
                "N1QL is awesome".toDopeType().position("N1QL").alias("n1ql"),
                "N1QL is awesome".toDopeType().position("SQL").alias("sql"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Position With As String And StringType`() {
        val expected = "SELECT POSITION(\"N1QL is awesome\", \"awesome\") AS `awesome`,\n" +
            "       POSITION(\"N1QL is awesome\", \"N1QL\") AS `n1ql`,\n" +
            "       POSITION(\"N1QL is awesome\", \"SQL\") AS `sql`"

        val actual: String = QueryBuilder
            .select(
                "N1QL is awesome".position("awesome".toDopeType()).alias("awesome"),
                "N1QL is awesome".position("N1QL".toDopeType()).alias("n1ql"),
                "N1QL is awesome".position("SQL".toDopeType()).alias("sql"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Repeat`() {
        val expected = "SELECT REPEAT(\"N1QL\", 0) AS `empty_string`, REPEAT(\"N1QL\", 3), REPEAT(\"N1QL\", 3), REPEAT(\"N1QL\", 3) AS `n1ql_3`"

        val actual: String = QueryBuilder
            .select(
                "N1QL".toDopeType().repeat(0).alias("empty_string"),
                "N1QL".toDopeType().repeat(3),
                "N1QL".toDopeType().repeat(3.toDopeType()),
                "N1QL".toDopeType().repeat(3.toDopeType()).alias("n1ql_3"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Reverse`() {
        val expected = "SELECT REVERSE(\"N1QL is awesome\") AS `n1ql`,\n" +
            "       REVERSE(\"racecar\") AS `palindrome`"

        val actual: String = QueryBuilder
            .select(
                "N1QL is awesome".reverse().alias("n1ql"),
                "racecar".reverse().alias("palindrome"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Rpad`() {
        val expected = "SELECT RPAD(\"N1QL is awesome\", 20) AS `implicit_padding`,\n" +
            "       RPAD(\"N1QL is awesome\", 20, \"-*\") AS `repeated_padding`,\n" +
            "       RPAD(\"N1QL is awesome\", 20, \"123456789\") AS `truncate_padding`,\n" +
            "       RPAD(\"N1QL is awesome\", 4, \"123456789\") AS `truncate_string`,\n" +
            "       RPAD(\"N1QL is awesome\", 4, \"123456789\") AS `truncate_string`,\n" +
            "       RPAD(\"N1QL is awesome\", 4) AS `truncate_string`,\n" +
            "       RPAD(\"N1QL is awesome\", 4, \"123456789\") AS `truncate_string`"

        val actual: String = QueryBuilder
            .select(
                "N1QL is awesome".rpad(20).alias("implicit_padding"),
                "N1QL is awesome".rpad(20, "-*").alias("repeated_padding"),
                "N1QL is awesome".rpad(20, "123456789").alias("truncate_padding"),
                "N1QL is awesome".rpad(4, "123456789").alias("truncate_string"),
                "N1QL is awesome".rpad(4.toDopeType(), "123456789".toDopeType()).alias("truncate_string"),
                "N1QL is awesome".rpad(4.toDopeType()).alias("truncate_string"),
                "N1QL is awesome".toDopeType().rpad(4, "123456789".toDopeType()).alias("truncate_string"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Rpad With StringType And String`() {
        val expected = "SELECT RPAD(\"N1QL is awesome\", 20) AS `implicit_padding`,\n" +
            "       RPAD(\"N1QL is awesome\", 20, \"-*\") AS `repeated_padding`,\n" +
            "       RPAD(\"N1QL is awesome\", 20, \"123456789\") AS `truncate_padding`,\n" +
            "       RPAD(\"N1QL is awesome\", 4, \"123456789\") AS `truncate_string`"

        val actual: String = QueryBuilder
            .select(
                "N1QL is awesome".toDopeType().rpad(20).alias("implicit_padding"),
                "N1QL is awesome".toDopeType().rpad(20, "-*").alias("repeated_padding"),
                "N1QL is awesome".toDopeType().rpad(20, "123456789").alias("truncate_padding"),
                "N1QL is awesome".toDopeType().rpad(4, "123456789").alias("truncate_string"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Rpad With String And StringType`() {
        val expected = "SELECT RPAD(\"N1QL is awesome\", 20) AS `implicit_padding`,\n" +
            "       RPAD(\"N1QL is awesome\", 20, \"-*\") AS `repeated_padding`,\n" +
            "       RPAD(\"N1QL is awesome\", 20, \"123456789\") AS `truncate_padding`,\n" +
            "       RPAD(\"N1QL is awesome\", 4, \"123456789\") AS `truncate_string`"

        val actual: String = QueryBuilder
            .select(
                "N1QL is awesome".rpad(20).alias("implicit_padding"),
                "N1QL is awesome".rpad(20, "-*".toDopeType()).alias("repeated_padding"),
                "N1QL is awesome".rpad(20, "123456789".toDopeType()).alias("truncate_padding"),
                "N1QL is awesome".rpad(4, "123456789".toDopeType()).alias("truncate_string"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Rtrim`() {
        val expected =
            "SELECT RTRIM(\"N1QL is awesome...\", \".\") AS `dots`, RTRIM(\"N1QL is awesome     \", \" \") AS " +
                "`explicit_spaces`, RTRIM(\"N1QL is awesome     \") AS `implicit_spaces`, RTRIM(\"N1QL is awesome\") AS `no_dots`"

        val actual: String = QueryBuilder
            .select(
                "N1QL is awesome...".rtrim(".").alias("dots"),
                "N1QL is awesome     ".rtrim(" ").alias("explicit_spaces"),
                "N1QL is awesome     ".rtrim().alias("implicit_spaces"),
                "N1QL is awesome".rtrim().alias("no_dots"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Rtrim With String And String`() {
        val expected =
            "SELECT RTRIM(\"N1QL is awesome...\", \".\") AS `dots`, RTRIM(\"N1QL is awesome     \", \" \") AS " +
                "`explicit_spaces`, RTRIM(\"N1QL is awesome     \") AS `implicit_spaces`, RTRIM(\"N1QL is awesome\") AS `no_dots`"

        val actual: String = QueryBuilder
            .select(
                "N1QL is awesome...".rtrim(".").alias("dots"),
                "N1QL is awesome     ".rtrim(" ").alias("explicit_spaces"),
                "N1QL is awesome     ".rtrim().alias("implicit_spaces"),
                "N1QL is awesome".rtrim().alias("no_dots"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Rtrim With StringType`() {
        val expected =
            "SELECT RTRIM(\"N1QL is awesome...\") AS `dots`, RTRIM(\"N1QL is awesome     \") AS " +
                "`explicit_spaces`, RTRIM(\"N1QL is awesome     \") AS `implicit_spaces`, RTRIM(\"N1QL is awesome\") AS `no_dots`"

        val actual: String = QueryBuilder
            .select(
                "N1QL is awesome...".toDopeType().rtrim().alias("dots"),
                "N1QL is awesome     ".toDopeType().rtrim().alias("explicit_spaces"),
                "N1QL is awesome     ".toDopeType().rtrim().alias("implicit_spaces"),
                "N1QL is awesome".toDopeType().rtrim().alias("no_dots"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Rtrim With StringType And A Char`() {
        val expected =
            "SELECT RTRIM(\"N1QL is awesome...\", \".\") AS `dots`, RTRIM(\"N1QL is awesome     \") AS " +
                "`explicit_spaces`, RTRIM(\"N1QL is awesome     \") AS `implicit_spaces`, RTRIM(\"N1QL is awesome\") AS `no_dots`"

        val actual: String = QueryBuilder
            .select(
                "N1QL is awesome...".toDopeType().rtrim(".").alias("dots"),
                "N1QL is awesome     ".toDopeType().rtrim().alias("explicit_spaces"),
                "N1QL is awesome     ".toDopeType().rtrim().alias("implicit_spaces"),
                "N1QL is awesome".toDopeType().rtrim().alias("no_dots"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Split`() {
        val expected =
            "SELECT SPLIT(\"N1QL is awesome\", \" \") AS `explicit_spaces`, SPLIT(\"N1QL is awesome\") AS" +
                " `implicit_spaces`, SPLIT(\"N1QL is awesome\", \"is\") AS `split_is`"

        val actual: String = QueryBuilder
            .select(
                "N1QL is awesome".toDopeType().split(" ").alias("explicit_spaces"),
                "N1QL is awesome".toDopeType().split().alias("implicit_spaces"),
                "N1QL is awesome".toDopeType().split("is").alias("split_is"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Split With StringType`() {
        val expected =
            "SELECT SPLIT(\"N1QL is awesome\", \" \") AS `explicit_spaces`, SPLIT(\"N1QL is awesome\") AS" +
                " `implicit_spaces`, SPLIT(\"N1QL is awesome\", \"is\") AS `split_is`"

        val actual: String = QueryBuilder
            .select(
                "N1QL is awesome".toDopeType().split(" ").alias("explicit_spaces"),
                "N1QL is awesome".toDopeType().split().alias("implicit_spaces"),
                "N1QL is awesome".toDopeType().split("is").alias("split_is"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Split With String And A StringType`() {
        val expected =
            "SELECT SPLIT(\"N1QL is awesome\", \" \") AS `explicit_spaces`, SPLIT(\"N1QL is awesome\") AS" +
                " `implicit_spaces`, SPLIT(\"N1QL is awesome\", \"is\") AS `split_is`"

        val actual: String = QueryBuilder
            .select(
                "N1QL is awesome".toDopeType().split(" ".toDopeType()).alias("explicit_spaces"),
                "N1QL is awesome".toDopeType().split().alias("implicit_spaces"),
                "N1QL is awesome".toDopeType().split("is".toDopeType()).alias("split_is"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Substring`() {
        val expected =
            "SELECT SUBSTR(\"N1QL is awesome\", 3) AS `end_of_string`, SUBSTR(\"N1QL is awesome\", 3, 1) AS" +
                " `single_letter`, SUBSTR(\"N1QL is awesome\", 3, 3) AS `three_letters`"

        val actual: String = QueryBuilder
            .select(
                "N1QL is awesome".toDopeType().substring(3).alias("end_of_string"),
                "N1QL is awesome".toDopeType().substring(3, 1).alias("single_letter"),
                "N1QL is awesome".toDopeType().substring(3, 3).alias("three_letters"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Suffixes`() {
        val expected = "SELECT SUFFIXES(\"N1QL is awesome\")"

        val actual: String = QueryBuilder
            .select("N1QL is awesome".suffixes()).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Title`() {
        val expected = "SELECT TITLE(\"N1QL is awesome\") AS `n1ql`"

        val actual: String = QueryBuilder
            .select("N1QL is awesome".title().alias("n1ql")).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Title with string Type`() {
        val expected = "SELECT TITLE(\"N1QL is awesome\") AS `n1ql`"

        val actual: String = QueryBuilder
            .select("N1QL is awesome".toDopeType().title().alias("n1ql")).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Tokens`() {
        val expected = "SELECT TOKENS([\"jim@example.com\", \"kim@example.com\", \"https://example.com/\", \"408-555-1212\"], " +
            "{\"name\": true})"

        val actual: String = QueryBuilder
            .select(
                listOf("jim@example.com", "kim@example.com", "https://example.com/", "408-555-1212").tokens(customTokenOptions(name = true)),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Tokens With Empty Options`() {
        val expected = "SELECT TOKENS([\"jim@example.com\"])"

        val actual: String = QueryBuilder
            .select(
                listOf("jim@example.com").tokens(),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Token Options`() {
        val expected = "SELECT TOKENS([\"jim@example.com\"], {\"specials\": true})"

        val actual: String = QueryBuilder
            .select(listOf("jim@example.com").tokens(customTokenOptions(specials = true)))
            .build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Multiple Token Options`() {
        val expected = "SELECT TOKENS([\"jim@example.com\"], " +
            "{\"case\": \"LOWER\", \"specials\": true})"

        val actual: String = QueryBuilder
            .select(
                listOf("jim@example.com").tokens(customTokenOptions(specials = true, case = TokenCases.LOWER)),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Multiple Token Options 2`() {
        val expected = "SELECT TOKENS([\"jim@example.com\"], " +
            "{\"name\": false, \"case\": \"UPPER\", \"specials\": false})"

        val actual: String = QueryBuilder
            .select(
                listOf("jim@example.com").tokens(customTokenOptions(specials = false, case = TokenCases.UPPER, name = false)),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Trim`() {
        val expected = "SELECT TRIM(\"...N1QL is awesome...\", \".\") AS `dots`," +
            " TRIM(\"     N1QL is awesome     \") AS `explicit_spaces`," +
            " TRIM(\"     N1QL is awesome     \") AS `implicit_spaces`," +
            " TRIM(\"N1QL is awesome\") AS `no_dots`"

        val actual: String = QueryBuilder
            .select(
                "...N1QL is awesome...".toDopeType().trim(".")
                    .alias("dots"),
                "     N1QL is awesome     ".toDopeType().trim()
                    .alias("explicit_spaces"),
                "     N1QL is awesome     ".toDopeType().trim()
                    .alias("implicit_spaces"),
                "N1QL is awesome".toDopeType().trim()
                    .alias("no_dots"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Trim With A String And A String`() {
        val expected = "SELECT TRIM(\"...N1QL is awesome...\", \"...\") AS `dots`," +
            " TRIM(\"     N1QL is awesome     \", \" \") AS `explicit_spaces`," +
            " TRIM(\"     N1QL is awesome     \") AS `implicit_spaces`," +
            " TRIM(\"N1QL is awesome\") AS `no_dots`"

        val actual: String = QueryBuilder
            .select(
                "...N1QL is awesome...".toDopeType().trim("...")
                    .alias("dots"),
                "     N1QL is awesome     ".toDopeType().trim(" ")
                    .alias("explicit_spaces"),
                "     N1QL is awesome     ".toDopeType().trim()
                    .alias("implicit_spaces"),
                "N1QL is awesome".toDopeType().trim()
                    .alias("no_dots"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Trim With String Type`() {
        val expected = "SELECT TRIM(\"...N1QL is awesome...\", \".\") AS `dots`," +
            " TRIM(\"     N1QL is awesome     \") AS `explicit_spaces`," +
            " TRIM(\"     N1QL is awesome     \") AS `implicit_spaces`," +
            " TRIM(\"N1QL is awesome\") AS `no_dots`"

        val actual: String = QueryBuilder
            .select(
                "...N1QL is awesome...".toDopeType().trim(".")
                    .alias("dots"),
                "     N1QL is awesome     ".toDopeType().trim()
                    .alias("explicit_spaces"),
                "     N1QL is awesome     ".toDopeType().trim()
                    .alias("implicit_spaces"),
                "N1QL is awesome".toDopeType().trim()
                    .alias("no_dots"),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Upper`() {
        val expected = "SELECT UPPER(\"N1QL is awesome\") AS `n1ql`"

        val actual: String = QueryBuilder
            .select("N1QL is awesome".toDopeType().upper().alias("n1ql"))
            .build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support string functions in where clause`() {
        val expected = "SELECT * FROM `someBucket` WHERE CONTAINS(`stringField`, \"123\")"

        val actual: String = QueryBuilder
            .selectFrom(someBucket())
            .where(someStringField().contains("123"))
            .build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support string functions with conditions in where clause`() {
        val expected = "SELECT * FROM `someBucket` WHERE UPPER(`stringField`) = \"VENDOLIN\""
        val actual: String = QueryBuilder
            .selectFrom(someBucket())
            .where(someStringField().upper().isEqualTo("VENDOLIN".toDopeType()))
            .build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested string functions`() {
        val expected = "SELECT CONTAINS(UPPER(\"vendolin\"), \"VEN\") AS `foo`"

        val actual: String = QueryBuilder
            .select("vendolin".toDopeType().upper().contains("VEN").alias("foo"))
            .build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support string functions with number types position`() {
        val expected = "SELECT (POSITION1(\"input\", \"i\") + POSITION(\"input\", \"n\")) " +
            "< (MB_POSITION(\"input\", \"in\") + MB_POSITION1(\"input\", \"pu\"))"

        val actual: String = QueryBuilder
            .select(
                "input".position1("i").add("input".position("n")).isLessThan(
                    "input".mbPosition("in").add(
                        "input".mbPosition1("pu"),
                    ),
                ),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support string functions with number types length`() {
        val expected = "SELECT (LENGTH(\"input\") + MB_LENGTH(\"input\")) > 5"

        val actual: String = QueryBuilder
            .select(
                "input".toDopeType().length().add("input".mbLength()).isGreaterThan(5),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support string functions with string types concat and lower`() {
        val expected = "SELECT CONCAT(CONCAT(\"a\", \"b\"), " +
            "CONCAT2(\" \", \"c\", \"d\"), LOWER(\"TEST\"), UPPER(\"test\"))"

        val actual: String = QueryBuilder
            .select(
                "a".toDopeType().concat("b").concat(" ".concat2("c", "d"), "TEST".toDopeType().lower(), "test".toDopeType().upper()),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support string functions with string types pad`() {
        val expected = "SELECT CONCAT(LPAD(\"input\", 4, \"i\"), RPAD(\"input\", 3), MB_LPAD(\"input\", 5), MB_RPAD(\"input\", 4, \"t\"))"

        val actual: String = QueryBuilder
            .select(
                "input".lpad(4, "i").concat("input".rpad(3), "input".mbLpad(5), "input".mbRpad(4, "t")),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support string functions with string types substring`() {
        val expected = "SELECT CONCAT(SUBSTR(\"input\", 2), SUBSTR1(\"input\", 4, 3), MB_SUBSTR(\"input\", 0, 2), MB_SUBSTR1(\"input\", 2, 2))"

        val actual: String = QueryBuilder
            .select(
                "input".toDopeType().substring(2).concat(
                    "input".toDopeType().substring1(4, 3),
                    "input".toDopeType().mbSubstring(0, 2),
                    "input".toDopeType().mbSubstring1(2, 2),
                ),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support string functions with string types trim`() {
        val expected = "SELECT CONCAT(TRIM(\"  input   \"), LTRIM(\"input\", \"in\"), RTRIM(\"input\", \"ut\"))"

        val actual: String = QueryBuilder
            .select(
                "  input   ".toDopeType().trim().concat("input".ltrim("in"), "input".rtrim("ut")),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support string functions with boolean types contains`() {
        val expected = "SELECT (CONTAINS(\"input\", \"in\") AND TRUE)"

        val actual: String = QueryBuilder
            .select(
                "input".toDopeType().contains("in").and(true),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support string functions with array string types suffix and split`() {
        val expected = "SELECT SUFFIXES(\"input\")[2] = SPLIT(\"input\", \"p\")[1]"

        val actual: String = QueryBuilder
            .select(
                "input".suffixes().get(2).isEqualTo("input".toDopeType().split("p").get(1)),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support string functions with number types other`() {
        val expected = "SELECT CONCAT(INITCAP(\"input\"), MASK(\"input\", {}), REPEAT(\"input\", 3), " +
            "REPLACE(\"input\", \"p\", \"abo\"), REVERSE(\"input\"), " +
            "TITLE(\"input\"), URL_DECODE(\"encoded\"), URL_ENCODE(\"input\"))"

        val actual: String = QueryBuilder
            .select(
                "input".initCap().concat(
                    "input".mask(),
                    "input".toDopeType().repeat(3),
                    "input".toDopeType().replace("p", "abo"),
                    "input".reverse(),
                    "input".title(),
                    "encoded".urlDecode(),
                    "input".urlEncode(),
                ),
            ).build(CouchbaseResolver()).queryString

        assertEquals(expected, actual)
    }
}
