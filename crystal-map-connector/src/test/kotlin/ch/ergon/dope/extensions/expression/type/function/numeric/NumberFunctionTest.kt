package ch.ergon.dope.extensions.expression.type.function.numeric

import ch.ergon.dope.extension.expression.type.function.numeric.abs
import ch.ergon.dope.extension.expression.type.function.numeric.acos
import ch.ergon.dope.extension.expression.type.function.numeric.asin
import ch.ergon.dope.extension.expression.type.function.numeric.atan
import ch.ergon.dope.extension.expression.type.function.numeric.atan2
import ch.ergon.dope.extension.expression.type.function.numeric.ceil
import ch.ergon.dope.extension.expression.type.function.numeric.cos
import ch.ergon.dope.extension.expression.type.function.numeric.degrees
import ch.ergon.dope.extension.expression.type.function.numeric.exp
import ch.ergon.dope.extension.expression.type.function.numeric.floor
import ch.ergon.dope.extension.expression.type.function.numeric.ln
import ch.ergon.dope.extension.expression.type.function.numeric.log
import ch.ergon.dope.extension.expression.type.function.numeric.power
import ch.ergon.dope.extension.expression.type.function.numeric.radians
import ch.ergon.dope.extension.expression.type.function.numeric.random
import ch.ergon.dope.extension.expression.type.function.numeric.round
import ch.ergon.dope.extension.expression.type.function.numeric.sign
import ch.ergon.dope.extension.expression.type.function.numeric.sin
import ch.ergon.dope.extension.expression.type.function.numeric.sqrt
import ch.ergon.dope.extension.expression.type.function.numeric.tan
import ch.ergon.dope.extension.expression.type.function.numeric.trunc
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.resolvable.expression.type.function.numeric.AbsoluteExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.ArcCosineExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.ArcSineExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.ArcTangent2Expression
import ch.ergon.dope.resolvable.expression.type.function.numeric.ArcTangentExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.CeilingExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.CosineExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.DegreesExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.ExponentExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.FloorExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.LogExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.LogNaturalisExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.PowerExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.RadiansExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.RandomExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.RoundExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.SignExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.SineExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.SquareRootExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.TangentExpression
import ch.ergon.dope.resolvable.expression.type.function.numeric.TruncationExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NumberFunctionTest {
    @Test
    fun `should support ABS number function with number CMJsonField`() {
        val field = someCMNumberField()
        val expected = AbsoluteExpression(field.toDopeType())

        val actual = abs(field)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ACOS number function with number CMJsonField`() {
        val field = someCMNumberField()
        val expected = ArcCosineExpression(field.toDopeType())

        val actual = acos(field)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ASIN number function with number CMJsonField`() {
        val field = someCMNumberField()
        val expected = ArcSineExpression(field.toDopeType())

        val actual = asin(field)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ATAN number function with number CMJsonField`() {
        val field = someCMNumberField()
        val expected = ArcTangentExpression(field.toDopeType())

        val actual = atan(field)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ATAN2 number function with divisor and dividend as number CMJsonField`() {
        val divisor = someCMNumberField()
        val dividend = someCMNumberField("anotherNumberField")
        val expected = ArcTangent2Expression(divisor.toDopeType(), dividend.toDopeType())

        val actual = atan2(divisor, dividend)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ATAN2 number function with divisor as number CMJsonField and dividend as TypeExpression`() {
        val divisor = someCMNumberField()
        val dividend = 1.toDopeType()
        val expected = ArcTangent2Expression(divisor.toDopeType(), dividend)

        val actual = atan2(divisor, dividend)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ATAN2 number function with divisor as number CMJsonField and dividend as Number`() {
        val divisor = someCMNumberField()
        val dividend = 1
        val expected = ArcTangent2Expression(divisor.toDopeType(), dividend.toDopeType())

        val actual = atan2(divisor, dividend)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ATAN2 number function with divisor as TypeExpression and dividend as number CMJsonField`() {
        val divisor = 1.toDopeType()
        val dividend = someCMNumberField()
        val expected = ArcTangent2Expression(divisor, dividend.toDopeType())

        val actual = atan2(divisor, dividend)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ATAN2 number function with divisor as Number and dividend as number CMJsonField`() {
        val divisor = 1
        val dividend = someCMNumberField()
        val expected = ArcTangent2Expression(divisor.toDopeType(), dividend.toDopeType())

        val actual = atan2(divisor, dividend)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support CEIL number function with number CMJsonField`() {
        val field = someCMNumberField()
        val expected = CeilingExpression(field.toDopeType())

        val actual = ceil(field)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support COS number function with number CMJsonField`() {
        val field = someCMNumberField()
        val expected = CosineExpression(field.toDopeType())

        val actual = cos(field)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support DEGREES number function with number CMJsonField`() {
        val field = someCMNumberField()
        val expected = DegreesExpression(field.toDopeType())

        val actual = degrees(field)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support EXP number function with number CMJsonField`() {
        val field = someCMNumberField()
        val expected = ExponentExpression(field.toDopeType())

        val actual = exp(field)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support LN number function with number CMJsonField`() {
        val field = someCMNumberField()
        val expected = LogNaturalisExpression(field.toDopeType())

        val actual = ln(field)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support LOG number function with number CMJsonField`() {
        val field = someCMNumberField()
        val expected = LogExpression(field.toDopeType())

        val actual = log(field)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support FLOOR number function with number CMJsonField`() {
        val field = someCMNumberField()
        val expected = FloorExpression(field.toDopeType())

        val actual = floor(field)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support POWER number function with base and exponent as number CMJsonField`() {
        val base = someCMNumberField()
        val exponent = someCMNumberField("anotherNumberField")
        val expected = PowerExpression(base.toDopeType(), exponent.toDopeType())

        val actual = power(base, exponent)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support POWER number function with base as number CMJsonField and exponent as TypeExpression`() {
        val base = someCMNumberField()
        val exponent = 1.toDopeType()
        val expected = PowerExpression(base.toDopeType(), exponent)

        val actual = power(base, exponent)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support POWER number function with base as number CMJsonField and exponent as Number`() {
        val base = someCMNumberField()
        val exponent = 1
        val expected = PowerExpression(base.toDopeType(), exponent.toDopeType())

        val actual = power(base, exponent)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support POWER number function with base as TypeExpression and exponent as number CMJsonField`() {
        val base = 1.toDopeType()
        val exponent = someCMNumberField()
        val expected = PowerExpression(base, exponent.toDopeType())

        val actual = power(base, exponent)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support POWER number function with base as Number and exponent as number CMJsonField`() {
        val base = 1
        val exponent = someCMNumberField()
        val expected = PowerExpression(base.toDopeType(), exponent.toDopeType())

        val actual = power(base, exponent)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support RADIANS number function with number CMJsonField`() {
        val field = someCMNumberField()
        val expected = RadiansExpression(field.toDopeType())

        val actual = radians(field)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support RANDOM number function with number CMJsonField`() {
        val field = someCMNumberField()
        val expected = RandomExpression(field.toDopeType())

        val actual = random(field)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ROUND number function with number CMJsonField`() {
        val field = someCMNumberField()
        val expected = RoundExpression(field.toDopeType())

        val actual = round(field)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ROUND number function with value and digits as number CMJsonField`() {
        val field = someCMNumberField()
        val digits = someCMNumberField("anotherNumberField")
        val expected = RoundExpression(field.toDopeType(), digits.toDopeType())

        val actual = round(field, digits)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ROUND number function with value as number CMJsonField and digits as TypeExpression`() {
        val field = someCMNumberField()
        val digits = 1.toDopeType()
        val expected = RoundExpression(field.toDopeType(), digits)

        val actual = round(field, digits)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ROUND number function with value as number CMJsonField and digits as Number`() {
        val field = someCMNumberField()
        val digits = 1
        val expected = RoundExpression(field.toDopeType(), digits.toDopeType())

        val actual = round(field, digits)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ROUND number function with value as TypeExpression and digits as number CMJsonField`() {
        val field = 1.toDopeType()
        val digits = someCMNumberField("anotherNumberField")
        val expected = RoundExpression(field, digits.toDopeType())

        val actual = round(field, digits)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ROUND number function with value as Number and digits as number CMJsonField`() {
        val field = 1
        val digits = someCMNumberField("anotherNumberField")
        val expected = RoundExpression(field.toDopeType(), digits.toDopeType())

        val actual = round(field, digits)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support SIGN number function with number CMJsonField`() {
        val field = someCMNumberField()
        val expected = SignExpression(field.toDopeType())

        val actual = sign(field)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support SIN number function with number CMJsonField`() {
        val field = someCMNumberField()
        val expected = SineExpression(field.toDopeType())

        val actual = sin(field)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support SQRT number function with number CMJsonField`() {
        val field = someCMNumberField()
        val expected = SquareRootExpression(field.toDopeType())

        val actual = sqrt(field)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TAN number function with number CMJsonField`() {
        val field = someCMNumberField()
        val expected = TangentExpression(field.toDopeType())

        val actual = tan(field)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TRUNC number function with number CMJsonField`() {
        val field = someCMNumberField()
        val expected = TruncationExpression(field.toDopeType())

        val actual = trunc(field)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TRUNC number function with value and digits as number CMJsonField`() {
        val field = someCMNumberField()
        val digits = someCMNumberField("anotherNumberField")
        val expected = TruncationExpression(field.toDopeType(), digits.toDopeType())

        val actual = trunc(field, digits)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TRUNC number function with value as number CMJsonField and digits as TypeExpression`() {
        val field = someCMNumberField()
        val digits = 1.toDopeType()
        val expected = TruncationExpression(field.toDopeType(), digits)

        val actual = trunc(field, digits)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TRUNC number function with value as number CMJsonField and digits as Number`() {
        val field = someCMNumberField()
        val digits = 1
        val expected = TruncationExpression(field.toDopeType(), digits.toDopeType())

        val actual = trunc(field, digits)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TRUNC number function with value as TypeExpression and digits as number CMJsonField`() {
        val field = 1.toDopeType()
        val digits = someCMNumberField("anotherNumberField")
        val expected = TruncationExpression(field, digits.toDopeType())

        val actual = trunc(field, digits)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TRUNC number function with value as Number and digits as number CMJsonField`() {
        val field = 1
        val digits = someCMNumberField("anotherNumberField")
        val expected = TruncationExpression(field.toDopeType(), digits.toDopeType())

        val actual = trunc(field, digits)

        assertEquals(expected, actual)
    }
}
