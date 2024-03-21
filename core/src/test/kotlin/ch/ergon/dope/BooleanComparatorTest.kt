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
        val expected = "SELECT * FROM route WHERE route.stops > 5"

        val actual = create
            .selectAll()
            .from(
                TestBucket.Route,
            ).where(
                TestBucket.Route.stops.isGreaterThan(5.toNumberType()),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support number is greater than a number`() {
        val expected = "SELECT * FROM airline WHERE 2 > 4"

        val actual = create
            .selectAll()
            .from(
                TestBucket.Airline,
            ).where(
                2.isGreaterThan(4),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support number is greater than a numberType`() {
        val expected = "SELECT * FROM airline WHERE 2 > 4"

        val actual = create
            .selectAll()
            .from(
                TestBucket.Airline,
            ).where(
                2.isGreaterThan(4.toNumberType()),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support numberType is greater than a number`() {
        val expected = "SELECT * FROM airline WHERE 2 > 4"

        val actual = create
            .selectAll()
            .from(
                TestBucket.Airline,
            ).where(
                2.toNumberType().isGreaterThan(4),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greater than with a field and string`() {
        val expected = "SELECT * FROM airline WHERE airline.name > \"a\""

        val actual = create
            .selectAll()
            .from(
                TestBucket.Airline,
            ).where(
                TestBucket.Airline.aname.isGreaterThan("a".toStringType()),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support string is greater than a string`() {
        val expected = "SELECT * FROM airline WHERE \"hallo\" > \"test\""

        val actual = create
            .selectAll()
            .from(
                TestBucket.Airline,
            ).where(
                "hallo".isGreaterThan("test"),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support stringType is greater than a string`() {
        val expected = "SELECT * FROM airline WHERE \"hallo\" > \"test\""

        val actual = create
            .selectAll()
            .from(
                TestBucket.Airline,
            ).where(
                "hallo".toStringType().isGreaterThan("test"),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support string is greater than a stringType`() {
        val expected = "SELECT * FROM airline WHERE \"hallo\" > \"test\""

        val actual = create
            .selectAll()
            .from(
                TestBucket.Airline,
            ).where(
                "hallo".isGreaterThan("test".toStringType()),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greater than with a string and a field`() {
        val expected = "SELECT * FROM airline WHERE \"a\" > airline.name"

        val actual = create
            .selectAll()
            .from(
                TestBucket.Airline,
            ).where(
                "a".toStringType().isGreaterThan(TestBucket.Airline.aname),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support less than with a field and a number`() {
        val expected = "SELECT * FROM route WHERE route.id < 5"

        val actual = create
            .selectAll()
            .from(
                TestBucket.Route,
            ).where(
                TestBucket.Route.id.isLessThan(5.toNumberType()),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support less than with a number and a field`() {
        val expected = "SELECT * FROM person WHERE 5 < person.age"

        val actual = create
            .selectAll()
            .from(
                TestBucket.Person,
            ).where(
                5.toNumberType().isLessThan(TestBucket.Person.age),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support number is less than a number`() {
        val expected = "SELECT * FROM person WHERE 5 < 7"

        val actual = create
            .selectAll()
            .from(
                TestBucket.Person,
            ).where(
                5.isLessThan(7),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support numberType is less than a number`() {
        val expected = "SELECT * FROM person WHERE 5 < 7"

        val actual = create
            .selectAll()
            .from(
                TestBucket.Person,
            ).where(
                5.toNumberType().isLessThan(7),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support number is less than a numberType`() {
        val expected = "SELECT * FROM person WHERE 5 < 7"

        val actual = create
            .selectAll()
            .from(
                TestBucket.Person,
            ).where(
                5.isLessThan(7.toNumberType()),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is less than with a string`() {
        val expected = "SELECT * FROM airline WHERE airline.name < \"a\""

        val actual = create
            .selectAll()
            .from(
                TestBucket.Airline,
            ).where(
                TestBucket.Airline.aname.isLessThan("a".toStringType()),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support string is less than a string`() {
        val expected = "SELECT * FROM person WHERE \"hallo\" < \"test\""

        val actual = create
            .selectAll()
            .from(
                TestBucket.Person,
            ).where(
                "hallo".isLessThan("test"),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support stringType is less than a string`() {
        val expected = "SELECT * FROM person WHERE \"hallo\" < \"test\""

        val actual = create
            .selectAll()
            .from(
                TestBucket.Person,
            ).where(
                "hallo".toStringType().isLessThan("test"),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support string is less than a stringType`() {
        val expected = "SELECT * FROM person WHERE \"hallo\" < \"test\""

        val actual = create
            .selectAll()
            .from(
                TestBucket.Person,
            ).where(
                "hallo".isLessThan("test".toStringType()),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support like with a string`() {
        val expected = "SELECT * FROM person WHERE person.fname LIKE \"_b%\""

        val actual = create
            .selectAll()
            .from(
                TestBucket.Person,
            ).where(
                TestBucket.Person.fname.isLike("_b%"),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support like with a type`() {
        val expected = "SELECT * FROM person WHERE person.fname LIKE \"_b%\""

        val actual = create
            .selectAll()
            .from(
                TestBucket.Person,
            ).where(
                TestBucket.Person.fname.isLike("_b%".toStringType()),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not like with a string`() {
        val expected = "SELECT * FROM person WHERE person.email NOT LIKE \"%@yahoo.com\""

        val actual: String = create
            .selectAll()
            .from(
                TestBucket.Person,
            ).where(
                TestBucket.Person.email.isNotLike("%@yahoo.com"),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support not like with a type`() {
        val expected = "SELECT * FROM person WHERE person.fname NOT LIKE \"_b%\""

        val actual = create
            .selectAll()
            .from(
                TestBucket.Person,
            ).where(
                TestBucket.Person.fname.isNotLike("_b%".toStringType()),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is null`() {
        val expected = "SELECT * FROM person WHERE person.fname IS NULL"

        val actual = create
            .selectAll()
            .from(
                TestBucket.Person,
            ).where(
                TestBucket.Person.fname.isNull(),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not null`() {
        val expected = "SELECT * FROM person WHERE person.fname IS NOT NULL"

        val actual = create
            .selectAll()
            .from(
                TestBucket.Person,
            ).where(
                TestBucket.Person.fname.isNotNull(),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is missing`() {
        val expected = "SELECT * FROM person WHERE person.fname IS MISSING"

        val actual = create
            .selectAll()
            .from(
                TestBucket.Person,
            ).where(
                TestBucket.Person.fname.isMissing(),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not missing`() {
        val expected = "SELECT * FROM person WHERE person.fname IS NOT MISSING"

        val actual = create
            .selectAll()
            .from(
                TestBucket.Person,
            ).where(
                TestBucket.Person.fname.isNotMissing(),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is valued`() {
        val expected = "SELECT * FROM person WHERE person.fname IS VALUED"

        val actual = create
            .selectAll()
            .from(
                TestBucket.Person,
            ).where(
                TestBucket.Person.fname.isValued(),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not valued`() {
        val expected = "SELECT * FROM person WHERE person.fname IS NOT VALUED"

        val actual = create
            .selectAll()
            .from(
                TestBucket.Person,
            ).where(
                TestBucket.Person.fname.isNotValued(),
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is not equal to with number`() {
        val expected = "SELECT * FROM person WHERE 12 != 5"

        val actual: String = create
            .selectAll()
            .from(TestBucket.Person)
            .where(
                12.toNumberType().isNotEqualTo(5.toNumberType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support number is not equal to with number`() {
        val expected = "SELECT * FROM person WHERE 12 != 5"

        val actual: String = create
            .selectAll()
            .from(TestBucket.Person)
            .where(
                12.isNotEqualTo(5),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support numberType is not equal to with number`() {
        val expected = "SELECT * FROM person WHERE 12 != 5"

        val actual: String = create
            .selectAll()
            .from(TestBucket.Person)
            .where(
                12.toNumberType().isNotEqualTo(5),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support number is not equal to with numberType`() {
        val expected = "SELECT * FROM person WHERE 12 != 5"

        val actual: String = create
            .selectAll()
            .from(TestBucket.Person)
            .where(
                12.isNotEqualTo(5.toNumberType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is not equal to with numberField and number`() {
        val expected = "SELECT * FROM person WHERE person.age != 5"

        val actual: String = create
            .selectAll()
            .from(TestBucket.Person)
            .where(
                TestBucket.Person.age.isNotEqualTo(5.toNumberType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is not equal to with number and numberField`() {
        val expected = "SELECT * FROM person WHERE 3 != person.age"

        val actual: String = create
            .selectAll()
            .from(TestBucket.Person)
            .where(
                3.toNumberType().isNotEqualTo(TestBucket.Person.age),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is not equal to with string`() {
        val expected = "SELECT * FROM person WHERE \"test\" != \"hallo\""

        val actual: String = create
            .selectAll()
            .from(TestBucket.Person)
            .where(
                "test".toStringType().isNotEqualTo("hallo".toStringType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is not equal to with stringField and string`() {
        val expected = "SELECT * FROM person WHERE person.fname != \"5\""

        val actual: String = create
            .selectAll()
            .from(TestBucket.Person)
            .where(
                TestBucket.Person.fname.isNotEqualTo("5".toStringType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with number`() {
        val expected = "SELECT * FROM person WHERE 12 >= 5"

        val actual: String = create
            .selectAll()
            .from(TestBucket.Person)
            .where(
                12.toNumberType().isGreaterOrEqualThan(5.toNumberType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support number is greater or equal to with numberType`() {
        val expected = "SELECT * FROM person WHERE 12 >= 5"

        val actual: String = create
            .selectAll()
            .from(TestBucket.Person)
            .where(
                12.isGreaterOrEqualThan(5.toNumberType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support numberType is greater or equal to with number`() {
        val expected = "SELECT * FROM person WHERE 12 >= 5"

        val actual: String = create
            .selectAll()
            .from(TestBucket.Person)
            .where(
                12.toNumberType().isGreaterOrEqualThan(5),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support string is greater or equal to with stringType`() {
        val expected = "SELECT * FROM person WHERE \"hallo\" >= \"test\""

        val actual: String = create
            .selectAll()
            .from(TestBucket.Person)
            .where(
                "hallo".isGreaterOrEqualThan("test".toStringType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support stringType is greater or equal to with string`() {
        val expected = "SELECT * FROM person WHERE person.fname >= \"test\""

        val actual: String = create
            .selectAll()
            .from(TestBucket.Person)
            .where(
                TestBucket.Person.fname.isGreaterOrEqualThan("test"),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with numberField and number`() {
        val expected = "SELECT * FROM person WHERE person.age >= 5"

        val actual: String = create
            .selectAll()
            .from(TestBucket.Person)
            .where(
                TestBucket.Person.age.isGreaterOrEqualThan(5.toNumberType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with number and numberField`() {
        val expected = "SELECT * FROM person WHERE 3 >= person.age"

        val actual: String = create
            .selectAll()
            .from(TestBucket.Person)
            .where(
                3.toNumberType().isGreaterOrEqualThan(TestBucket.Person.age),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with string`() {
        val expected = "SELECT * FROM person WHERE \"test\" >= \"hallo\""

        val actual: String = create
            .selectAll()
            .from(TestBucket.Person)
            .where(
                "test".toStringType().isGreaterOrEqualThan("hallo".toStringType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with stringField and string`() {
        val expected = "SELECT * FROM person WHERE person.fname >= \"5\""

        val actual: String = create
            .selectAll()
            .from(TestBucket.Person)
            .where(
                TestBucket.Person.fname.isGreaterOrEqualThan("5".toStringType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is greater or equal to with boolean and stringField`() {
        val expected = "SELECT * FROM person WHERE \"test\" >= person.fname"

        val actual: String = create
            .selectAll()
            .from(TestBucket.Person)
            .where(
                "test".toStringType().isGreaterOrEqualThan(TestBucket.Person.fname),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is Less or equal to with number`() {
        val expected = "SELECT * FROM person WHERE 12 <= 5"

        val actual: String = create
            .selectAll()
            .from(TestBucket.Person)
            .where(
                12.toNumberType().isLessOrEqualThan(5.toNumberType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is Less or equal to with numberField and number`() {
        val expected = "SELECT * FROM person WHERE person.age <= 5"

        val actual: String = create
            .selectAll()
            .from(TestBucket.Person)
            .where(
                TestBucket.Person.age.isLessOrEqualThan(5.toNumberType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is Less or equal to with number and numberField`() {
        val expected = "SELECT * FROM person WHERE 3 <= person.age"

        val actual: String = create
            .selectAll()
            .from(TestBucket.Person)
            .where(
                3.toNumberType().isLessOrEqualThan(TestBucket.Person.age),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is Less or equal to with string`() {
        val expected = "SELECT * FROM person WHERE \"test\" <= \"hallo\""

        val actual: String = create
            .selectAll()
            .from(TestBucket.Person)
            .where(
                "test".toStringType().isLessOrEqualThan("hallo".toStringType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should support is Less or equal to with stringField and string`() {
        val expected = "SELECT * FROM person WHERE person.fname <= \"5\""

        val actual: String = create
            .selectAll()
            .from(TestBucket.Person)
            .where(
                TestBucket.Person.fname.isLessOrEqualThan("5".toStringType()),
            ).build()

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `should Support Where With Like`() {
        val expected = "SELECT person.fname, person.email FROM person WHERE person.email LIKE \"%@yahoo.com\""

        val actual: String = create.select(
            TestBucket.Person.fname,
            TestBucket.Person.email,
        ).from(
            TestBucket.Person,
        ).where(
            TestBucket.Person.email.isLike(
                "%@yahoo.com".toStringType(),
            ),
        ).build()

        kotlin.test.assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Where With Like Chained`() {
        val expected = "SELECT person.email, person.age FROM person WHERE person.email LIKE \"%@gmail.com\" AND person.age = 46"

        val actual: String = create.select(
            TestBucket.Person.email,
            TestBucket.Person.age,
        ).from(
            TestBucket.Person,
        ).where(
            TestBucket.Person.email.isLike(
                "%@gmail.com".toStringType(),
            ).and(
                TestBucket.Person.age.isEqualTo(46.toNumberType()),
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
