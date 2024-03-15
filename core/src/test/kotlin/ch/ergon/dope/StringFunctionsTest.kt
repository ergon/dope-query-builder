package ch.ergon.dope

import ch.ergon.dope.helper.unifyString
import ch.ergon.dope.resolvable.expression.alias
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.concat
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.concat2
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.contains
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.factory.CustomTokenOptions
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.factory.TOKEN_CASES
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.factory.tokens
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.initCap
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.length
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.lower
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.lpad
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.ltrim
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.mask
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.position
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.repeat
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.reverse
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.rpad
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.rtrim
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.split
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.substr
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.suffixes
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.title
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.trim
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.upper
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class StringFunctionsTest {

    private lateinit var builder: StringBuilder
    private lateinit var create: DSLContext

    @BeforeTest
    fun setup() {
        builder = StringBuilder()
        create = DSLContext()
    }

    @Test
    fun `should Support Concat With StringTypes`() {
        val expected = "SELECT CONCAT(\"abc\", \"def\", \"ghi\") AS concat"

        val actual: String = create.select(
            concat("abc".toStringType(), "def".toStringType(), "ghi".toStringType()).alias("concat"),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Concat With Strings`() {
        val expected = "SELECT CONCAT(\"abc\", \"def\", \"ghi\") AS concat"

        val actual: String = create.select(
            concat("abc", "def", "ghi").alias("concat"),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Mixed Concat`() {
        val expected = "SELECT CONCAT(\"abc\", \"def\", \"ghi\", person.fname) AS concat"

        val actual: String = create.select(
            concat(
                "abc".toStringType(),
                "def".toStringType(),
                "ghi".toStringType(),
                TestBucket.Person.fname,
            ).alias(
                "concat",
            ),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Concat2 With StringTypes`() {
        val expected = "SELECT CONCAT2(\"-\", \"a\", \"b\", \"c\", \"d\") AS c1"

        val actual: String = create.select(
            concat2(
                "-",
                "a".toStringType(),
                "b".toStringType(),
                "c".toStringType(),
                "d".toStringType(),
            ).alias("c1"),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Concat2 With Strings`() {
        val expected = "SELECT CONCAT2(\"-\", \"a\", \"b\", \"c\", \"d\") AS c1"

        val actual: String = create.select(
            concat2(
                "-",
                "a",
                "b",
                "c",
                "d",
            ).alias("c1"),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Concat2 With Strings And StringType As Seperator`() {
        val expected = "SELECT CONCAT2(\"-\", \"a\", \"b\", \"c\", \"d\") AS c1"

        val actual: String = create.select(
            concat2(
                "-".toStringType(),
                "a",
                "b",
                "c",
                "d",
            ).alias("c1"),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Concat2 One Argument`() {
        val expected = "CONCAT2(\"-\", \"a\") AS c2"

        val actual: String = concat2("-", "a".toStringType()).alias("c2").toQueryString()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Contains`() {
        val expected = "SELECT CONTAINS(\"N1QL is awesome\", \"N1QL\") AS n1ql\n"

        val actual: String = create.select(
            contains("N1QL is awesome".toStringType(), "N1QL".toStringType())
                .alias("n1ql"),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Contains With String`() {
        val expected = "SELECT CONTAINS(\"N1QL is awesome\", \"N1QL\") AS n1ql\n"

        val actual: String = create.select(
            contains("N1QL is awesome", "N1QL".toStringType())
                .alias("n1ql"),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Contains Chained`() {
        val expected = "SELECT CONTAINS(\"N1QL is awesome\", \"N1QL\") AS n1ql,\n" +
            "       CONTAINS(\"N1QL is awesome\", \"SQL\") AS no_sql"

        val actual: String =
            create.select(
                contains("N1QL is awesome".toStringType(), "N1QL".toStringType()).alias("n1ql"),
                contains("N1QL is awesome", "SQL").alias("no_sql"),
            ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Initcap`() {
        val expected = "SELECT INITCAP(\"N1QL is awesome\") AS n1ql"

        val actual: String = create.select(initCap("N1QL is awesome").alias("n1ql")).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Length`() {
        val expected = "SELECT LENGTH(\"N1QL is awesome\") AS ascii,\n" +
            "       LENGTH(\"Café\") AS diacritic,\n" +
            "       LENGTH(\"\") AS zero"

        val actual: String =
            create.select(
                length("N1QL is awesome").alias("ascii"),
                length("Café").alias("diacritic"),
                length("").alias("zero"),
            ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Lower`() {
        val expected = "SELECT LOWER(\"N1QL is awesome\") AS n1ql"

        val actual: String = create.select(lower("N1QL is awesome").alias("n1ql")).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Lpad`() {
        val expected = "SELECT LPAD(\"N1QL is awesome\", 20, \" \") AS implicit_padding,\n" +
            "       LPAD(\"N1QL is awesome\", 20, \"-*\") AS repeated_padding,\n" +
            "       LPAD(\"N1QL is awesome\", 20, \"987654321\") AS truncate_padding,\n" +
            "       LPAD(\"N1QL is awesome\", 4, \"987654321\") AS truncate_string"

        val actual: String = create.select(
            lpad("N1QL is awesome", 20).alias("implicit_padding"),
            lpad("N1QL is awesome", 20, "-*").alias("repeated_padding"),
            lpad("N1QL is awesome", 20, "987654321").alias("truncate_padding"),
            lpad("N1QL is awesome", 4, "987654321").alias("truncate_string"),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Lpad With String`() {
        val expected = "SELECT LPAD(\"N1QL is awesome\", 20, \" \") AS implicit_padding,\n" +
            "       LPAD(\"N1QL is awesome\", 20, \"-*\") AS repeated_padding,\n" +
            "       LPAD(\"N1QL is awesome\", 20, \"987654321\") AS truncate_padding,\n" +
            "       LPAD(\"N1QL is awesome\", 4, \"987654321\") AS truncate_string"

        val actual: String = create.select(
            lpad("N1QL is awesome".toStringType(), 20).alias("implicit_padding"),
            lpad("N1QL is awesome".toStringType(), 20, "-*").alias("repeated_padding"),
            lpad("N1QL is awesome".toStringType(), 20, "987654321").alias("truncate_padding"),
            lpad("N1QL is awesome".toStringType(), 4, "987654321").alias("truncate_string"),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Lpad With String And StringTypes`() {
        val expected = "SELECT LPAD(\"N1QL is awesome\", 20, \"1234\") AS implicit_padding"

        val actual: String = create.select(
            lpad("N1QL is awesome", 20, "1234".toStringType()).alias("implicit_padding"),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Ltrim`() {
        val expected = "SELECT LTRIM(\"...N1QL is awesome\", \".\") AS dots, LTRIM(\"    N1QL is awesome\", \" \") AS " +
            "explicit_spaces, LTRIM(\"      N1QL is awesome\", \" \") AS implicit_spaces, LTRIM(\"N1QL is awesome\", \" \") AS no_dots"

        val actual: String = create.select(
            ltrim("...N1QL is awesome", '.').alias("dots"),
            ltrim("    N1QL is awesome", ' ').alias("explicit_spaces"),
            ltrim("      N1QL is awesome").alias("implicit_spaces"),
            ltrim("N1QL is awesome".toStringType()).alias("no_dots"),
        ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Ltrim With A String`() {
        val expected = "SELECT LTRIM(\"...N1QL is awesome\", \"...\") AS dots"

        val actual: String = create.select(
            ltrim("...N1QL is awesome", "...").alias("dots"),
        ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Ltrim With A StringType And Char`() {
        val expected = "SELECT LTRIM(\"...N1QL is awesome\", \".\") AS dots"

        val actual: String = create.select(
            ltrim("...N1QL is awesome".toStringType(), '.').alias("dots"),
        ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Mask`() {
        val expected = "SELECT MASK(\"SomeTextToMask\", {}) AS mask,\n" +
            "       MASK(\"SomeTextToMask\", {\"mask\": \"++++\"}) AS mask_custom,\n" +
            "       MASK(\"SomeTextToMask\", {\"mask\": \"++++ ++++\"}) AS mask_hole"

        val actual: String = create.select(
            mask("SomeTextToMask").alias("mask"),
            mask("SomeTextToMask", mapOf("mask" to "++++")).alias("mask_custom"),
            mask("SomeTextToMask", mapOf("mask" to "++++ ++++")).alias("mask_hole"),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Mask With StringType`() {
        val expected = "SELECT MASK(\"SomeTextToMask\", {}) AS mask,\n" +
            "       MASK(\"SomeTextToMask\", {\"mask\": \"++++\"}) AS mask_custom,\n" +
            "       MASK(\"SomeTextToMask\", {\"mask\": \"++++ ++++\"}) AS mask_hole"

        val actual: String = create.select(
            mask("SomeTextToMask".toStringType()).alias("mask"),
            mask("SomeTextToMask".toStringType(), mapOf("mask" to "++++")).alias("mask_custom"),
            mask("SomeTextToMask".toStringType(), mapOf("mask" to "++++ ++++")).alias("mask_hole"),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Mask 2`() {
        val expected = "SELECT MASK(\"SomeTextToMask\", {}) AS mask,\n" +
            "       MASK(\"SomeTextToMask\", {\"mask\": \"++++\"}) AS mask_custom,\n" +
            "       MASK(\"SomeTextToMask\", {\"mask\": \"++++ ++++\"}) AS mask_hole"

        val actual: String = create.select(
            mask("SomeTextToMask")
                .alias("mask"),
            mask("SomeTextToMask", mapOf("mask" to "++++"))
                .alias("mask_custom"),
            mask("SomeTextToMask", mapOf("mask" to "++++ ++++"))
                .alias("mask_hole"),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Position`() {
        val expected = "SELECT POSITION(\"N1QL is awesome\", \"awesome\") AS awesome,\n" +
            "       POSITION(\"N1QL is awesome\", \"N1QL\") AS n1ql,\n" +
            "       POSITION(\"N1QL is awesome\", \"SQL\") AS sql"

        val actual: String = create.select(
            position("N1QL is awesome", "awesome").alias("awesome"),
            position("N1QL is awesome", "N1QL").alias("n1ql"),
            position("N1QL is awesome", "SQL").alias("sql"),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Position With As StringType And String`() {
        val expected = "SELECT POSITION(\"N1QL is awesome\", \"awesome\") AS awesome,\n" +
            "       POSITION(\"N1QL is awesome\", \"N1QL\") AS n1ql,\n" +
            "       POSITION(\"N1QL is awesome\", \"SQL\") AS sql"

        val actual: String = create.select(
            position("N1QL is awesome".toStringType(), "awesome").alias("awesome"),
            position("N1QL is awesome".toStringType(), "N1QL").alias("n1ql"),
            position("N1QL is awesome".toStringType(), "SQL").alias("sql"),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Position With As String And StringType`() {
        val expected = "SELECT POSITION(\"N1QL is awesome\", \"awesome\") AS awesome,\n" +
            "       POSITION(\"N1QL is awesome\", \"N1QL\") AS n1ql,\n" +
            "       POSITION(\"N1QL is awesome\", \"SQL\") AS sql"

        val actual: String = create.select(
            position("N1QL is awesome", "awesome".toStringType()).alias("awesome"),
            position("N1QL is awesome", "N1QL".toStringType()).alias("n1ql"),
            position("N1QL is awesome", "SQL".toStringType()).alias("sql"),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Repeat`() {
        val expected = "SELECT REPEAT(\"N1QL\", 0) AS empty_string,\n" +
            "       REPEAT(\"N1QL\", 3) AS n1ql_3"

        val actual: String = create.select(
            repeat("N1QL", 0).alias("empty_string"),
            repeat("N1QL", 3).alias("n1ql_3"),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Reverse`() {
        val expected = "SELECT REVERSE(\"N1QL is awesome\") AS n1ql,\n" +
            "       REVERSE(\"racecar\") AS palindrome"

        val actual: String = create.select(
            reverse("N1QL is awesome").alias("n1ql"),
            reverse("racecar").alias("palindrome"),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Rpad`() {
        val expected = "SELECT RPAD(\"N1QL is awesome\", 20, \" \") AS implicit_padding,\n" +
            "       RPAD(\"N1QL is awesome\", 20, \"-*\") AS repeated_padding,\n" +
            "       RPAD(\"N1QL is awesome\", 20, \"123456789\") AS truncate_padding,\n" +
            "       RPAD(\"N1QL is awesome\", 4, \"123456789\") AS truncate_string"

        val actual: String = create.select(
            rpad("N1QL is awesome", 20).alias("implicit_padding"),
            rpad("N1QL is awesome", 20, "-*").alias("repeated_padding"),
            rpad("N1QL is awesome", 20, "123456789").alias("truncate_padding"),
            rpad("N1QL is awesome", 4, "123456789").alias("truncate_string"),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Rpad With StringType And String`() {
        val expected = "SELECT RPAD(\"N1QL is awesome\", 20, \" \") AS implicit_padding,\n" +
            "       RPAD(\"N1QL is awesome\", 20, \"-*\") AS repeated_padding,\n" +
            "       RPAD(\"N1QL is awesome\", 20, \"123456789\") AS truncate_padding,\n" +
            "       RPAD(\"N1QL is awesome\", 4, \"123456789\") AS truncate_string"

        val actual: String = create.select(
            rpad("N1QL is awesome".toStringType(), 20).alias("implicit_padding"),
            rpad("N1QL is awesome".toStringType(), 20, "-*").alias("repeated_padding"),
            rpad("N1QL is awesome".toStringType(), 20, "123456789").alias("truncate_padding"),
            rpad("N1QL is awesome".toStringType(), 4, "123456789").alias("truncate_string"),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Rpad With String And StringType`() {
        val expected = "SELECT RPAD(\"N1QL is awesome\", 20, \" \") AS implicit_padding,\n" +
            "       RPAD(\"N1QL is awesome\", 20, \"-*\") AS repeated_padding,\n" +
            "       RPAD(\"N1QL is awesome\", 20, \"123456789\") AS truncate_padding,\n" +
            "       RPAD(\"N1QL is awesome\", 4, \"123456789\") AS truncate_string"

        val actual: String = create.select(
            rpad("N1QL is awesome", 20).alias("implicit_padding"),
            rpad("N1QL is awesome", 20, "-*".toStringType()).alias("repeated_padding"),
            rpad("N1QL is awesome", 20, "123456789".toStringType()).alias("truncate_padding"),
            rpad("N1QL is awesome", 4, "123456789".toStringType()).alias("truncate_string"),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Rtrim`() {
        val expected =
            "SELECT RTRIM(\"N1QL is awesome...\", \".\") AS dots, RTRIM(\"N1QL is awesome     \", \" \") AS " +
                "explicit_spaces, RTRIM(\"N1QL is awesome     \", \" \") AS implicit_spaces, RTRIM(\"N1QL is awesome\", \" \") AS no_dots"

        val actual: String = create.select(
            rtrim("N1QL is awesome...", '.').alias("dots"),
            rtrim("N1QL is awesome     ", ' ').alias("explicit_spaces"),
            rtrim("N1QL is awesome     ").alias("implicit_spaces"),
            rtrim("N1QL is awesome").alias("no_dots"),
        ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Rtrim With String And String`() {
        val expected =
            "SELECT RTRIM(\"N1QL is awesome...\", \".\") AS dots, RTRIM(\"N1QL is awesome     \", \" \") AS " +
                "explicit_spaces, RTRIM(\"N1QL is awesome     \", \" \") AS implicit_spaces, RTRIM(\"N1QL is awesome\", \" \") AS no_dots"

        val actual: String = create.select(
            rtrim("N1QL is awesome...", ".").alias("dots"),
            rtrim("N1QL is awesome     ", " ").alias("explicit_spaces"),
            rtrim("N1QL is awesome     ").alias("implicit_spaces"),
            rtrim("N1QL is awesome").alias("no_dots"),
        ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Rtrim With StringType`() {
        val expected =
            "SELECT RTRIM(\"N1QL is awesome...\", \" \") AS dots, RTRIM(\"N1QL is awesome     \", \" \") AS " +
                "explicit_spaces, RTRIM(\"N1QL is awesome     \", \" \") AS implicit_spaces, RTRIM(\"N1QL is awesome\", \" \") AS no_dots"

        val actual: String = create.select(
            rtrim("N1QL is awesome...".toStringType()).alias("dots"),
            rtrim("N1QL is awesome     ".toStringType()).alias("explicit_spaces"),
            rtrim("N1QL is awesome     ".toStringType()).alias("implicit_spaces"),
            rtrim("N1QL is awesome".toStringType()).alias("no_dots"),
        ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Rtrim With StringType And A Char`() {
        val expected =
            "SELECT RTRIM(\"N1QL is awesome...\", \".\") AS dots, RTRIM(\"N1QL is awesome     \", \" \") AS " +
                "explicit_spaces, RTRIM(\"N1QL is awesome     \", \" \") AS implicit_spaces, RTRIM(\"N1QL is awesome\", \" \") AS no_dots"

        val actual: String = create.select(
            rtrim("N1QL is awesome...".toStringType(), '.').alias("dots"),
            rtrim("N1QL is awesome     ".toStringType(), ' ').alias("explicit_spaces"),
            rtrim("N1QL is awesome     ".toStringType(), ' ').alias("implicit_spaces"),
            rtrim("N1QL is awesome".toStringType()).alias("no_dots"),
        ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Split`() {
        val expected =
            "SELECT SPLIT(\"N1QL is awesome\", \" \") AS explicit_spaces, SPLIT(\"N1QL is awesome\", \" \") AS" +
                " implicit_spaces, SPLIT(\"N1QL is awesome\", \"is\") AS split_is"

        val actual: String = create.select(
            split("N1QL is awesome", " ").alias("explicit_spaces"),
            split("N1QL is awesome").alias("implicit_spaces"),
            split("N1QL is awesome", "is").alias("split_is"),
        ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Split With StringType`() {
        val expected =
            "SELECT SPLIT(\"N1QL is awesome\", \" \") AS explicit_spaces, SPLIT(\"N1QL is awesome\", \" \") AS" +
                " implicit_spaces, SPLIT(\"N1QL is awesome\", \"is\") AS split_is"

        val actual: String = create.select(
            split("N1QL is awesome".toStringType(), " ").alias("explicit_spaces"),
            split("N1QL is awesome".toStringType()).alias("implicit_spaces"),
            split("N1QL is awesome".toStringType(), "is").alias("split_is"),
        ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Split With String And A StringType`() {
        val expected =
            "SELECT SPLIT(\"N1QL is awesome\", \" \") AS explicit_spaces, SPLIT(\"N1QL is awesome\", \" \") AS" +
                " implicit_spaces, SPLIT(\"N1QL is awesome\", \"is\") AS split_is"

        val actual: String = create.select(
            split("N1QL is awesome", " ".toStringType()).alias("explicit_spaces"),
            split("N1QL is awesome").alias("implicit_spaces"),
            split("N1QL is awesome", "is".toStringType()).alias("split_is"),
        ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Substring`() {
        val expected =
            "SELECT SUBSTR(\"N1QL is awesome\", 3, 15) AS end_of_string, SUBSTR(\"N1QL is awesome\", 3, 1) AS" +
                " single_letter, SUBSTR(\"N1QL is awesome\", 3, 3) AS three_letters"

        val actual: String = create.select(
            substr("N1QL is awesome", 3).alias("end_of_string"),
            substr("N1QL is awesome", 3, 1).alias("single_letter"),
            substr("N1QL is awesome", 3, 3).alias("three_letters"),
        ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Suffixes`() {
        val expected = "SELECT SUFFIXES(\"N1QL is awesome\")"

        val actual: String = create.select(suffixes("N1QL is awesome")).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Title`() {
        val expected = "SELECT INITCAP(\"N1QL is awesome\") AS n1ql"

        val actual: String = create.select(title("N1QL is awesome").alias("n1ql")).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Title with string Type`() {
        val expected = "SELECT INITCAP(\"N1QL is awesome\") AS n1ql"

        val actual: String = create.select(title("N1QL is awesome".toStringType()).alias("n1ql")).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Tokens`() {
        val expected = "SELECT TOKENS([\"jim@example.com, kim@example.com, http://example.com/, 408-555-1212\"], " +
            "{\"name\": false, \"specials\": true})"

        val actual: String = create.select(
            tokens(listOf("jim@example.com", "kim@example.com", "http://example.com/", "408-555-1212"), CustomTokenOptions(specials = true)),
        ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Tokens With Empty Options`() {
        val expected = "SELECT TOKENS([\"jim@example.com\"], {\"name\": false, \"specials\": false})"

        val actual: String = create.select(
            tokens(listOf("jim@example.com")),
        ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Token Options`() {
        val expected = "SELECT TOKENS([\"jim@example.com\"], {\"name\": false, \"specials\": true})"

        val actual: String = create.select(tokens(listOf("jim@example.com"), CustomTokenOptions(specials = true))).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Multiple Token Options`() {
        val expected = "SELECT TOKENS([\"jim@example.com\"], {\"name\": false, \"case\": \"UPPER\", \"specials\": true})"

        val actual: String = create.select(
            tokens(
                listOf("jim@example.com"),
                CustomTokenOptions(specials = true, case = TOKEN_CASES.UPPER),
            ),
        ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Multiple Token Options 2`() {
        val expected = "SELECT TOKENS([\"jim@example.com\"], {\"name\": false, \"case\": \"UPPER\", \"specials\": false})"

        val actual: String = create.select(
            tokens(listOf("jim@example.com"), CustomTokenOptions(specials = false, case = TOKEN_CASES.UPPER, name = false)),
        ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Trim`() {
        val expected = "SELECT TRIM(\"...N1QL is awesome...\", \".\") AS dots," +
            " TRIM(\"     N1QL is awesome     \", \" \") AS explicit_spaces," +
            " TRIM(\"     N1QL is awesome     \", \" \") AS implicit_spaces," +
            " TRIM(\"N1QL is awesome\", \" \") AS no_dots"

        val actual: String = create.select(
            trim("...N1QL is awesome...", '.')
                .alias("dots"),
            trim("     N1QL is awesome     ")
                .alias("explicit_spaces"),
            trim("     N1QL is awesome     ")
                .alias("implicit_spaces"),
            trim("N1QL is awesome")
                .alias("no_dots"),
        ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Trim With A String And A String`() {
        val expected = "SELECT TRIM(\"...N1QL is awesome...\", \"...\") AS dots," +
            " TRIM(\"     N1QL is awesome     \", \" \") AS explicit_spaces," +
            " TRIM(\"     N1QL is awesome     \", \" \") AS implicit_spaces," +
            " TRIM(\"N1QL is awesome\", \" \") AS no_dots"

        val actual: String = create.select(
            trim("...N1QL is awesome...", "...")
                .alias("dots"),
            trim("     N1QL is awesome     ", " ")
                .alias("explicit_spaces"),
            trim("     N1QL is awesome     ")
                .alias("implicit_spaces"),
            trim("N1QL is awesome")
                .alias("no_dots"),
        ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Trim With String Type`() {
        val expected = "SELECT TRIM(\"...N1QL is awesome...\", \".\") AS dots," +
            " TRIM(\"     N1QL is awesome     \", \" \") AS explicit_spaces," +
            " TRIM(\"     N1QL is awesome     \", \" \") AS implicit_spaces," +
            " TRIM(\"N1QL is awesome\", \" \") AS no_dots"

        val actual: String = create.select(
            trim("...N1QL is awesome...".toStringType(), '.')
                .alias("dots"),
            trim("     N1QL is awesome     ".toStringType())
                .alias("explicit_spaces"),
            trim("     N1QL is awesome     ".toStringType())
                .alias("implicit_spaces"),
            trim("N1QL is awesome".toStringType())
                .alias("no_dots"),
        ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Upper`() {
        val expected = "SELECT UPPER(\"N1QL is awesome\") AS n1ql"

        val actual: String = create.select(upper("N1QL is awesome").alias("n1ql")).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support string functions in where clause`() {
        val expected = "SELECT * FROM customer WHERE CONTAINS(customer.firstname, \"123\")"

        val actual: String = create.selectFrom(TestBucket.Customer).where(contains(TestBucket.Customer.firstName, "123")).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support string functions with conditions in where clause`() {
        val expected = "SELECT * FROM customer WHERE UPPER(customer.firstname) = \"VENDOLIN\""
        val actual: String =
            create.selectFrom(TestBucket.Customer).where(upper(TestBucket.Customer.firstName).isEqualTo("VENDOLIN".toStringType())).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested string functions`() {
        val expected = "SELECT CONTAINS(UPPER(\"vendolin\"), \"VEN\") AS foo"

        val actual: String = create.select(contains(upper("vendolin"), "VEN").alias("foo")).build()

        assertEquals(expected, actual)
    }
}
