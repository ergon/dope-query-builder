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
        val expected = "SELECT * FROM someBucket WHERE numField > 5"

        val actual = create
            .selectFrom(someBucket())
            .where(
                someNumberField().isGreaterThan(5.toNumberType()),
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
}
