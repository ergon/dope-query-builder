package ch.ergon.dope.extensions.type.numeric

import ch.ergon.dope.extension.type.numberfunction.abs
import ch.ergon.dope.extension.type.numberfunction.acos
import ch.ergon.dope.extension.type.numberfunction.asin
import ch.ergon.dope.extension.type.numberfunction.atan
import ch.ergon.dope.extension.type.numberfunction.atan2
import ch.ergon.dope.extension.type.numberfunction.ceil
import ch.ergon.dope.extension.type.numberfunction.cos
import ch.ergon.dope.extension.type.numberfunction.degrees
import ch.ergon.dope.extension.type.numberfunction.exp
import ch.ergon.dope.extension.type.numberfunction.floor
import ch.ergon.dope.extension.type.numberfunction.ln
import ch.ergon.dope.extension.type.numberfunction.log
import ch.ergon.dope.extension.type.numberfunction.power
import ch.ergon.dope.extension.type.numberfunction.radians
import ch.ergon.dope.extension.type.numberfunction.random
import ch.ergon.dope.extension.type.numberfunction.round
import ch.ergon.dope.extension.type.numberfunction.sign
import ch.ergon.dope.extension.type.numberfunction.sin
import ch.ergon.dope.extension.type.numberfunction.sqrt
import ch.ergon.dope.extension.type.numberfunction.tan
import ch.ergon.dope.extension.type.numberfunction.trunc
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class NumberFunctionTest {
    @Test
    fun `should support ABS number function with number CMField`() {
        val expected = "ABS(`CMNumberField`)"

        val actual = abs(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ACOS number function with number CMField`() {
        val expected = "ACOS(`CMNumberField`)"

        val actual = acos(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ASIN number function with number CMField`() {
        val expected = "ASIN(`CMNumberField`)"

        val actual = asin(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ATAN number function with number CMField`() {
        val expected = "ATAN(`CMNumberField`)"

        val actual = atan(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ATAN2 number function with divisor and dividend as number CMField`() {
        val expected = "ATAN2(`CMNumberField`, `anotherNumberField`)"

        val actual = atan2(someCMNumberField(), someCMNumberField("anotherNumberField")).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ATAN2 number function with divisor as number CMField and dividend as TypeExpression`() {
        val expected = "ATAN2(`CMNumberField`, 1)"

        val actual = atan2(someCMNumberField(), 1.toDopeType()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ATAN2 number function with divisor as number CMField and dividend as Number`() {
        val expected = "ATAN2(`CMNumberField`, 1)"

        val actual = atan2(someCMNumberField(), 1).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ATAN2 number function with divisor as TypeExpression and dividend as number CMField`() {
        val expected = "ATAN2(1, `CMNumberField`)"

        val actual = atan2(1.toDopeType(), someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ATAN2 number function with divisor as Number and dividend as number CMField`() {
        val expected = "ATAN2(1, `CMNumberField`)"

        val actual = atan2(1, someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support CEIL number function with number CMField`() {
        val expected = "CEIL(`CMNumberField`)"

        val actual = ceil(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support COS number function with number CMField`() {
        val expected = "COS(`CMNumberField`)"

        val actual = cos(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support DEGREES number function with number CMField`() {
        val expected = "DEGREES(`CMNumberField`)"

        val actual = degrees(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support EXP number function with number CMField`() {
        val expected = "EXP(`CMNumberField`)"

        val actual = exp(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support LN number function with number CMField`() {
        val expected = "LN(`CMNumberField`)"

        val actual = ln(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support LOG number function with number CMField`() {
        val expected = "LOG(`CMNumberField`)"

        val actual = log(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support FLOOR number function with number CMField`() {
        val expected = "FLOOR(`CMNumberField`)"

        val actual = floor(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support POWER number function with base and exponent as number CMField`() {
        val expected = "POWER(`CMNumberField`, `anotherNumberField`)"

        val actual = power(someCMNumberField(), someCMNumberField("anotherNumberField")).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support POWER number function with base as number CMField and exponent as TypeExpression`() {
        val expected = "POWER(`CMNumberField`, 1)"

        val actual = power(someCMNumberField(), 1.toDopeType()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support POWER number function with base as number CMField and exponent as Number`() {
        val expected = "POWER(`CMNumberField`, 1)"

        val actual = power(someCMNumberField(), 1).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support POWER number function with base as TypeExpression and exponent as number CMField`() {
        val expected = "POWER(1, `CMNumberField`)"

        val actual = power(1.toDopeType(), someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support POWER number function with base as Number and exponent as number CMField`() {
        val expected = "POWER(1, `CMNumberField`)"

        val actual = power(1, someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support RADIANS number function with number CMField`() {
        val expected = "RADIANS(`CMNumberField`)"

        val actual = radians(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support RANDOM number function with number CMField`() {
        val expected = "RANDOM(`CMNumberField`)"

        val actual = random(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ROUND number function with number CMField`() {
        val expected = "ROUND(`CMNumberField`)"

        val actual = round(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ROUND number function with value and digits as number CMField`() {
        val expected = "ROUND(`CMNumberField`, `anotherNumberField`)"

        val actual = round(someCMNumberField(), someCMNumberField("anotherNumberField")).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ROUND number function with value as number CMField and digits as TypeExpression`() {
        val expected = "ROUND(`CMNumberField`, 1)"

        val actual = round(someCMNumberField(), 1.toDopeType()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ROUND number function with value as number CMField and digits as Number`() {
        val expected = "ROUND(`CMNumberField`, 1)"

        val actual = round(someCMNumberField(), 1).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ROUND number function with value as TypeExpression and digits as number CMField`() {
        val expected = "ROUND(1, `CMNumberField`)"

        val actual = round(1.toDopeType(), someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ROUND number function with value as Number and digits as number CMField`() {
        val expected = "ROUND(1, `CMNumberField`)"

        val actual = round(1, someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support SIGN number function with number CMField`() {
        val expected = "SIGN(`CMNumberField`)"

        val actual = sign(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support SIN number function with number CMField`() {
        val expected = "SIN(`CMNumberField`)"

        val actual = sin(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support SQRT number function with number CMField`() {
        val expected = "SQRT(`CMNumberField`)"

        val actual = sqrt(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TAN number function with number CMField`() {
        val expected = "TAN(`CMNumberField`)"

        val actual = tan(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TRUNC number function with number CMField`() {
        val expected = "TRUNC(`CMNumberField`)"

        val actual = trunc(someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TRUNC number function with value and digits as number CMField`() {
        val expected = "TRUNC(`CMNumberField`, `anotherNumberField`)"

        val actual = trunc(someCMNumberField(), someCMNumberField("anotherNumberField")).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TRUNC number function with value as number CMField and digits as TypeExpression`() {
        val expected = "TRUNC(`CMNumberField`, 1)"

        val actual = trunc(someCMNumberField(), 1.toDopeType()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TRUNC number function with value as number CMField and digits as Number`() {
        val expected = "TRUNC(`CMNumberField`, 1)"

        val actual = trunc(someCMNumberField(), 1).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TRUNC number function with value as TypeExpression and digits as number CMField`() {
        val expected = "TRUNC(1, `CMNumberField`)"

        val actual = trunc(1.toDopeType(), someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TRUNC number function with value as Number and digits as number CMField`() {
        val expected = "TRUNC(1, `CMNumberField`)"

        val actual = trunc(1, someCMNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }
}
