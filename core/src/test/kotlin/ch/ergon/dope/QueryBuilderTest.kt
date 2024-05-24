package ch.ergon.dope

import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.unifyString
import ch.ergon.dope.resolvable.expression.alias
import ch.ergon.dope.resolvable.expression.unaliased.type.Primitive.Companion.FALSE
import ch.ergon.dope.resolvable.expression.unaliased.type.Primitive.Companion.MISSING
import ch.ergon.dope.resolvable.expression.unaliased.type.Primitive.Companion.NULL
import ch.ergon.dope.resolvable.expression.unaliased.type.Primitive.Companion.TRUE
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.and
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.not
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.or
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isGreaterOrEqualThan
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isLessOrEqualThan
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isLessThan
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isLike
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNotEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.nowStr
import ch.ergon.dope.resolvable.expression.unaliased.type.toBooleanType
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.fromable.asterisk
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class QueryBuilderTest {
    private lateinit var builder: StringBuilder
    private lateinit var create: QueryBuilder

    @BeforeTest
    fun setup() {
        builder = StringBuilder()
        create = QueryBuilder()
    }

    @Test
    fun `should Equal Simple String`() {
        val expected = "SELECT `stringField`\n" + "FROM `someBucket`"

        val actual = create.select(
            someStringField(),
        ).from(
            someBucket(),
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Equal Simple String 2`() {
        val expected = "SELECT *\n" + "  FROM `someBucket`\n" + "    WHERE `stringField` = \"Ian\"\n"

        val actual: String = create
            .selectAsterisk()
            .from(
                someBucket(),
            ).where(
                someStringField().isEqualTo("Ian".toStringType()),
            ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Where`() {
        val expected = "SELECT *\n" + "  FROM `someBucket`\n" + "    WHERE `stringField` = \"Ian\"\n"

        val actual: String = create.selectFrom(
            someBucket(),
        ).where(
            someStringField().isEqualTo("Ian".toStringType()),
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Equal Simple String 3`() {
        val expected = "SELECT *\n" + "  FROM `someBucket`\n" + "    WHERE `stringField` = \"Ian\"\n"

        val actual: String = create
            .selectAsterisk()
            .from(
                someBucket(),
            ).where(
                someStringField().isEqualTo("Ian".toStringType()),
            ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Select Distinct`() {
        val expected = "SELECT DISTINCT `stringField`, `numberField`\n" + "  FROM `someBucket`\n" + "    WHERE `stringField` = \"Ian\"\n"

        val actual: String = create.selectDistinct(
            someStringField(),
            someNumberField(),
        ).from(
            someBucket(),
        ).where(
            someStringField().isEqualTo("Ian".toStringType()),
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Alias`() {
        val expected = "SELECT `stringField` AS `firstName`\n" + "  FROM `someBucket`\n" + "    WHERE `stringField` = \"Peter\""

        val actual: String = create.select(
            someStringField().alias("firstName"),
        ).from(
            someBucket(),
        ).where(
            someStringField().isEqualTo("Peter".toStringType()),
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Alias Mixed In First`() {
        val expected =
            "SELECT `stringField` AS `FirstName`, `stringField` FROM `someBucket` WHERE `stringField` = \"Jackson\""

        val actual: String = create.select(
            someStringField().alias("FirstName"),
            someStringField(),
        ).from(someBucket()).where(
            someStringField().isEqualTo("Jackson".toStringType()),
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Alias Mixed In Last`() {
        val expected =
            "SELECT `stringField`, `stringField` AS `LastName` FROM `someBucket` WHERE `stringField` = \"Jackson\""

        val actual: String = create.select(
            someStringField(),
            someStringField().alias("LastName"),
        ).from(someBucket()).where(
            someStringField().isEqualTo("Jackson".toStringType()),
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Where With Simple Condition`() {
        val expected = "SELECT * FROM `someBucket` WHERE `numberField` < 50"

        val actual: String = create
            .selectAsterisk()
            .from(
                someBucket(),
            ).where(
                someNumberField().isLessThan(50.toNumberType()),
            ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Where With Chained Conditions`() {
        val expected = "SELECT * FROM `someBucket` WHERE (`numberField` < 50 AND `stringField` = \"Mr.\")"

        val actual: String = create
            .selectAsterisk()
            .from(
                someBucket(),
            ).where(
                someNumberField().isLessThan(50.toNumberType()).and(
                    someStringField().isEqualTo("Mr.".toStringType()),
                ),
            ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Where With Chained Conditions And Expressions`() {
        val expected = "SELECT * FROM `someBucket` WHERE (`numberField` < 50 AND `stringField` = \"Mr.\")"

        val actual: String = create
            .selectAsterisk()
            .from(
                someBucket(),
            ).where(
                someNumberField().isLessThan(
                    (45 + 5).toNumberType(),
                ).and(
                    someStringField().isEqualTo(
                        ("M" + "r.").toStringType(),
                    ),
                ),
            ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should support is not equal to with number`() {
        val expected = "SELECT * FROM `someBucket` WHERE 12 != 5"

        val actual: String = create
            .selectAsterisk()
            .from(someBucket())
            .where(
                12.toNumberType().isNotEqualTo(5.toNumberType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not equal to with numberField and number`() {
        val expected = "SELECT * FROM `someBucket` WHERE `numberField` != 5"

        val actual: String = create
            .selectAsterisk()
            .from(someBucket())
            .where(
                someNumberField().isNotEqualTo(5.toNumberType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not equal to with number and numberField`() {
        val expected = "SELECT * FROM `someBucket` WHERE 3 != `numberField`"

        val actual: String = create
            .selectAsterisk()
            .from(someBucket())
            .where(
                3.toNumberType().isNotEqualTo(someNumberField()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not equal to with string`() {
        val expected = "SELECT * FROM `someBucket` WHERE \"test\" != \"hallo\""

        val actual: String = create
            .selectAsterisk()
            .from(someBucket())
            .where(
                "test".toStringType().isNotEqualTo("hallo".toStringType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not equal to with stringField and string`() {
        val expected = "SELECT * FROM `someBucket` WHERE `stringField` != \"5\""

        val actual: String = create
            .selectAsterisk()
            .from(someBucket())
            .where(
                someStringField().isNotEqualTo("5".toStringType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with number`() {
        val expected = "SELECT * FROM `someBucket` WHERE 12 >= 5"

        val actual: String = create
            .selectAsterisk()
            .from(someBucket())
            .where(
                12.toNumberType().isGreaterOrEqualThan(5.toNumberType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with numberField and number`() {
        val expected = "SELECT * FROM `someBucket` WHERE `numberField` >= 5"

        val actual: String = create
            .selectAsterisk()
            .from(someBucket())
            .where(
                someNumberField().isGreaterOrEqualThan(5.toNumberType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with number and numberField`() {
        val expected = "SELECT * FROM `someBucket` WHERE 3 >= `numberField`"

        val actual: String = create
            .selectAsterisk()
            .from(someBucket())
            .where(
                3.toNumberType().isGreaterOrEqualThan(someNumberField()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with string`() {
        val expected = "SELECT * FROM `someBucket` WHERE \"test\" >= \"hallo\""

        val actual: String = create
            .selectAsterisk()
            .from(someBucket())
            .where(
                "test".toStringType().isGreaterOrEqualThan("hallo".toStringType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with stringField and string`() {
        val expected = "SELECT * FROM `someBucket` WHERE `stringField` >= \"5\""

        val actual: String = create
            .selectAsterisk()
            .from(someBucket())
            .where(
                someStringField().isGreaterOrEqualThan("5".toStringType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with boolean and stringField`() {
        val expected = "SELECT * FROM `someBucket` WHERE \"test\" >= `stringField`"

        val actual: String = create
            .selectAsterisk()
            .from(someBucket())
            .where(
                "test".toStringType().isGreaterOrEqualThan(someStringField()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is Less or equal to with number`() {
        val expected = "SELECT * FROM `someBucket` WHERE 12 <= 5"

        val actual: String = create
            .selectAsterisk()
            .from(someBucket())
            .where(
                12.toNumberType().isLessOrEqualThan(5.toNumberType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is Less or equal to with numberField and number`() {
        val expected = "SELECT * FROM `someBucket` WHERE `numberField` <= 5"

        val actual: String = create
            .selectAsterisk()
            .from(someBucket())
            .where(
                someNumberField().isLessOrEqualThan(5.toNumberType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is Less or equal to with number and numberField`() {
        val expected = "SELECT * FROM `someBucket` WHERE 3 <= `numberField`"

        val actual: String = create
            .selectAsterisk()
            .from(someBucket())
            .where(
                3.toNumberType().isLessOrEqualThan(someNumberField()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is Less or equal to with string`() {
        val expected = "SELECT * FROM `someBucket` WHERE \"test\" <= \"hallo\""

        val actual: String = create
            .selectAsterisk()
            .from(someBucket())
            .where(
                "test".toStringType().isLessOrEqualThan("hallo".toStringType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is Less or equal to with stringField and string`() {
        val expected = "SELECT * FROM `someBucket` WHERE `stringField` <= \"5\""

        val actual: String = create
            .selectAsterisk()
            .from(someBucket())
            .where(
                someStringField().isLessOrEqualThan("5".toStringType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Where With Many Chained Conditions`() {
        val expected = "SELECT * FROM `someBucket` WHERE (`numberField` < 50 AND (`stringField` = \"Mr.\" AND `stringField` = \"friend\"))"

        val actual: String = create
            .selectAsterisk()
            .from(
                someBucket(),
            ).where(
                someNumberField().isLessThan(
                    (45 + 5).toNumberType(),
                ).and(
                    someStringField().isEqualTo(
                        "Mr.".toStringType(),
                    ).and(
                        someStringField().isEqualTo("friend".toStringType()),
                    ),
                ),
            ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Where With Like`() {
        val expected = "SELECT `stringField` FROM `someBucket` WHERE `email` LIKE \"%@yahoo.com\""

        val actual: String = create.select(
            someStringField(),
        ).from(
            someBucket(),
        ).where(
            someStringField("email").isLike(
                "%@yahoo.com".toStringType(),
            ),
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Where With Like Chained`() {
        val expected = "SELECT `stringField`, `numberField` FROM `someBucket` WHERE (`email` LIKE \"%@gmail.com\" AND `numberField` = 46)"

        val actual: String = create.select(
            someStringField(),
            someNumberField(),
        ).from(
            someBucket(),
        ).where(
            someStringField("email").isLike(
                "%@gmail.com".toStringType(),
            ).and(
                someNumberField().isEqualTo(46.toNumberType()),
            ),
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Null`() {
        val expected = "SELECT NULL"

        val actual: String = create.select(
            NULL,
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Missing`() {
        val expected = "SELECT MISSING"

        val actual: String = create.select(
            MISSING,
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Long Complex Query`() {
        val expected = "SELECT ((1 = 1 AND 2 = 2) AND 3 = 3) AS `what` FROM `someBucket` WHERE (1 = 1 AND \"run\" = \"run\")"

        val actual: String = create.select(
            1.toNumberType().isEqualTo(
                1.toNumberType(),
            ).and(
                2.toNumberType().isEqualTo(
                    2.toNumberType(),
                ),
            ).and(
                3.toNumberType().isEqualTo(
                    3.toNumberType(),
                ),
            ).alias("what"),
        ).from(
            someBucket(),
        ).where(
            1.toNumberType().isEqualTo(
                1.toNumberType(),
            ).and(
                "run".toStringType().isEqualTo(
                    "run".toStringType(),
                ),
            ),
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Simple Boolean Value True`() {
        val expected = "SELECT TRUE"

        val actual: String = create.select(TRUE).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Simple Boolean Value False`() {
        val expected = "SELECT FALSE"

        val actual: String = create.select(FALSE).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Simple String Value False`() {
        val expected = "SELECT \"FALSE\""

        val actual: String = create.select("FALSE".toStringType()).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Separate Between Boolean And String`() {
        val expected = "SELECT TRUE = TRUE"

        val actual: String = create.select(
            TRUE.isEqualTo(
                TRUE,
            ),
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support And with two types`() {
        val expected = "SELECT (TRUE AND FALSE)"

        val actual: String = create.select(
            TRUE.and(
                FALSE,
            ),
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support And with a boolean type and boolean`() {
        val expected = "SELECT (TRUE AND FALSE)"

        val actual: String = create.select(
            TRUE.and(
                false,
            ),
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support And with a boolean and boolean type`() {
        val expected = "SELECT (TRUE AND FALSE)"

        val actual: String = create.select(
            true.and(
                FALSE,
            ),
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Or with two types`() {
        val expected = "SELECT (TRUE OR FALSE)"

        val actual: String = create.select(
            TRUE.or(
                FALSE,
            ),
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Or with boolean type and boolean`() {
        val expected = "SELECT (TRUE OR FALSE)"

        val actual: String = create.select(
            TRUE.or(
                false,
            ),
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Or with boolean and boolean type`() {
        val expected = "SELECT (TRUE OR FALSE)"

        val actual: String = create.select(
            true.or(
                FALSE,
            ),
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Not 1`() {
        val expected = "SELECT NOT TRUE"

        val actual: String = create.select(not(TRUE)).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Not 2`() {
        val expected = "SELECT NOT (TRUE AND FALSE)"

        val actual: String = create.select(
            not(
                TRUE.and(
                    FALSE,
                ),
            ),
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Not 3`() {
        val expected = "SELECT NOT (TRUE AND (FALSE AND TRUE))"

        val actual: String = create.select(
            not(
                TRUE.and(
                    FALSE.and(
                        TRUE,
                    ),
                ),
            ),
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Equals with two types`() {
        val expected = "TRUE = FALSE"

        val actual: String = TRUE.isEqualTo(
            FALSE,
        ).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `Should support the NOW_STR function`() {
        val expected = "SELECT NOW_STR() AS `full_date`, NOW_STR(\"invalid date\") AS `invalid_date`, " +
            "NOW_STR(\"1111-11-11\") " +
            "AS `short_date`"

        val actual = create.select(
            nowStr().alias("full_date"),
            nowStr("invalid date").alias("invalid_date"),
            nowStr("1111-11-11").alias("short_date"),
        ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select raw`() {
        val expected = "SELECT RAW NOT (TRUE OR FALSE)"

        val actual = create.selectRaw(
            not(TRUE.or(FALSE)),
        ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select raw with field`() {
        val expected = "SELECT RAW `name` FROM `someBucket`"

        val actual = create.selectRaw(
            someStringField("name"),
        ).from(
            someBucket(),
        ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to boolean type`() {
        val getSomething = { "something" }
        val expected = "TRUE"

        val actual = (getSomething() == "something").toBooleanType().toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support selecting all Fields from bucket`() {
        val someBucket = someBucket()
        val expected = "SELECT `someBucket`.* FROM `someBucket`"

        val actual = create
            .select(someBucket.asterisk())
            .from(someBucket)
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support selecting all Fields from aliased bucket`() {
        val someBucket = someBucket().alias("alias")
        val expected = "SELECT `alias`.* FROM `someBucket` AS `alias`"

        val actual = create
            .select(someBucket.asterisk())
            .from(someBucket)
            .build().queryString

        assertEquals(expected, actual)
    }
}
