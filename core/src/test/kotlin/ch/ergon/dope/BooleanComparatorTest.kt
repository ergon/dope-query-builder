package ch.ergon.dope

import ch.ergon.dope.helper.unifyString
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.and
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isGreaterOrEqualThan
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isGreaterThan
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isLessOrEqualThan
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isLessThan
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isLike
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isMissing
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNotEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNotLike
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNotMissing
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNotNull
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNotValued
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNull
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isValued
import ch.ergon.dope.resolvable.expression.unaliased.type.toBooleanType
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.BeforeTest
import kotlin.test.Test

class BooleanComparatorTest {
    private lateinit var builder: StringBuilder
    private lateinit var create: DSLContext

    @BeforeTest
    fun setup() {
        builder = StringBuilder()
        create = DSLContext()
    }

    @Test
    fun `should support greater than number`() {
        val expected = "SELECT * FROM someBucket WHERE numField > 5"

        val actual = create
            .selectFrom(someBucket())
            .where(
                someNumberField().isGreaterThan(5.toNumberType()),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support number is greater than a number`() {
        val expected = "SELECT * FROM someBucket WHERE 2 > 4"

        val actual = create
            .selectAll()
            .from(
                someBucket(),
            ).where(
                2.isGreaterThan(4),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support number is greater than a numberType`() {
        val expected = "SELECT * FROM someBucket WHERE 2 > 4"

        val actual = create
            .selectAll()
            .from(
                someBucket(),
            ).where(
                2.isGreaterThan(4.toNumberType()),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support numberType is greater than a number`() {
        val expected = "SELECT * FROM someBucket WHERE 2 > 4"

        val actual = create
            .selectAll()
            .from(
                someBucket(),
            ).where(
                2.toNumberType().isGreaterThan(4),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greater than with a field and string`() {
        val expected = "SELECT * FROM someBucket WHERE strField > \"a\""

        val actual = create
            .selectFrom(someBucket())
            .where(
                someStringField().isGreaterThan("a".toStringType()),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support string is greater than a string`() {
        val expected = "SELECT * FROM someBucket WHERE \"hallo\" > \"test\""

        val actual = create
            .selectAll()
            .from(
                someBucket(),
            ).where(
                "hallo".isGreaterThan("test"),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support stringType is greater than a string`() {
        val expected = "SELECT * FROM someBucket WHERE \"hallo\" > \"test\""

        val actual = create
            .selectAll()
            .from(
                someBucket(),
            ).where(
                "hallo".toStringType().isGreaterThan("test"),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support string is greater than a stringType`() {
        val expected = "SELECT * FROM someBucket WHERE \"hallo\" > \"test\""

        val actual = create
            .selectAll()
            .from(
                someBucket(),
            ).where(
                "hallo".isGreaterThan("test".toStringType()),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greater than with a string and a field`() {
        val expected = "SELECT * FROM someBucket WHERE \"a\" > strField"

        val actual = create
            .selectFrom(someBucket())
            .where(
                "a".toStringType().isGreaterThan(someStringField()),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support less than with a field and a number`() {
        val expected = "SELECT * FROM someBucket WHERE numField < 5"

        val actual = create
            .selectFrom(
                someBucket(),
            ).where(
                someNumberField().isLessThan(5.toNumberType()),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support less than with a number and a field`() {
        val expected = "SELECT * FROM someBucket WHERE 5 < numField"

        val actual = create
            .selectFrom(someBucket())
            .where(
                5.toNumberType().isLessThan(someNumberField()),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support number is less than a number`() {
        val expected = "SELECT * FROM someBucket WHERE 5 < 7"

        val actual = create
            .selectAll()
            .from(
                someBucket(),
            ).where(
                5.isLessThan(7),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support numberType is less than a number`() {
        val expected = "SELECT * FROM someBucket WHERE 5 < 7"

        val actual = create
            .selectAll()
            .from(
                someBucket(),
            ).where(
                5.toNumberType().isLessThan(7),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support number is less than a numberType`() {
        val expected = "SELECT * FROM someBucket WHERE 5 < 7"

        val actual = create
            .selectAll()
            .from(
                someBucket(),
            ).where(
                5.isLessThan(7.toNumberType()),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is less than with a string`() {
        val expected = "SELECT * FROM someBucket WHERE strField < \"a\""

        val actual = create
            .selectFrom(someBucket())
            .where(
                someStringField().isLessThan("a".toStringType()),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support string is less than a string`() {
        val expected = "SELECT * FROM someBucket WHERE \"hallo\" < \"test\""

        val actual = create
            .selectAll()
            .from(
                someBucket(),
            ).where(
                "hallo".isLessThan("test"),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support stringType is less than a string`() {
        val expected = "SELECT * FROM someBucket WHERE \"hallo\" < \"test\""

        val actual = create
            .selectAll()
            .from(
                someBucket(),
            ).where(
                "hallo".toStringType().isLessThan("test"),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support string is less than a stringType`() {
        val expected = "SELECT * FROM someBucket WHERE \"hallo\" < \"test\""

        val actual = create
            .selectAll()
            .from(
                someBucket(),
            ).where(
                "hallo".isLessThan("test".toStringType()),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support like with a string`() {
        val expected = "SELECT * FROM someBucket WHERE strField LIKE \"_b%\""

        val actual = create
            .selectFrom(someBucket())
            .where(
                someStringField().isLike("_b%"),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support like with a type`() {
        val expected = "SELECT * FROM someBucket WHERE strField LIKE \"_b%\""

        val actual = create
            .selectFrom(someBucket())
            .where(
                someStringField().isLike("_b%".toStringType()),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not like with a string`() {
        val expected = "SELECT * FROM someBucket WHERE email NOT LIKE \"%@yahoo.com\""

        val actual: String = create
            .selectFrom(someBucket())
            .where(
                someStringField("email").isNotLike("%@yahoo.com"),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support not like with a type`() {
        val expected = "SELECT * FROM someBucket WHERE strField NOT LIKE \"_b%\""

        val actual = create
            .selectFrom(someBucket())
            .where(
                someStringField().isNotLike("_b%".toStringType()),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is null`() {
        val expected = "SELECT * FROM someBucket WHERE strField IS NULL"

        val actual = create
            .selectFrom(someBucket())
            .where(
                someStringField().isNull(),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not null`() {
        val expected = "SELECT * FROM someBucket WHERE strField IS NOT NULL"

        val actual = create
            .selectFrom(someBucket())
            .where(
                someStringField().isNotNull(),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is missing`() {
        val expected = "SELECT * FROM someBucket WHERE strField IS MISSING"

        val actual = create
            .selectFrom(someBucket())
            .where(
                someStringField().isMissing(),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not missing`() {
        val expected = "SELECT * FROM someBucket WHERE strField IS NOT MISSING"

        val actual = create
            .selectFrom(someBucket())
            .where(
                someStringField().isNotMissing(),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is valued`() {
        val expected = "SELECT * FROM someBucket WHERE strField IS VALUED"

        val actual = create
            .selectFrom(someBucket())
            .where(
                someStringField().isValued(),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not valued`() {
        val expected = "SELECT * FROM someBucket WHERE strField IS NOT VALUED"

        val actual = create
            .selectFrom(someBucket())
            .where(
                someStringField().isNotValued(),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not equal to with number`() {
        val expected = "SELECT * FROM someBucket WHERE 12 != 5"

        val actual: String = create
            .selectAll()
            .from(someBucket())
            .where(
                12.toNumberType().isNotEqualTo(5.toNumberType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support number is not equal to with number`() {
        val expected = "SELECT * FROM someBucket WHERE 12 != 5"

        val actual: String = create
            .selectAll()
            .from(someBucket())
            .where(
                12.isNotEqualTo(5),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support numberType is not equal to with number`() {
        val expected = "SELECT * FROM someBucket WHERE 12 != 5"

        val actual: String = create
            .selectAll()
            .from(someBucket())
            .where(
                12.toNumberType().isNotEqualTo(5),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support number is not equal to with numberType`() {
        val expected = "SELECT * FROM someBucket WHERE 12 != 5"

        val actual: String = create
            .selectAll()
            .from(someBucket())
            .where(
                12.isNotEqualTo(5.toNumberType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is not equal to with numberField and number`() {
        val expected = "SELECT * FROM someBucket WHERE numField != 5"

        val actual: String = create
            .selectAll()
            .from(someBucket())
            .where(
                someNumberField().isNotEqualTo(5.toNumberType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is not equal to with number and numberField`() {
        val expected = "SELECT * FROM someBucket WHERE 3 != numField"

        val actual: String = create
            .selectAll()
            .from(someBucket())
            .where(
                3.toNumberType().isNotEqualTo(someNumberField()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is not equal to with string`() {
        val expected = "SELECT * FROM someBucket WHERE \"test\" != \"hallo\""

        val actual: String = create
            .selectAll()
            .from(someBucket())
            .where(
                "test".toStringType().isNotEqualTo("hallo".toStringType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is not equal to with stringField and string`() {
        val expected = "SELECT * FROM someBucket WHERE strField != \"5\""

        val actual: String = create
            .selectAll()
            .from(someBucket())
            .where(
                someStringField().isNotEqualTo("5".toStringType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with number`() {
        val expected = "SELECT * FROM someBucket WHERE 12 >= 5"

        val actual: String = create
            .selectAll()
            .from(someBucket())
            .where(
                12.toNumberType().isGreaterOrEqualThan(5.toNumberType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support number is greater or equal to with numberType`() {
        val expected = "SELECT * FROM someBucket WHERE 12 >= 5"

        val actual: String = create
            .selectAll()
            .from(someBucket())
            .where(
                12.isGreaterOrEqualThan(5.toNumberType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support numberType is greater or equal to with number`() {
        val expected = "SELECT * FROM someBucket WHERE 12 >= 5"

        val actual: String = create
            .selectAll()
            .from(someBucket())
            .where(
                12.toNumberType().isGreaterOrEqualThan(5),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support string is greater or equal to with stringType`() {
        val expected = "SELECT * FROM someBucket WHERE \"hallo\" >= \"test\""

        val actual: String = create
            .selectAll()
            .from(someBucket())
            .where(
                "hallo".isGreaterOrEqualThan("test".toStringType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support stringType is greater or equal to with string`() {
        val expected = "SELECT * FROM someBucket WHERE strField >= \"test\""

        val actual: String = create
            .selectAll()
            .from(someBucket())
            .where(
                someStringField().isGreaterOrEqualThan("test"),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with numberField and number`() {
        val expected = "SELECT * FROM someBucket WHERE numField >= 5"

        val actual: String = create
            .selectAll()
            .from(someBucket())
            .where(
                someNumberField().isGreaterOrEqualThan(5.toNumberType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with number and numberField`() {
        val expected = "SELECT * FROM someBucket WHERE 3 >= numField"

        val actual: String = create
            .selectAll()
            .from(someBucket())
            .where(
                3.toNumberType().isGreaterOrEqualThan(someNumberField()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with string`() {
        val expected = "SELECT * FROM someBucket WHERE \"test\" >= \"hallo\""

        val actual: String = create
            .selectAll()
            .from(someBucket())
            .where(
                "test".toStringType().isGreaterOrEqualThan("hallo".toStringType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with stringField and string`() {
        val expected = "SELECT * FROM someBucket WHERE strField >= \"5\""

        val actual: String = create
            .selectAll()
            .from(someBucket())
            .where(
                someStringField().isGreaterOrEqualThan("5".toStringType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with boolean and stringField`() {
        val expected = "SELECT * FROM someBucket WHERE \"test\" >= strField"

        val actual: String = create
            .selectAll()
            .from(someBucket())
            .where(
                "test".toStringType().isGreaterOrEqualThan(someStringField()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is Less or equal to with number`() {
        val expected = "SELECT * FROM someBucket WHERE 12 <= 5"

        val actual: String = create
            .selectAll()
            .from(someBucket())
            .where(
                12.toNumberType().isLessOrEqualThan(5.toNumberType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is Less or equal to with numberField and number`() {
        val expected = "SELECT * FROM someBucket WHERE numField <= 5"

        val actual: String = create
            .selectAll()
            .from(someBucket())
            .where(
                someNumberField().isLessOrEqualThan(5.toNumberType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is Less or equal to with number and numberField`() {
        val expected = "SELECT * FROM someBucket WHERE 3 <= numField"

        val actual: String = create
            .selectAll()
            .from(someBucket())
            .where(
                3.toNumberType().isLessOrEqualThan(someNumberField()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is Less or equal to with string`() {
        val expected = "SELECT * FROM someBucket WHERE \"test\" <= \"hallo\""

        val actual: String = create
            .selectAll()
            .from(someBucket())
            .where(
                "test".toStringType().isLessOrEqualThan("hallo".toStringType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is Less or equal to with stringField and string`() {
        val expected = "SELECT * FROM someBucket WHERE strField <= \"5\""

        val actual: String = create
            .selectAll()
            .from(someBucket())
            .where(
                someStringField().isLessOrEqualThan("5".toStringType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should Support Where With Like`() {
        val expected = "SELECT strField, email FROM someBucket WHERE email LIKE \"%@yahoo.com\""

        val actual: String = create.select(
            someStringField(),
            someStringField("email"),
        ).from(
            someBucket(),
        ).where(
            someStringField("email").isLike(
                "%@yahoo.com".toStringType(),
            ),
        ).build()

        kotlin.test.assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Where With Like Chained`() {
        val expected = "SELECT strField, numField FROM someBucket WHERE email LIKE \"%@gmail.com\" AND numField = 46"

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
        ).build()

        kotlin.test.assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should support Number isEqualTo Number`() {
        val expected = "5 = 5"

        val actual: String = 5.isEqualTo(5).toQueryString()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support Number isEqualTo NumberType`() {
        val numberExpression = 5.toNumberType()
        val expected = "5 = 5"

        val actual: String = 5.isEqualTo(numberExpression).toQueryString()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support NumberType isEqualTo Number`() {
        val numberExpression = 5.toNumberType()
        val expected = "5 = 5"

        val actual: String = numberExpression.isEqualTo(5).toQueryString()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support String isEqualTo String`() {
        val expected = "\"hello\" = \"hello\""

        val actual: String = "hello".isEqualTo("hello").toQueryString()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support String isEqualTo StringType`() {
        val stringExpression = "hello".toStringType()
        val expected = "\"hello\" = \"hello\""

        val actual: String = "hello".isEqualTo(stringExpression).toQueryString()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support StringType isEqualTo String`() {
        val stringExpression = "hello".toStringType()
        val expected = "\"hello\" = \"hello\""

        val actual: String = stringExpression.isEqualTo("hello").toQueryString()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support Boolean isEqualTo Boolean`() {
        val expected = "TRUE = TRUE"

        val actual: String = true.isEqualTo(true).toQueryString()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support Boolean isEqualTo BooleanType`() {
        val booleanExpression = true.toBooleanType()
        val expected = "TRUE = TRUE"

        val actual: String = true.isEqualTo(booleanExpression).toQueryString()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support BooleanType isEqualTo Boolean`() {
        val booleanExpression = true.toBooleanType()
        val expected = "TRUE = TRUE"

        val actual: String = booleanExpression.isEqualTo(true).toQueryString()

        kotlin.test.assertEquals(expected, actual)
    }
}
