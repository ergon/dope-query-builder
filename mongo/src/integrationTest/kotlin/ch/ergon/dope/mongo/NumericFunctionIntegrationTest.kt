package ch.ergon.dope.mongo

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.mongo.integrationTest.BaseIntegrationTest
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.age
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.name
import ch.ergon.dope.mongo.integrationTest.TestMongoDatabase.users
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.function.numeric.abs
import ch.ergon.dope.resolvable.expression.type.function.numeric.acos
import ch.ergon.dope.resolvable.expression.type.function.numeric.asin
import ch.ergon.dope.resolvable.expression.type.function.numeric.atan
import ch.ergon.dope.resolvable.expression.type.function.numeric.atan2
import ch.ergon.dope.resolvable.expression.type.function.numeric.ceil
import ch.ergon.dope.resolvable.expression.type.function.numeric.cos
import ch.ergon.dope.resolvable.expression.type.function.numeric.degrees
import ch.ergon.dope.resolvable.expression.type.function.numeric.e
import ch.ergon.dope.resolvable.expression.type.function.numeric.exp
import ch.ergon.dope.resolvable.expression.type.function.numeric.floor
import ch.ergon.dope.resolvable.expression.type.function.numeric.ln
import ch.ergon.dope.resolvable.expression.type.function.numeric.log
import ch.ergon.dope.resolvable.expression.type.function.numeric.pi
import ch.ergon.dope.resolvable.expression.type.function.numeric.power
import ch.ergon.dope.resolvable.expression.type.function.numeric.radians
import ch.ergon.dope.resolvable.expression.type.function.numeric.random
import ch.ergon.dope.resolvable.expression.type.function.numeric.round
import ch.ergon.dope.resolvable.expression.type.function.numeric.sign
import ch.ergon.dope.resolvable.expression.type.function.numeric.sin
import ch.ergon.dope.resolvable.expression.type.function.numeric.sqrt
import ch.ergon.dope.resolvable.expression.type.function.numeric.tan
import ch.ergon.dope.resolvable.expression.type.function.numeric.trunc
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class NumericFunctionIntegrationTest : BaseIntegrationTest() {
    private fun selectComputed(expression: TypeExpression<*>) =
        executeQuery(
            QueryBuilder
                .select(expression.alias("result"))
                .from(users)
                .where(name.isEqualTo("John Smith"))
                .buildMongo(resolver),
        )

    @Test
    fun `abs of negative literal`() {
        val result = selectComputed(abs(-5))
        assertEquals(5, result[0]["result"])
    }

    @Test
    fun `sign of negative literal is minus one`() {
        val result = selectComputed(sign(-5))
        assertEquals(-1, result[0]["result"])
    }

    @Test
    fun `sign of positive literal is one`() {
        val result = selectComputed(sign(42))
        assertEquals(1, result[0]["result"])
    }

    @Test
    fun `random yields a double between zero and one`() {
        val value = selectComputed(random())[0]["result"] as Double
        assertTrue(value in 0.0..1.0, "random $value not in [0, 1]")
    }

    @Test
    fun `acos of one is zero`() {
        val result = selectComputed(acos(1))
        assertEquals(0.0, result[0]["result"])
    }

    @Test
    fun `asin of zero is zero`() {
        val result = selectComputed(asin(0))
        assertEquals(0.0, result[0]["result"])
    }

    @Test
    fun `atan of zero is zero`() {
        val result = selectComputed(atan(0))
        assertEquals(0.0, result[0]["result"])
    }

    @Test
    fun `atan2 of one and one is quarter pi`() {
        val result = selectComputed(atan2(1, 1))
        assertEquals(PI / 4, result[0]["result"])
    }

    @Test
    fun `ceil of decimal literal`() {
        val result = selectComputed(ceil(2.1))
        assertEquals(3.0, result[0]["result"])
    }

    @Test
    fun `cos of zero is one`() {
        val result = selectComputed(cos(0))
        assertEquals(1.0, result[0]["result"])
    }

    @Test
    fun `degrees of pi is one hundred eighty`() {
        val result = selectComputed(degrees(PI))
        assertEquals(180.0, result[0]["result"])
    }

    @Test
    fun `euler constant`() {
        val result = selectComputed(e())
        assertEquals(2.718281828459045, result[0]["result"])
    }

    @Test
    fun `exp of zero is one`() {
        val result = selectComputed(exp(0))
        assertEquals(1.0, result[0]["result"])
    }

    @Test
    fun `floor of decimal literal`() {
        val result = selectComputed(floor(2.9))
        assertEquals(2.0, result[0]["result"])
    }

    @Test
    fun `log base ten of one thousand is three`() {
        val result = selectComputed(log(1000))
        assertEquals(3.0, result[0]["result"])
    }

    @Test
    fun `ln of one is zero`() {
        val result = selectComputed(ln(1))
        assertEquals(0.0, result[0]["result"])
    }

    @Test
    fun `pi constant`() {
        val result = selectComputed(pi())
        assertEquals(3.141592653589793, result[0]["result"])
    }

    @Test
    fun `power of two raised to ten is one thousand twenty four`() {
        val result = selectComputed(power(2, 10))
        assertEquals(1024, result[0]["result"])
    }

    @Test
    fun `radians of one hundred eighty is pi`() {
        val result = selectComputed(radians(180))
        assertEquals(PI, result[0]["result"])
    }

    @Test
    fun `round to two digits`() {
        val result = selectComputed(round(3.14159, 2))
        assertEquals(3.14, result[0]["result"])
    }

    @Test
    fun `round without digits uses bankers rounding`() {
        val result = selectComputed(round(2.5))
        assertEquals(2.0, result[0]["result"])
    }

    @Test
    fun `sin of zero is zero`() {
        val result = selectComputed(sin(0))
        assertEquals(0.0, result[0]["result"])
    }

    @Test
    fun `sqrt of nine is three`() {
        val result = selectComputed(sqrt(9))
        assertEquals(3.0, result[0]["result"])
    }

    @Test
    fun `tan of zero is zero`() {
        val result = selectComputed(tan(0))
        assertEquals(0.0, result[0]["result"])
    }

    @Test
    fun `trunc to one digit`() {
        val result = selectComputed(trunc(3.789, 1))
        assertEquals(3.7, result[0]["result"])
    }

    @Test
    fun `trunc without digits truncates to integer`() {
        val result = selectComputed(trunc(3.789))
        assertEquals(3.0, result[0]["result"])
    }

    @Test
    fun `abs of stored age field`() {
        val result = selectComputed(abs(age))
        assertEquals(30, result[0]["result"])
    }
}
