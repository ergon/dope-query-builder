package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.type.arithmetic.div
import ch.ergon.dope.resolvable.expression.type.arithmetic.mod
import ch.ergon.dope.resolvable.expression.type.arithmetic.mul
import ch.ergon.dope.resolvable.expression.type.arithmetic.neg
import ch.ergon.dope.resolvable.expression.type.arithmetic.sub
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class OperatorTest {
    private val someBucket = someBucket("someBucket")

    @Test
    fun `should support adding two number types`() {
        val expected = "SELECT (2 + 5) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                2.toDopeType().add(5.toDopeType()),
            ).from(
                someBucket,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support adding two number`() {
        val expected = "SELECT (2 + 5) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                2.add(5),
            ).from(
                someBucket(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support adding a number with a numberType`() {
        val expected = "SELECT (2 + `numberField`) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                2.add(someNumberField()),
            ).from(
                someBucket(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support adding a number type with a number`() {
        val expected = "SELECT (`numberField` + 2) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                someNumberField().add(2),
            ).from(
                someBucket(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support adding a NumberField and a number`() {
        val expected = "SELECT (`numberField` + 5) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                someNumberField().add(5.toDopeType()),
            ).from(
                someBucket,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested additions`() {
        val expected = "SELECT (3 + (`numberField` + 5)) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                3.toDopeType().add(someNumberField().add(5.toDopeType())),
            ).from(
                someBucket,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support alias on addition`() {
        val expected = "SELECT (`numberField` + 5) AS `something` FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                someNumberField().add(5.toDopeType()).alias("something"),
            ).from(
                someBucket,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support subtracting two number types`() {
        val expected = "SELECT (13 - 6) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                13.toDopeType().sub(6.toDopeType()),
            ).from(
                someBucket,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support subtracting two number`() {
        val expected = "SELECT (2 - 5) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                2.sub(5),
            ).from(
                someBucket(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support subtracting a number with a numberType`() {
        val expected = "SELECT (6 - `numberField`) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                6.sub(someNumberField()),
            ).from(
                someBucket(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support subtracting a number type with a number`() {
        val expected = "SELECT (`numberField` - 11) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                someNumberField().sub(11),
            ).from(
                someBucket(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support subtracting a NumberField and a number`() {
        val expected = "SELECT (`numberField` - 2) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                someNumberField().sub(2.toDopeType()),
            ).from(
                someBucket,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested subtracting`() {
        val expected = "SELECT (9 - (`numberField` - 2)) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                9.toDopeType().sub(someNumberField().sub(2.toDopeType())),
            ).from(
                someBucket,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support alias on subtraction`() {
        val expected = "SELECT (`numberField` - 5) AS `something` FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                someNumberField().sub(5.toDopeType()).alias("something"),
            ).from(
                someBucket,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiplying two number types`() {
        val expected = "SELECT (13 * 6) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                13.toDopeType().mul(6.toDopeType()),
            ).from(
                someBucket,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiplying two number`() {
        val expected = "SELECT (7 * 5) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                7.mul(5),
            ).from(
                someBucket(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiplying a number with a numberType`() {
        val expected = "SELECT (4 * `numberField`) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                4.mul(someNumberField()),
            ).from(
                someBucket(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiplying a number type with a number`() {
        val expected = "SELECT (`numberField` * 7) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                someNumberField().mul(7),
            ).from(
                someBucket(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support multiplying a NumberField and a number`() {
        val expected = "SELECT (`numberField` * 2) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                someNumberField().mul(2.toDopeType()),
            ).from(
                someBucket,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested multiplying`() {
        val expected = "SELECT (9 * (`numberField` * 2)) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                9.toDopeType().mul(someNumberField().mul(2.toDopeType())),
            ).from(
                someBucket,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support alias on multiplication`() {
        val expected = "SELECT (`numberField` * 5) AS `something` FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                someNumberField().mul(5.toDopeType()).alias("something"),
            ).from(
                someBucket,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support dividing two number types`() {
        val expected = "SELECT (13 / 6) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                13.toDopeType().div(6.toDopeType()),
            ).from(
                someBucket,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support dividing a number with a numberType`() {
        val expected = "SELECT (14 / `numberField`) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                14.div(someNumberField()),
            ).from(
                someBucket(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support dividing a number type with a number`() {
        val expected = "SELECT (`numberField` / 2) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                someNumberField().div(2),
            ).from(
                someBucket(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support dividing a NumberField and a number`() {
        val expected = "SELECT (`numberField` / 2) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                someNumberField().div(2.toDopeType()),
            ).from(
                someBucket,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested dividing`() {
        val expected = "SELECT (9 / (`numberField` / 2)) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                9.toDopeType().div(someNumberField().div(2.toDopeType())),
            ).from(
                someBucket,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support alias on dividing`() {
        val expected = "SELECT (`numberField` / 5) AS `something` FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                someNumberField().div(5.toDopeType()).alias("something"),
            ).from(
                someBucket,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support modulo with two number types`() {
        val expected = "SELECT (13 % 6) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                13.toDopeType().mod(6.toDopeType()),
            ).from(
                someBucket,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support modulo with two numbers`() {
        val expected = "SELECT (2 % 5) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                2.mod(5),
            ).from(
                someBucket(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support modulo with a number with a numberType`() {
        val expected = "SELECT (2 % `numberField`) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                2.mod(someNumberField()),
            ).from(
                someBucket(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support modulo with a number type with a number`() {
        val expected = "SELECT (`numberField` % 2) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                someNumberField().mod(2),
            ).from(
                someBucket(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support modulo with a NumberField and a number`() {
        val expected = "SELECT (`numberField` % 2) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                someNumberField().mod(2.toDopeType()),
            ).from(
                someBucket,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support nested modulo`() {
        val expected = "SELECT (9 % (`numberField` % 2)) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                9.toDopeType().mod(someNumberField().mod(2.toDopeType())),
            ).from(
                someBucket,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support alias on modulo`() {
        val expected = "SELECT (`numberField` % 5) AS `something` FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                someNumberField().mod(5.toDopeType()).alias("something"),
            ).from(
                someBucket,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support negation with a NumberField`() {
        val expected = "SELECT -`numberField` FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                neg(someNumberField()),
            ).from(
                someBucket,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support negation on a calculation`() {
        val expected = "SELECT -(`numberField` * 12) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                neg(someNumberField().mul(12.toDopeType())),
            ).from(
                someBucket,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support negation with different calculation`() {
        val expected = "SELECT (-`numberField` + 6) FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                neg(someNumberField()).add(6.toDopeType()),
            ).from(
                someBucket,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support negation with calculations`() {
        val expected = "SELECT (12 + (-`numberField` % ((12 * -(`numberField` - -12)) + 6))) AS `calculation` FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                12.toDopeType().add(
                    neg(someNumberField()).mod(
                        12.toDopeType().mul(
                            neg(
                                someNumberField().sub(
                                    neg(12.toDopeType()),
                                ),
                            ),
                        ).add(
                            6.toDopeType(),
                        ),
                    ),
                ).alias("calculation"),
            ).from(
                someBucket,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support more complicated calculations`() {
        val expected = "SELECT (9 * ((15 - (19 / 4)) + (21 * (12 - 59)))) AS `calculation` FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                9.toDopeType().mul(
                    15.toDopeType().sub(
                        19.toDopeType().div(4.toDopeType()),
                    ).add(
                        21.toDopeType().mul(
                            12.toDopeType().sub(59.toDopeType()),
                        ),
                    ),
                ).alias("calculation"),
            ).from(
                someBucket,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support negation with a number type`() {
        val expected = "SELECT -6 FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                neg(6.toDopeType()),
            ).from(
                someBucket,
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support negation with a number`() {
        val expected = "SELECT -6 FROM `someBucket`"

        val actual: String = QueryBuilder
            .select(
                neg(6),
            ).from(
                someBucket(),
            ).build().queryString

        assertEquals(expected, actual)
    }
}
