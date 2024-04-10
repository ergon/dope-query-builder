package ch.ergon.dope

import ch.ergon.dope.resolvable.expression.alias
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.div
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.mod
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.mul
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.neg
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.sub
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import junit.framework.TestCase.assertEquals
import kotlin.test.BeforeTest
import kotlin.test.Test

class OperatorTest {
    private lateinit var builder: StringBuilder
    private lateinit var create: DSLContext
    private val person = someBucket("person")

    @BeforeTest
    fun setup() {
        builder = StringBuilder()
        create = DSLContext()
    }

    @Test
    fun `should support adding two numbers`() {
        val expected = "SELECT (2 + 5) FROM person"

        val actual: String = create
            .select(
                2.toNumberType().add(5.toNumberType()),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support adding a NumberField and a number`() {
        val expected = "SELECT (person.age + 5) FROM person"

        val actual: String = create
            .select(
                someNumberField("age", person).add(5.toNumberType()),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested additions`() {
        val expected = "SELECT (3 + (person.age + 5)) FROM person"

        val actual: String = create
            .select(
                3.toNumberType().add(someNumberField("age", person).add(5.toNumberType())),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support alias on addition`() {
        val expected = "SELECT (person.age + 5) AS something FROM person"

        val actual: String = create
            .select(
                someNumberField("age", person).add(5.toNumberType()).alias("something"),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support subtracting two numbers`() {
        val expected = "SELECT (13 - 6) FROM person"

        val actual: String = create
            .select(
                13.toNumberType().sub(6.toNumberType()),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support subtracting a NumberField and a number`() {
        val expected = "SELECT (person.age - 2) FROM person"

        val actual: String = create
            .select(
                someNumberField("age", person).sub(2.toNumberType()),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested subtracting`() {
        val expected = "SELECT (9 - (person.age - 2)) FROM person"

        val actual: String = create
            .select(
                9.toNumberType().sub(someNumberField("age", person).sub(2.toNumberType())),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support alias on subtraction`() {
        val expected = "SELECT (person.age - 5) AS something FROM person"

        val actual: String = create
            .select(
                someNumberField("age", person).sub(5.toNumberType()).alias("something"),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiplying two numbers`() {
        val expected = "SELECT (13 * 6) FROM person"

        val actual: String = create
            .select(
                13.toNumberType().mul(6.toNumberType()),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiplying a NumberField and a number`() {
        val expected = "SELECT (person.age * 2) FROM person"

        val actual: String = create
            .select(
                someNumberField("age", person).mul(2.toNumberType()),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested multiplying`() {
        val expected = "SELECT (9 * (person.age * 2)) FROM person"

        val actual: String = create
            .select(
                9.toNumberType().mul(someNumberField("age", person).mul(2.toNumberType())),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support alias on multiplication`() {
        val expected = "SELECT (person.age * 5) AS something FROM person"

        val actual: String = create
            .select(
                someNumberField("age", person).mul(5.toNumberType()).alias("something"),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support dividing two numbers`() {
        val expected = "SELECT (13 / 6) FROM person"

        val actual: String = create
            .select(
                13.toNumberType().div(6.toNumberType()),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support dividing a NumberField and a number`() {
        val expected = "SELECT (person.age / 2) FROM person"

        val actual: String = create
            .select(
                someNumberField("age", person).div(2.toNumberType()),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested dividing`() {
        val expected = "SELECT (9 / (person.age / 2)) FROM person"

        val actual: String = create
            .select(
                9.toNumberType().div(someNumberField("age", person).div(2.toNumberType())),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support alias on dividing`() {
        val expected = "SELECT (person.age / 5) AS something FROM person"

        val actual: String = create
            .select(
                someNumberField("age", person).div(5.toNumberType()).alias("something"),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support modulo with two numbers`() {
        val expected = "SELECT (13 % 6) FROM person"

        val actual: String = create
            .select(
                13.toNumberType().mod(6.toNumberType()),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support modulo with a NumberField and a number`() {
        val expected = "SELECT (person.age % 2) FROM person"

        val actual: String = create
            .select(
                someNumberField("age", person).mod(2.toNumberType()),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested modulo`() {
        val expected = "SELECT (9 % (person.age % 2)) FROM person"

        val actual: String = create
            .select(
                9.toNumberType().mod(someNumberField("age", person).mod(2.toNumberType())),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support alias on modulo`() {
        val expected = "SELECT (person.age % 5) AS something FROM person"

        val actual: String = create
            .select(
                someNumberField("age", person).mod(5.toNumberType()).alias("something"),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support negation with a NumberField`() {
        val expected = "SELECT -(numField) FROM person"

        val actual: String = create
            .select(
                neg(someNumberField()),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support negation on a calculation`() {
        val expected = "SELECT -((numField * 12)) FROM person"

        val actual: String = create
            .select(
                neg(someNumberField().mul(12.toNumberType())),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support negation with different calculation`() {
        val expected = "SELECT (-(numField) + 6) FROM person"

        val actual: String = create
            .select(
                neg(someNumberField()).add(6.toNumberType()),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support negation with calculations`() {
        val expected = "SELECT (12 + (-(numField) % ((12 * -((numField - -(12)))) + 6))) AS calculation FROM person"

        val actual: String = create
            .select(
                12.toNumberType().add(
                    neg(someNumberField()).mod(
                        12.toNumberType().mul(
                            neg(
                                someNumberField().sub(
                                    neg(12.toNumberType()),
                                ),
                            ),
                        ).add(
                            6.toNumberType(),
                        ),
                    ),
                ).alias("calculation"),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support more complicated calculations`() {
        val expected = "SELECT (9 * ((15 - (19 / 4)) + (21 * (12 - 59)))) AS calculation FROM person"

        val actual: String = create
            .select(
                9.toNumberType().mul(
                    15.toNumberType().sub(
                        19.toNumberType().div(4.toNumberType()),
                    ).add(
                        21.toNumberType().mul(
                            12.toNumberType().sub(59.toNumberType()),
                        ),
                    ),
                ).alias("calculation"),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support negation with a number`() {
        val expected = "SELECT -(6) FROM person"

        val actual: String = create
            .select(
                neg(6.toNumberType()),
            ).from(
                person,
            ).build()

        assertEquals(expected, actual)
    }
}
