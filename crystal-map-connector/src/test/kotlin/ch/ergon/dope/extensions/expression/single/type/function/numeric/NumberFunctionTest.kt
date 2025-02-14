package ch.ergon.dope.extensions.expression.single.type.function.numeric

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.expression.single.type.function.numeric.abs
import ch.ergon.dope.extension.expression.single.type.function.numeric.acos
import ch.ergon.dope.extension.expression.single.type.function.numeric.asin
import ch.ergon.dope.extension.expression.single.type.function.numeric.atan
import ch.ergon.dope.extension.expression.single.type.function.numeric.atan2
import ch.ergon.dope.extension.expression.single.type.function.numeric.ceil
import ch.ergon.dope.extension.expression.single.type.function.numeric.cos
import ch.ergon.dope.extension.expression.single.type.function.numeric.degrees
import ch.ergon.dope.extension.expression.single.type.function.numeric.exp
import ch.ergon.dope.extension.expression.single.type.function.numeric.floor
import ch.ergon.dope.extension.expression.single.type.function.numeric.ln
import ch.ergon.dope.extension.expression.single.type.function.numeric.log
import ch.ergon.dope.extension.expression.single.type.function.numeric.power
import ch.ergon.dope.extension.expression.single.type.function.numeric.radians
import ch.ergon.dope.extension.expression.single.type.function.numeric.random
import ch.ergon.dope.extension.expression.single.type.function.numeric.round
import ch.ergon.dope.extension.expression.single.type.function.numeric.sign
import ch.ergon.dope.extension.expression.single.type.function.numeric.sin
import ch.ergon.dope.extension.expression.single.type.function.numeric.sqrt
import ch.ergon.dope.extension.expression.single.type.function.numeric.tan
import ch.ergon.dope.extension.expression.single.type.function.numeric.trunc
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NumberFunctionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ABS number function with number CMJsonField`() {
        val expected = "ABS(`cmNumberField`)"

        val actual = abs(someCMNumberField()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ACOS number function with number CMJsonField`() {
        val expected = "ACOS(`cmNumberField`)"

        val actual = acos(someCMNumberField()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ASIN number function with number CMJsonField`() {
        val expected = "ASIN(`cmNumberField`)"

        val actual = asin(someCMNumberField()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ATAN number function with number CMJsonField`() {
        val expected = "ATAN(`cmNumberField`)"

        val actual = atan(someCMNumberField()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ATAN2 number function with divisor and dividend as number CMJsonField`() {
        val expected = "ATAN2(`cmNumberField`, `anotherNumberField`)"

        val actual = atan2(someCMNumberField(), someCMNumberField("anotherNumberField")).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ATAN2 number function with divisor as number CMJsonField and dividend as TypeExpression`() {
        val expected = "ATAN2(`cmNumberField`, 1)"

        val actual = atan2(someCMNumberField(), 1.toDopeType()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ATAN2 number function with divisor as number CMJsonField and dividend as Number`() {
        val expected = "ATAN2(`cmNumberField`, 1)"

        val actual = atan2(someCMNumberField(), 1).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ATAN2 number function with divisor as TypeExpression and dividend as number CMJsonField`() {
        val expected = "ATAN2(1, `cmNumberField`)"

        val actual = atan2(1.toDopeType(), someCMNumberField()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ATAN2 number function with divisor as Number and dividend as number CMJsonField`() {
        val expected = "ATAN2(1, `cmNumberField`)"

        val actual = atan2(1, someCMNumberField()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support CEIL number function with number CMJsonField`() {
        val expected = "CEIL(`cmNumberField`)"

        val actual = ceil(someCMNumberField()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support COS number function with number CMJsonField`() {
        val expected = "COS(`cmNumberField`)"

        val actual = cos(someCMNumberField()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support DEGREES number function with number CMJsonField`() {
        val expected = "DEGREES(`cmNumberField`)"

        val actual = degrees(someCMNumberField()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support EXP number function with number CMJsonField`() {
        val expected = "EXP(`cmNumberField`)"

        val actual = exp(someCMNumberField()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support LN number function with number CMJsonField`() {
        val expected = "LN(`cmNumberField`)"

        val actual = ln(someCMNumberField()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support LOG number function with number CMJsonField`() {
        val expected = "LOG(`cmNumberField`)"

        val actual = log(someCMNumberField()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support FLOOR number function with number CMJsonField`() {
        val expected = "FLOOR(`cmNumberField`)"

        val actual = floor(someCMNumberField()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support POWER number function with base and exponent as number CMJsonField`() {
        val expected = "POWER(`cmNumberField`, `anotherNumberField`)"

        val actual = power(someCMNumberField(), someCMNumberField("anotherNumberField")).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support POWER number function with base as number CMJsonField and exponent as TypeExpression`() {
        val expected = "POWER(`cmNumberField`, 1)"

        val actual = power(someCMNumberField(), 1.toDopeType()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support POWER number function with base as number CMJsonField and exponent as Number`() {
        val expected = "POWER(`cmNumberField`, 1)"

        val actual = power(someCMNumberField(), 1).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support POWER number function with base as TypeExpression and exponent as number CMJsonField`() {
        val expected = "POWER(1, `cmNumberField`)"

        val actual = power(1.toDopeType(), someCMNumberField()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support POWER number function with base as Number and exponent as number CMJsonField`() {
        val expected = "POWER(1, `cmNumberField`)"

        val actual = power(1, someCMNumberField()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support RADIANS number function with number CMJsonField`() {
        val expected = "RADIANS(`cmNumberField`)"

        val actual = radians(someCMNumberField()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support RANDOM number function with number CMJsonField`() {
        val expected = "RANDOM(`cmNumberField`)"

        val actual = random(someCMNumberField()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ROUND number function with number CMJsonField`() {
        val expected = "ROUND(`cmNumberField`)"

        val actual = round(someCMNumberField()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ROUND number function with value and digits as number CMJsonField`() {
        val expected = "ROUND(`cmNumberField`, `anotherNumberField`)"

        val actual = round(someCMNumberField(), someCMNumberField("anotherNumberField")).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ROUND number function with value as number CMJsonField and digits as TypeExpression`() {
        val expected = "ROUND(`cmNumberField`, 1)"

        val actual = round(someCMNumberField(), 1.toDopeType()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ROUND number function with value as number CMJsonField and digits as Number`() {
        val expected = "ROUND(`cmNumberField`, 1)"

        val actual = round(someCMNumberField(), 1).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ROUND number function with value as TypeExpression and digits as number CMJsonField`() {
        val expected = "ROUND(1, `cmNumberField`)"

        val actual = round(1.toDopeType(), someCMNumberField()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ROUND number function with value as Number and digits as number CMJsonField`() {
        val expected = "ROUND(1, `cmNumberField`)"

        val actual = round(1, someCMNumberField()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support SIGN number function with number CMJsonField`() {
        val expected = "SIGN(`cmNumberField`)"

        val actual = sign(someCMNumberField()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support SIN number function with number CMJsonField`() {
        val expected = "SIN(`cmNumberField`)"

        val actual = sin(someCMNumberField()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support SQRT number function with number CMJsonField`() {
        val expected = "SQRT(`cmNumberField`)"

        val actual = sqrt(someCMNumberField()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TAN number function with number CMJsonField`() {
        val expected = "TAN(`cmNumberField`)"

        val actual = tan(someCMNumberField()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TRUNC number function with number CMJsonField`() {
        val expected = "TRUNC(`cmNumberField`)"

        val actual = trunc(someCMNumberField()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TRUNC number function with value and digits as number CMJsonField`() {
        val expected = "TRUNC(`cmNumberField`, `anotherNumberField`)"

        val actual = trunc(someCMNumberField(), someCMNumberField("anotherNumberField")).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TRUNC number function with value as number CMJsonField and digits as TypeExpression`() {
        val expected = "TRUNC(`cmNumberField`, 1)"

        val actual = trunc(someCMNumberField(), 1.toDopeType()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TRUNC number function with value as number CMJsonField and digits as Number`() {
        val expected = "TRUNC(`cmNumberField`, 1)"

        val actual = trunc(someCMNumberField(), 1).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TRUNC number function with value as TypeExpression and digits as number CMJsonField`() {
        val expected = "TRUNC(1, `cmNumberField`)"

        val actual = trunc(1.toDopeType(), someCMNumberField()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TRUNC number function with value as Number and digits as number CMJsonField`() {
        val expected = "TRUNC(1, `cmNumberField`)"

        val actual = trunc(1, someCMNumberField()).toDopeQuery(manager).queryString

        assertEquals(expected, actual)
    }
}
