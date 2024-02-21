package ch.ergon.dope

import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isGreaterThan
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isLessThan
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isLike
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isMissing
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNotLike
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNotMissing
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNotNull
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNotValued
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNull
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isValued
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
}
