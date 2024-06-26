package ch.ergon.dope

import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.alias
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction.abs
import ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction.acos
import ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction.asin
import ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction.atan
import ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction.atan2
import ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction.ceil
import ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction.cos
import ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction.degrees
import ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction.e
import ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction.exp
import ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction.floor
import ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction.ln
import ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction.log
import ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction.pi
import ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction.power
import ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction.radians
import ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction.random
import ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction.round
import ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction.sign
import ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction.sin
import ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction.sqrt
import ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction.tan
import ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction.trunc
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import junit.framework.TestCase.assertEquals
import kotlin.test.BeforeTest
import kotlin.test.Test

class NumberFunctionsTest {
    private lateinit var create: QueryBuilder

    @BeforeTest
    fun setup() {
        create = QueryBuilder()
    }

    @Test
    fun `should support numberType alias`() {
        val expected = "SELECT 12 AS `someNumber`"

        val actual: String = create
            .select(
                12.toDopeType().alias("someNumber"),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ABS expression`() {
        val expected = "ABS(-1)"

        val actual = abs(-1).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ABS expression with number field`() {
        val expected = "ABS(`numberField`)"

        val actual = abs(someNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ACOS expression`() {
        val expected = "ACOS(-1)"

        val actual = acos(-1).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ACOS expression with number field`() {
        val expected = "ACOS(`numberField`)"

        val actual = acos(someNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ASIN expression`() {
        val expected = "ASIN(-1)"

        val actual = asin(-1).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ASIN expression with number field`() {
        val expected = "ASIN(`numberField`)"

        val actual = asin(someNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ATAN expression`() {
        val expected = "ATAN(-1)"

        val actual = atan(-1).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ATAN expression with number field`() {
        val expected = "ATAN(`numberField`)"

        val actual = atan(someNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ATAN2 expression`() {
        val expected = "ATAN2(1, 0.5)"

        val actual = atan2(1, 0.5).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ATAN2 expression with number field as divisor`() {
        val expected = "ATAN2(`numberField`, 0.5)"

        val actual = atan2(someNumberField(), 0.5).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ATAN2 expression with number field as dividend`() {
        val expected = "ATAN2(0.5, `numberField`)"

        val actual = atan2(0.5, someNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ATAN2 expression with number field as divisor and dividend`() {
        val expected = "ATAN2(`numberField`, `anotherNumberField`)"

        val actual = atan2(someNumberField(), someNumberField("anotherNumberField")).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support CEIL expression`() {
        val expected = "CEIL(3.14)"

        val actual = ceil(3.14).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support CEIL expression with number field`() {
        val expected = "CEIL(`numberField`)"

        val actual = ceil(someNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support COS expression`() {
        val expected = "COS(-1)"

        val actual = cos(-1).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support COS expression with number field`() {
        val expected = "COS(`numberField`)"

        val actual = cos(someNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support DEGREES expression`() {
        val expected = "DEGREES(-1)"

        val actual = degrees(-1).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support DEGREES expression with number field`() {
        val expected = "DEGREES(`numberField`)"

        val actual = degrees(someNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support E (euler) expression`() {
        val expected = "E()"

        val actual = e().toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support EXP expression`() {
        val expected = "EXP(-1)"

        val actual = exp(-1).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support EXP expression with number field`() {
        val expected = "EXP(`numberField`)"

        val actual = exp(someNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support LN expression`() {
        val expected = "LN(1)"

        val actual = ln(1).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support LN expression with number field`() {
        val expected = "LN(`numberField`)"

        val actual = ln(someNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support LOG expression`() {
        val expected = "LOG(1)"

        val actual = log(1).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support LOG expression with number field`() {
        val expected = "LOG(`numberField`)"

        val actual = log(someNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support FLOOR expression`() {
        val expected = "FLOOR(3.14)"

        val actual = floor(3.14).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support FLOOR expression with number field`() {
        val expected = "FLOOR(`numberField`)"

        val actual = floor(someNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support PI expression`() {
        val expected = "PI()"

        val actual = pi().toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support POWER expression`() {
        val expected = "POWER(2, 3)"

        val actual = power(2, 3).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support POWER expression with base as number field`() {
        val expected = "POWER(`numberField`, 3)"

        val actual = power(someNumberField(), 3).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support POWER expression with exponent as number field`() {
        val expected = "POWER(3.14, `numberField`)"

        val actual = power(3.14, someNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support POWER expression with base and exponent as number fields`() {
        val expected = "POWER(`numberField`, `anotherNumberField`)"

        val actual = power(someNumberField(), someNumberField("anotherNumberField")).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support RADIANS expression`() {
        val expected = "RADIANS(180)"

        val actual = radians(180).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support RADIANS expression with number field`() {
        val expected = "RADIANS(`numberField`)"

        val actual = radians(someNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support RANDOM expression`() {
        val expected = "RANDOM()"

        val actual = random().toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support RANDOM expression with seed`() {
        val expected = "RANDOM(1)"

        val actual = random(1).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support RANDOM expression with number field as seed`() {
        val expected = "RANDOM(`numberField`)"

        val actual = random(someNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ROUND expression`() {
        val expected = "ROUND(3.14)"

        val actual = round(3.14).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ROUND expression with number field`() {
        val expected = "ROUND(`numberField`)"

        val actual = round(someNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ROUND expression with digits`() {
        val expected = "ROUND(3.14, 1)"

        val actual = round(3.14, 1).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ROUND expression with number field and digits`() {
        val expected = "ROUND(`numberField`, 1)"

        val actual = round(someNumberField(), 1).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ROUND expression with digits as number field`() {
        val expected = "ROUND(3.14, `numberField`)"

        val actual = round(3.14, someNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ROUND expression with number field and digits as number fields`() {
        val expected = "ROUND(`numberField`, `anotherNumberField`)"

        val actual = round(someNumberField(), someNumberField("anotherNumberField")).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support SIGN expression`() {
        val expected = "SIGN(-1)"

        val actual = sign(-1).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support SIGN expression with number field`() {
        val expected = "SIGN(`numberField`)"

        val actual = sign(someNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support SIN expression`() {
        val expected = "SIN(3.14)"

        val actual = sin(3.14).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support SIN expression with number field`() {
        val expected = "SIN(`numberField`)"

        val actual = sin(someNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support SQRT expression`() {
        val expected = "SQRT(16)"

        val actual = sqrt(16).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support SQRT expression with number field`() {
        val expected = "SQRT(`numberField`)"

        val actual = sqrt(someNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TAN expression`() {
        val expected = "TAN(3.14)"

        val actual = tan(3.14).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TAN expression with number field`() {
        val expected = "TAN(`numberField`)"

        val actual = tan(someNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TRUNC expression`() {
        val expected = "TRUNC(3.14)"

        val actual = trunc(3.14).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TRUNC expression with number field`() {
        val expected = "TRUNC(`numberField`)"

        val actual = trunc(someNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TRUNC expression with digits`() {
        val expected = "TRUNC(3.14, 1)"

        val actual = trunc(3.14, 1).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TRUNC expression with number field and digits`() {
        val expected = "TRUNC(`numberField`, 1)"

        val actual = trunc(someNumberField(), 1).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TRUNC expression with digits as number field`() {
        val expected = "TRUNC(3.14, `numberField`)"

        val actual = trunc(3.14, someNumberField()).toDopeQuery().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TRUNC expression with number field and digits as number fields`() {
        val expected = "TRUNC(`numberField`, `anotherNumberField`)"

        val actual = trunc(someNumberField(), someNumberField("anotherNumberField")).toDopeQuery().queryString

        assertEquals(expected, actual)
    }
}
