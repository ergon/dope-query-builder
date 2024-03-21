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

    @BeforeTest
    fun setup() {
        builder = StringBuilder()
        create = DSLContext()
    }

    @Test
    fun `should support adding two number types`() {
        val expected = "SELECT (2 + 5) FROM person"

        val actual: String = create
            .select(
                2.toNumberType().add(5.toNumberType()),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support adding two number`() {
        val expected = "SELECT (2 + 5) FROM person"

        val actual: String = create
            .select(
                2.add(5),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support adding a number with a numberType`() {
        val expected = "SELECT (2 + person.age) FROM person"

        val actual: String = create
            .select(
                2.add(TestBucket.Person.age),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support adding a number type with a number`() {
        val expected = "SELECT (person.age + 2) FROM person"

        val actual: String = create
            .select(
                TestBucket.Person.age.add(2),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support adding a NumberField and a number`() {
        val expected = "SELECT (person.age + 5) FROM person"

        val actual: String = create
            .select(
                TestBucket.Person.age.add(5.toNumberType()),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested additions`() {
        val expected = "SELECT (3 + (person.age + 5)) FROM person"

        val actual: String = create
            .select(
                3.toNumberType().add(TestBucket.Person.age.add(5.toNumberType())),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support alias on addition`() {
        val expected = "SELECT (person.age + 5) AS something FROM person"

        val actual: String = create
            .select(
                TestBucket.Person.age.add(5.toNumberType()).alias("something"),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support subtracting two number types`() {
        val expected = "SELECT (13 - 6) FROM person"

        val actual: String = create
            .select(
                13.toNumberType().sub(6.toNumberType()),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support subtracting two number`() {
        val expected = "SELECT (2 - 5) FROM person"

        val actual: String = create
            .select(
                2.sub(5),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support subtracting a number with a numberType`() {
        val expected = "SELECT (6 - person.age) FROM person"

        val actual: String = create
            .select(
                6.sub(TestBucket.Person.age),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support subtracting a number type with a number`() {
        val expected = "SELECT (person.age - 11) FROM person"

        val actual: String = create
            .select(
                TestBucket.Person.age.sub(11),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support subtracting a NumberField and a number`() {
        val expected = "SELECT (person.age - 2) FROM person"

        val actual: String = create
            .select(
                TestBucket.Person.age.sub(2.toNumberType()),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested subtracting`() {
        val expected = "SELECT (9 - (person.age - 2)) FROM person"

        val actual: String = create
            .select(
                9.toNumberType().sub(TestBucket.Person.age.sub(2.toNumberType())),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support alias on subtraction`() {
        val expected = "SELECT (person.age - 5) AS something FROM person"

        val actual: String = create
            .select(
                TestBucket.Person.age.sub(5.toNumberType()).alias("something"),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiplying two number types`() {
        val expected = "SELECT (13 * 6) FROM person"

        val actual: String = create
            .select(
                13.toNumberType().mul(6.toNumberType()),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiplying two number`() {
        val expected = "SELECT (7 * 5) FROM person"

        val actual: String = create
            .select(
                7.mul(5),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiplying a number with a numberType`() {
        val expected = "SELECT (4 * person.age) FROM person"

        val actual: String = create
            .select(
                4.mul(TestBucket.Person.age),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiplying a number type with a number`() {
        val expected = "SELECT (person.age * 7) FROM person"

        val actual: String = create
            .select(
                TestBucket.Person.age.mul(7),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiplying a NumberField and a number`() {
        val expected = "SELECT (person.age * 2) FROM person"

        val actual: String = create
            .select(
                TestBucket.Person.age.mul(2.toNumberType()),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested multiplying`() {
        val expected = "SELECT (9 * (person.age * 2)) FROM person"

        val actual: String = create
            .select(
                9.toNumberType().mul(TestBucket.Person.age.mul(2.toNumberType())),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support alias on multiplication`() {
        val expected = "SELECT (person.age * 5) AS something FROM person"

        val actual: String = create
            .select(
                TestBucket.Person.age.mul(5.toNumberType()).alias("something"),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support dividing two number types`() {
        val expected = "SELECT (13 / 6) FROM person"

        val actual: String = create
            .select(
                13.toNumberType().div(6.toNumberType()),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support dividing a number with a numberType`() {
        val expected = "SELECT (14 / person.age) FROM person"

        val actual: String = create
            .select(
                14.div(TestBucket.Person.age),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support dividing a number type with a number`() {
        val expected = "SELECT (person.age / 2) FROM person"

        val actual: String = create
            .select(
                TestBucket.Person.age.div(2),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support dividing a NumberField and a number`() {
        val expected = "SELECT (person.age / 2) FROM person"

        val actual: String = create
            .select(
                TestBucket.Person.age.div(2.toNumberType()),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested dividing`() {
        val expected = "SELECT (9 / (person.age / 2)) FROM person"

        val actual: String = create
            .select(
                9.toNumberType().div(TestBucket.Person.age.div(2.toNumberType())),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support alias on dividing`() {
        val expected = "SELECT (person.age / 5) AS something FROM person"

        val actual: String = create
            .select(
                TestBucket.Person.age.div(5.toNumberType()).alias("something"),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support modulo with two number types`() {
        val expected = "SELECT (13 % 6) FROM person"

        val actual: String = create
            .select(
                13.toNumberType().mod(6.toNumberType()),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support modulo with two numbers`() {
        val expected = "SELECT (2 % 5) FROM person"

        val actual: String = create
            .select(
                2.mod(5),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support modulo with a number with a numberType`() {
        val expected = "SELECT (2 % person.age) FROM person"

        val actual: String = create
            .select(
                2.mod(TestBucket.Person.age),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support modulo with a number type with a number`() {
        val expected = "SELECT (person.age % 2) FROM person"

        val actual: String = create
            .select(
                TestBucket.Person.age.mod(2),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support modulo with a NumberField and a number`() {
        val expected = "SELECT (person.age % 2) FROM person"

        val actual: String = create
            .select(
                TestBucket.Person.age.mod(2.toNumberType()),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested modulo`() {
        val expected = "SELECT (9 % (person.age % 2)) FROM person"

        val actual: String = create
            .select(
                9.toNumberType().mod(TestBucket.Person.age.mod(2.toNumberType())),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support alias on modulo`() {
        val expected = "SELECT (person.age % 5) AS something FROM person"

        val actual: String = create
            .select(
                TestBucket.Person.age.mod(5.toNumberType()).alias("something"),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support negation with a NumberField`() {
        val expected = "SELECT -(person.age) FROM person"

        val actual: String = create
            .select(
                neg(TestBucket.Person.age),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support negation on a calculation`() {
        val expected = "SELECT -((person.age * 12)) FROM person"

        val actual: String = create
            .select(
                neg(TestBucket.Person.age.mul(12.toNumberType())),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support negation with different calculation`() {
        val expected = "SELECT (-(person.age) + 6) FROM person"

        val actual: String = create
            .select(
                neg(TestBucket.Person.age).add(6.toNumberType()),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support negation with calculations`() {
        val expected = "SELECT (12 + (-(route.stops) % ((12 * -((route.id - -(12)))) + 6))) AS calculation FROM route"

        val actual: String = create
            .select(
                12.toNumberType().add(
                    neg(TestBucket.Route.stops).mod(
                        12.toNumberType().mul(
                            neg(
                                TestBucket.Route.id.sub(
                                    neg(12.toNumberType()),
                                ),
                            ),
                        ).add(
                            6.toNumberType(),
                        ),
                    ),
                ).alias("calculation"),
            ).from(
                TestBucket.Route,
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
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support negation with a number type`() {
        val expected = "SELECT -(6) FROM person"

        val actual: String = create
            .select(
                neg(6.toNumberType()),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support negation with a number`() {
        val expected = "SELECT -(6) FROM person"

        val actual: String = create
            .select(
                neg(6),
            ).from(
                TestBucket.Person,
            ).build()

        assertEquals(expected, actual)
    }
}
