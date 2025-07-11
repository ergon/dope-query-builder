package ch.ergon.dope.buildTest

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.helper.unifyString
import ch.ergon.dope.resolvable.expression.type.function.comparison.greatestOf
import ch.ergon.dope.resolvable.expression.type.function.comparison.leastOf
import ch.ergon.dope.resolvable.expression.type.logic.and
import ch.ergon.dope.resolvable.expression.type.logic.or
import ch.ergon.dope.resolvable.expression.type.relational.between
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.relational.isGreaterOrEqualThan
import ch.ergon.dope.resolvable.expression.type.relational.isGreaterThan
import ch.ergon.dope.resolvable.expression.type.relational.isLessOrEqualThan
import ch.ergon.dope.resolvable.expression.type.relational.isLessThan
import ch.ergon.dope.resolvable.expression.type.relational.isLike
import ch.ergon.dope.resolvable.expression.type.relational.isMissing
import ch.ergon.dope.resolvable.expression.type.relational.isNotEqualTo
import ch.ergon.dope.resolvable.expression.type.relational.isNotLike
import ch.ergon.dope.resolvable.expression.type.relational.isNotMissing
import ch.ergon.dope.resolvable.expression.type.relational.isNotNull
import ch.ergon.dope.resolvable.expression.type.relational.isNotValued
import ch.ergon.dope.resolvable.expression.type.relational.isNull
import ch.ergon.dope.resolvable.expression.type.relational.isValued
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class BooleanComparatorTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support greater than number`() {
        val expected = "SELECT * FROM `someBucket` WHERE `numberField` > 5"

        val actual = QueryBuilder
            .selectFrom(someBucket())
            .where(
                someNumberField().isGreaterThan(5.toDopeType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support number is greater than a number`() {
        val expected = "SELECT * FROM `someBucket` WHERE 2 > 4"

        val actual = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket(),
            ).where(
                2.isGreaterThan(4),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support number is greater than a numberType`() {
        val expected = "SELECT * FROM `someBucket` WHERE 2 > 4"

        val actual = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket(),
            ).where(
                2.isGreaterThan(4.toDopeType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support numberType is greater than a number`() {
        val expected = "SELECT * FROM `someBucket` WHERE 2 > 4"

        val actual = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket(),
            ).where(
                2.toDopeType().isGreaterThan(4),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greater than with a field and string`() {
        val expected = "SELECT * FROM `someBucket` WHERE `stringField` > \"a\""

        val actual = QueryBuilder
            .selectFrom(someBucket())
            .where(
                someStringField().isGreaterThan("a".toDopeType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support string is greater than a string`() {
        val expected = "SELECT * FROM `someBucket` WHERE \"hallo\" > \"test\""

        val actual = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket(),
            ).where(
                "hallo".isGreaterThan("test"),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support stringType is greater than a string`() {
        val expected = "SELECT * FROM `someBucket` WHERE \"hallo\" > \"test\""

        val actual = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket(),
            ).where(
                "hallo".toDopeType().isGreaterThan("test"),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support string is greater than a stringType`() {
        val expected = "SELECT * FROM `someBucket` WHERE \"hallo\" > \"test\""

        val actual = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket(),
            ).where(
                "hallo".isGreaterThan("test".toDopeType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greater than with a string and a field`() {
        val expected = "SELECT * FROM `someBucket` WHERE \"a\" > `stringField`"

        val actual = QueryBuilder
            .selectFrom(someBucket())
            .where(
                "a".toDopeType().isGreaterThan(someStringField()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support less than with a field and a number`() {
        val expected = "SELECT * FROM `someBucket` WHERE `numberField` < 5"

        val actual = QueryBuilder
            .selectFrom(
                someBucket(),
            ).where(
                someNumberField().isLessThan(5.toDopeType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support less than with a number and a field`() {
        val expected = "SELECT * FROM `someBucket` WHERE 5 < `numberField`"

        val actual = QueryBuilder
            .selectFrom(someBucket())
            .where(
                5.toDopeType().isLessThan(someNumberField()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support number is less than a number`() {
        val expected = "SELECT * FROM `someBucket` WHERE 5 < 7"

        val actual = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket(),
            ).where(
                5.isLessThan(7),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support numberType is less than a number`() {
        val expected = "SELECT * FROM `someBucket` WHERE 5 < 7"

        val actual = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket(),
            ).where(
                5.toDopeType().isLessThan(7),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support number is less than a numberType`() {
        val expected = "SELECT * FROM `someBucket` WHERE 5 < 7"

        val actual = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket(),
            ).where(
                5.isLessThan(7.toDopeType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is less than with a string`() {
        val expected = "SELECT * FROM `someBucket` WHERE `stringField` < \"a\""

        val actual = QueryBuilder
            .selectFrom(someBucket())
            .where(
                someStringField().isLessThan("a".toDopeType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support string is less than a string`() {
        val expected = "SELECT * FROM `someBucket` WHERE \"hallo\" < \"test\""

        val actual = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket(),
            ).where(
                "hallo".isLessThan("test"),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support stringType is less than a string`() {
        val expected = "SELECT * FROM `someBucket` WHERE \"hallo\" < \"test\""

        val actual = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket(),
            ).where(
                "hallo".toDopeType().isLessThan("test"),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support string is less than a stringType`() {
        val expected = "SELECT * FROM `someBucket` WHERE \"hallo\" < \"test\""

        val actual = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket(),
            ).where(
                "hallo".isLessThan("test".toDopeType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support like with a string`() {
        val expected = "SELECT * FROM `someBucket` WHERE `stringField` LIKE \"_b%\""

        val actual = QueryBuilder
            .selectFrom(someBucket())
            .where(
                someStringField().isLike("_b%"),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support like with a type`() {
        val expected = "SELECT * FROM `someBucket` WHERE `stringField` LIKE \"_b%\""

        val actual = QueryBuilder
            .selectFrom(someBucket())
            .where(
                someStringField().isLike("_b%".toDopeType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not like with a string`() {
        val expected = "SELECT * FROM `someBucket` WHERE `email` NOT LIKE \"%@yahoo.com\""

        val actual: String = QueryBuilder
            .selectFrom(someBucket())
            .where(
                someStringField("email").isNotLike("%@yahoo.com"),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not like with a type`() {
        val expected = "SELECT * FROM `someBucket` WHERE `stringField` NOT LIKE \"_b%\""

        val actual = QueryBuilder
            .selectFrom(someBucket())
            .where(
                someStringField().isNotLike("_b%".toDopeType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is null`() {
        val expected = "SELECT * FROM `someBucket` WHERE `stringField` IS NULL"

        val actual = QueryBuilder
            .selectFrom(someBucket())
            .where(
                someStringField().isNull(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not null`() {
        val expected = "SELECT * FROM `someBucket` WHERE `stringField` IS NOT NULL"

        val actual = QueryBuilder
            .selectFrom(someBucket())
            .where(
                someStringField().isNotNull(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is missing`() {
        val expected = "SELECT * FROM `someBucket` WHERE `stringField` IS MISSING"

        val actual = QueryBuilder
            .selectFrom(someBucket())
            .where(
                someStringField().isMissing(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not missing`() {
        val expected = "SELECT * FROM `someBucket` WHERE `stringField` IS NOT MISSING"

        val actual = QueryBuilder
            .selectFrom(someBucket())
            .where(
                someStringField().isNotMissing(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is valued`() {
        val expected = "SELECT * FROM `someBucket` WHERE `stringField` IS VALUED"

        val actual = QueryBuilder
            .selectFrom(someBucket())
            .where(
                someStringField().isValued(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not valued`() {
        val expected = "SELECT * FROM `someBucket` WHERE `stringField` IS NOT VALUED"

        val actual = QueryBuilder
            .selectFrom(someBucket())
            .where(
                someStringField().isNotValued(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not equal to with number`() {
        val expected = "SELECT * FROM `someBucket` WHERE 12 != 5"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someBucket())
            .where(
                12.toDopeType().isNotEqualTo(5.toDopeType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support number is not equal to with number`() {
        val expected = "SELECT * FROM `someBucket` WHERE 12 != 5"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someBucket())
            .where(
                12.isNotEqualTo(5),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support numberType is not equal to with number`() {
        val expected = "SELECT * FROM `someBucket` WHERE 12 != 5"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someBucket())
            .where(
                12.toDopeType().isNotEqualTo(5),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support number is not equal to with numberType`() {
        val expected = "SELECT * FROM `someBucket` WHERE 12 != 5"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someBucket())
            .where(
                12.isNotEqualTo(5.toDopeType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not equal to with numberField and number`() {
        val expected = "SELECT * FROM `someBucket` WHERE `numberField` != 5"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someBucket())
            .where(
                someNumberField().isNotEqualTo(5.toDopeType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not equal to with number and numberField`() {
        val expected = "SELECT * FROM `someBucket` WHERE 3 != `numberField`"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someBucket())
            .where(
                3.toDopeType().isNotEqualTo(someNumberField()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not equal to with string`() {
        val expected = "SELECT * FROM `someBucket` WHERE \"test\" != \"hallo\""

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someBucket())
            .where(
                "test".toDopeType().isNotEqualTo("hallo".toDopeType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not equal to with stringField and string`() {
        val expected = "SELECT * FROM `someBucket` WHERE `stringField` != \"5\""

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someBucket())
            .where(
                someStringField().isNotEqualTo("5".toDopeType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with number`() {
        val expected = "SELECT * FROM `someBucket` WHERE 12 >= 5"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someBucket())
            .where(
                12.toDopeType().isGreaterOrEqualThan(5.toDopeType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support number is greater or equal to with numberType`() {
        val expected = "SELECT * FROM `someBucket` WHERE 12 >= 5"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someBucket())
            .where(
                12.isGreaterOrEqualThan(5.toDopeType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support numberType is greater or equal to with number`() {
        val expected = "SELECT * FROM `someBucket` WHERE 12 >= 5"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someBucket())
            .where(
                12.toDopeType().isGreaterOrEqualThan(5),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support string is greater or equal to with stringType`() {
        val expected = "SELECT * FROM `someBucket` WHERE \"hallo\" >= \"test\""

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someBucket())
            .where(
                "hallo".isGreaterOrEqualThan("test".toDopeType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support stringType is greater or equal to with string`() {
        val expected = "SELECT * FROM `someBucket` WHERE `stringField` >= \"test\""

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someBucket())
            .where(
                someStringField().isGreaterOrEqualThan("test"),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with numberField and number`() {
        val expected = "SELECT * FROM `someBucket` WHERE `numberField` >= 5"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someBucket())
            .where(
                someNumberField().isGreaterOrEqualThan(5.toDopeType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with number and numberField`() {
        val expected = "SELECT * FROM `someBucket` WHERE 3 >= `numberField`"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someBucket())
            .where(
                3.toDopeType().isGreaterOrEqualThan(someNumberField()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with string`() {
        val expected = "SELECT * FROM `someBucket` WHERE \"test\" >= \"hallo\""

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someBucket())
            .where(
                "test".toDopeType().isGreaterOrEqualThan("hallo".toDopeType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with stringField and string`() {
        val expected = "SELECT * FROM `someBucket` WHERE `stringField` >= \"5\""

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someBucket())
            .where(
                someStringField().isGreaterOrEqualThan("5".toDopeType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with boolean and stringField`() {
        val expected = "SELECT * FROM `someBucket` WHERE \"test\" >= `stringField`"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someBucket())
            .where(
                "test".toDopeType().isGreaterOrEqualThan(someStringField()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is Less or equal to with number`() {
        val expected = "SELECT * FROM `someBucket` WHERE 12 <= 5"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someBucket())
            .where(
                12.toDopeType().isLessOrEqualThan(5.toDopeType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is Less or equal to with numberField and number`() {
        val expected = "SELECT * FROM `someBucket` WHERE `numberField` <= 5"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someBucket())
            .where(
                someNumberField().isLessOrEqualThan(5.toDopeType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is Less or equal to with number and numberField`() {
        val expected = "SELECT * FROM `someBucket` WHERE 3 <= `numberField`"

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someBucket())
            .where(
                3.toDopeType().isLessOrEqualThan(someNumberField()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is Less or equal to with string`() {
        val expected = "SELECT * FROM `someBucket` WHERE \"test\" <= \"hallo\""

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someBucket())
            .where(
                "test".toDopeType().isLessOrEqualThan("hallo".toDopeType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is Less or equal to with stringField and string`() {
        val expected = "SELECT * FROM `someBucket` WHERE `stringField` <= \"5\""

        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(someBucket())
            .where(
                someStringField().isLessOrEqualThan("5".toDopeType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Where With Like`() {
        val expected = "SELECT `stringField`, `email` FROM `someBucket` WHERE `email` LIKE \"%@yahoo.com\""

        val actual: String = QueryBuilder.select(
            someStringField(),
            someStringField("email"),
        ).from(
            someBucket(),
        ).where(
            someStringField("email").isLike(
                "%@yahoo.com".toDopeType(),
            ),
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Where With Like Chained`() {
        val expected = "SELECT `stringField`, `numberField` FROM `someBucket` WHERE (`email` LIKE \"%@gmail.com\" AND `numberField` = 46)"

        val actual: String = QueryBuilder.select(
            someStringField(),
            someNumberField(),
        ).from(
            someBucket(),
        ).where(
            someStringField("email").isLike(
                "%@gmail.com".toDopeType(),
            ).and(
                someNumberField().isEqualTo(46.toDopeType()),
            ),
        ).build().queryString

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should support Number isEqualTo Number`() {
        val expected = "5 = 5"

        val actual: String = 5.isEqualTo(5).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support Number isEqualTo NumberType`() {
        val numberExpression = 5.toDopeType()
        val expected = "5 = 5"

        val actual: String = 5.isEqualTo(numberExpression).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NumberType isEqualTo Number`() {
        val numberExpression = 5.toDopeType()
        val expected = "5 = 5"

        val actual: String = numberExpression.isEqualTo(5).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support String isEqualTo String`() {
        val expected = "\"hello\" = \"hello\""

        val actual: String = "hello".isEqualTo("hello").toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support String isEqualTo StringType`() {
        val stringExpression = "hello".toDopeType()
        val expected = "\"hello\" = \"hello\""

        val actual: String = "hello".isEqualTo(stringExpression).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support StringType isEqualTo String`() {
        val stringExpression = "hello".toDopeType()
        val expected = "\"hello\" = \"hello\""

        val actual: String = stringExpression.isEqualTo("hello").toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support Boolean isEqualTo Boolean`() {
        val expected = "TRUE = TRUE"

        val actual: String = true.isEqualTo(true).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support Boolean isEqualTo BooleanType`() {
        val booleanExpression = true.toDopeType()
        val expected = "TRUE = TRUE"

        val actual: String = true.isEqualTo(booleanExpression).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support BooleanType isEqualTo Boolean`() {
        val booleanExpression = true.toDopeType()
        val expected = "TRUE = TRUE"

        val actual: String = booleanExpression.isEqualTo(true).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should add brackets to one Boolean comparator`() {
        val expected = "SELECT * FROM `someBucket` WHERE (TRUE AND TRUE)"
        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket(),
            )
            .where(
                true.toDopeType().and(true),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should add first bracket pair to two Boolean comparators`() {
        val expected = "SELECT * FROM `someBucket` WHERE ((TRUE AND TRUE) OR FALSE)"
        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket(),
            )
            .where(
                true.toDopeType().and(true).or(false),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should add second bracket pair to two Boolean comparators`() {
        val expected = "SELECT * FROM `someBucket` WHERE (TRUE AND (TRUE OR FALSE))"
        val actual: String = QueryBuilder
            .selectAsterisk()
            .from(
                someBucket(),
            )
            .where(
                true.toDopeType().and(true.toDopeType().or(false)),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support between comparison`() {
        val expected = "SELECT * FROM `someBucket` WHERE `numberField` BETWEEN 1 AND 10"

        val actual = QueryBuilder
            .selectFrom(
                someBucket(),
            )
            .where(
                someNumberField().between(1.toDopeType(), 10.toDopeType()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greatest comparison`() {
        val expected = "SELECT * FROM `someBucket` WHERE GREATEST(`numberField`, `anotherNumberField`) > 5"

        val actual = QueryBuilder
            .selectFrom(
                someBucket(),
            )
            .where(
                greatestOf(
                    someNumberField(),
                    someNumberField("anotherNumberField"),
                ).isGreaterThan(
                    someNumber(),
                ),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support least comparison`() {
        val expected = "SELECT * FROM `someBucket` WHERE LEAST(`numberField`, `anotherNumberField`) <= 5"

        val actual = QueryBuilder
            .selectFrom(
                someBucket(),
            )
            .where(
                leastOf(
                    someNumberField(),
                    someNumberField("anotherNumberField"),
                ).isLessOrEqualThan(
                    someNumber(),
                ),
            ).build().queryString

        assertEquals(expected, actual)
    }
}
