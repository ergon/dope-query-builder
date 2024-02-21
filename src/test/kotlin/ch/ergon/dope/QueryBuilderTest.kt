package ch.ergon.dope

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
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class QueryBuilderTest {
    private lateinit var builder: StringBuilder
    private lateinit var create: DSLContext

    @BeforeTest
    fun setup() {
        builder = StringBuilder()
        create = DSLContext()
    }

    @Test
    fun `should Equal Simple String`() {
        val expected = "SELECT customer.firstname\n" + "FROM customer"

        val actual = create.select(
            TestBucket.Customer.firstName,
        ).from(
            TestBucket.Customer,
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Equal Simple String 2`() {
        val expected = "SELECT *\n" + "  FROM person\n" + "    WHERE person.fname = \"Ian\"\n"

        val actual: String = create
            .selectAll()
            .from(
                TestBucket.Person,
            ).where(
                TestBucket.Person.fname.isEqualTo("Ian".toStringType()),
            ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Where`() {
        val expected = "SELECT *\n" + "  FROM person\n" + "    WHERE person.fname = \"Ian\"\n"

        val actual: String = create.selectFrom(
            TestBucket.Person,
        ).where(
            TestBucket.Person.fname.isEqualTo("Ian".toStringType()),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Equal Simple String 3`() {
        val expected = "SELECT *\n" + "  FROM person\n" + "    WHERE person.fname = \"Ian\"\n"

        val actual: String = create
            .selectAll()
            .from(
                TestBucket.Person,
            ).where(
                TestBucket.Person.fname.isEqualTo("Ian".toStringType()),
            ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Select Distinct`() {
        val expected = "SELECT DISTINCT person.fname, person.lname\n" + "  FROM person\n" + "    WHERE person.fname = \"Ian\"\n"

        val actual: String = create.selectDistinct(
            TestBucket.Person.fname,
            TestBucket.Person.lname,
        ).from(
            TestBucket.Person,
        ).where(
            TestBucket.Person.fname.isEqualTo("Ian".toStringType()),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Alias`() {
        val expected = "SELECT person.fname AS firstName\n" + "  FROM person\n" + "    WHERE person.fname = \"Peter\""

        val actual: String = create.select(
            TestBucket.Person.fname.alias("firstName"),
        ).from(
            TestBucket.Person,
        ).where(
            TestBucket.Person.fname.isEqualTo("Peter".toStringType()),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Alias Mixed In First`() {
        val expected = "SELECT person.fname AS FirstName, person.lname\n" + "    FROM person\n" + "       WHERE person.lname = \"Jackson\""

        val actual: String = create.select(
            TestBucket.Person.fname.alias("FirstName"),
            TestBucket.Person.lname,
        ).from(TestBucket.Person).where(
            TestBucket.Person.lname.isEqualTo("Jackson".toStringType()),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Alias Mixed In Last`() {
        val expected = "SELECT person.fname, person.lname AS LastName\n" + "    FROM person\n" + "       WHERE person.lname = \"Jackson\""

        val actual: String = create.select(
            TestBucket.Person.fname,
            TestBucket.Person.lname.alias("LastName"),
        ).from(TestBucket.Person).where(
            TestBucket.Person.lname.isEqualTo("Jackson".toStringType()),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Where With Simple Condition`() {
        val expected = "SELECT * FROM person WHERE person.age < 50"

        val actual: String = create
            .selectAll()
            .from(
                TestBucket.Person,
            ).where(
                TestBucket.Person.age.isLessThan(50.toNumberType()),
            ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Where With Chained Conditions`() {
        val expected = "SELECT * FROM person WHERE person.age < 50 AND person.title = \"Mr.\""

        val actual: String = create
            .selectAll()
            .from(
                TestBucket.Person,
            ).where(
                TestBucket.Person.age.isLessThan(50.toNumberType()).and(
                    TestBucket.Person.title.isEqualTo("Mr.".toStringType()),
                ),
            ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Where With Chained Conditions And Expressions`() {
        val expected = "SELECT * FROM person WHERE person.age < 50 AND person.title = \"Mr.\""

        val actual: String = create
            .selectAll()
            .from(
                TestBucket.Person,
            ).where(
                TestBucket.Person.age.isLessThan(
                    (45 + 5).toNumberType(),
                ).and(
                    TestBucket.Person.title.isEqualTo(
                        ("M" + "r.").toStringType(),
                    ),
                ),
            ).build()

        assertEquals(unifyString(expected), actual)
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

        assertEquals(expected, actual)
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

        assertEquals(expected, actual)
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

        assertEquals(expected, actual)
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

        assertEquals(expected, actual)
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

        assertEquals(expected, actual)
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

        assertEquals(expected, actual)
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

        assertEquals(expected, actual)
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

        assertEquals(expected, actual)
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

        assertEquals(expected, actual)
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

        assertEquals(expected, actual)
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

        assertEquals(expected, actual)
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

        assertEquals(expected, actual)
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

        assertEquals(expected, actual)
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

        assertEquals(expected, actual)
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

        assertEquals(expected, actual)
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

        assertEquals(expected, actual)
    }

    @Test
    fun `should Support Where With Many Chained Conditions`() {
        val expected = "SELECT * FROM person WHERE person.age < 50 AND person.title = \"Mr.\" AND person.relation = \"friend\""

        val actual: String = create
            .selectAll()
            .from(
                TestBucket.Person,
            ).where(
                TestBucket.Person.age.isLessThan(
                    (45 + 5).toNumberType(),
                ).and(
                    TestBucket.Person.title.isEqualTo(
                        "Mr.".toStringType(),
                    ).and(
                        TestBucket.Person.relation.isEqualTo("friend".toStringType()),
                    ),
                ),
            ).build()

        assertEquals(unifyString(expected), actual)
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

        assertEquals(unifyString(expected), actual)
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

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Null`() {
        val expected = "SELECT NULL"

        val actual: String = create.select(
            NULL,
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Missing`() {
        val expected = "SELECT MISSING"

        val actual: String = create.select(
            MISSING,
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Long Complex Query`() {
        val expected = "SELECT 1 = 1 AND 2 = 2 AND 3 = 3 AS what FROM airline WHERE 1 = 1 AND \"run\" = \"run\""

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
            TestBucket.Airline,
        ).where(
            1.toNumberType().isEqualTo(
                1.toNumberType(),
            ).and(
                "run".toStringType().isEqualTo(
                    "run".toStringType(),
                ),
            ),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Simple Boolean Value True`() {
        val expected = "SELECT TRUE"

        val actual: String = create.select(TRUE).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Simple Boolean Value False`() {
        val expected = "SELECT FALSE"

        val actual: String = create.select(FALSE).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Simple String Value False`() {
        val expected = "SELECT \"FALSE\""

        val actual: String = create.select("FALSE".toStringType()).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Separate Between Boolean And String`() {
        val expected = "SELECT TRUE = TRUE"

        val actual: String = create.select(
            TRUE.isEqualTo(
                TRUE,
            ),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support And`() {
        val expected = "SELECT TRUE AND FALSE"

        val actual: String = create.select(
            TRUE.and(
                FALSE,
            ),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Or`() {
        val expected = "SELECT TRUE OR FALSE"

        val actual: String = create.select(
            TRUE.or(
                FALSE,
            ),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Not 1`() {
        val expected = "SELECT NOT(TRUE)"

        val actual: String = create.select(not(TRUE)).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Not 2`() {
        val expected = "SELECT NOT(TRUE AND FALSE)"

        val actual: String = create.select(
            not(
                TRUE.and(
                    FALSE,
                ),
            ),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Not 3`() {
        val expected = "SELECT NOT(TRUE AND FALSE AND TRUE)"

        val actual: String = create.select(
            not(
                TRUE.and(
                    FALSE.and(
                        TRUE,
                    ),
                ),
            ),
        ).build()

        assertEquals(unifyString(expected), actual)
    }

    @Test
    fun `should Support Equals As Object`() {
        val expected = "TRUE = FALSE"

        val actual: String = TRUE.isEqualTo(
            FALSE,
        ).toQueryString()

        assertEquals(expected, actual)
    }

    @Test
    fun `Should support the NOW_STR function`() {
        val expected = "SELECT NOW_STR(\"\") AS full_date, NOW_STR(\"invalid date\") AS invalid_date, " +
            "NOW_STR(\"1111-11-11\") " +
            "AS short_date"

        val actual = create.select(
            nowStr().alias("full_date"),
            nowStr("invalid date").alias("invalid_date"),
            nowStr("1111-11-11").alias("short_date"),
        ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select raw`() {
        val expected = "SELECT RAW NOT(TRUE OR FALSE)"

        val actual = create.selectRaw(
            not(TRUE.or(FALSE)),
        ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select raw with field`() {
        val expected = "SELECT RAW customer.firstname FROM customer"

        val actual = create.selectRaw(
            TestBucket.Customer.firstName,
        ).from(
            TestBucket.Customer,
        ).build()

        assertEquals(expected, actual)
    }
}
