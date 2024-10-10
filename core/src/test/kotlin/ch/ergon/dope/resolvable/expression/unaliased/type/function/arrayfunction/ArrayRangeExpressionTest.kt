package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayRangeExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_RANGE`() {
        val expected = DopeQuery(
            queryString = "ARRAY_RANGE(0, 10)",
        )
        val underTest = ArrayRangeExpression(0.toDopeType(), 10.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_RANGE with step`() {
        val expected = DopeQuery(
            queryString = "ARRAY_RANGE(0, 10, 2)",
        )
        val underTest = ArrayRangeExpression(0.toDopeType(), 10.toDopeType(), 2.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_RANGE with named parameter as start value`() {
        val parameterValue = someNumber()
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "ARRAY_RANGE(\$$parameterName, 10)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayRangeExpression(parameterValue.asParameter(parameterName), 10.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_RANGE with positional parameter as start value`() {
        val parameterValue = someNumber()
        val expected = DopeQuery(
            queryString = "ARRAY_RANGE($1, 10)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayRangeExpression(parameterValue.asParameter(), 10.toDopeType())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_RANGE with named parameter as end value`() {
        val parameterValue = someNumber()
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "ARRAY_RANGE(0, \$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayRangeExpression(0.toDopeType(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_RANGE with positional parameter as end value`() {
        val parameterValue = someNumber()
        val expected = DopeQuery(
            queryString = "ARRAY_RANGE(0, $1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayRangeExpression(0.toDopeType(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_RANGE with named parameters as start and end`() {
        val parameterValue = someNumber()
        val parameterValue2 = someNumber()
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "ARRAY_RANGE(\$$parameterName, \$$parameterName2)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = ArrayRangeExpression(parameterValue.asParameter(parameterName), parameterValue2.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_RANGE with positional parameters as start and end`() {
        val parameterValue = someNumber()
        val parameterValue2 = someNumber()
        val expected = DopeQuery(
            queryString = "ARRAY_RANGE($1, $2)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = ArrayRangeExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_RANGE with all named parameters`() {
        val parameterValue = someNumber()
        val parameterValue2 = someNumber()
        val parameterValue3 = someNumber()
        val parameterName = "param1"
        val parameterName2 = "param2"
        val parameterName3 = "param3"
        val expected = DopeQuery(
            queryString = "ARRAY_RANGE(\$$parameterName, \$$parameterName2, \$$parameterName3)",
            DopeParameters(
                namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2, parameterName3 to parameterValue3),
            ),
        )
        val underTest = ArrayRangeExpression(
            parameterValue.asParameter(parameterName),
            parameterValue2.asParameter(parameterName2),
            parameterValue3.asParameter(parameterName3),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_RANGE with all positional parameters`() {
        val parameterValue = someNumber()
        val parameterValue2 = someNumber()
        val parameterValue3 = someNumber()
        val expected = DopeQuery(
            queryString = "ARRAY_RANGE($1, $2, $3)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2, parameterValue3)),
        )
        val underTest = ArrayRangeExpression(
            parameterValue.asParameter(),
            parameterValue2.asParameter(),
            parameterValue3.asParameter(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_RANGE extension type type type`() {
        val start = someNumberField()
        val end = someNumberField()
        val step = someNumberField()
        val expected = ArrayRangeExpression(start, end, step)

        val actual = arrayRange(start, end, step)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_RANGE extension type type number`() {
        val start = someNumberField()
        val end = someNumberField()
        val step = someNumber()
        val expected = ArrayRangeExpression(start, end, step.toDopeType())

        val actual = arrayRange(start, end, step)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_RANGE extension type number type`() {
        val start = someNumberField()
        val end = someNumber()
        val step = someNumberField()
        val expected = ArrayRangeExpression(start, end.toDopeType(), step)

        val actual = arrayRange(start, end, step)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_RANGE extension number type type`() {
        val start = someNumber()
        val end = someNumberField()
        val step = someNumberField()
        val expected = ArrayRangeExpression(start.toDopeType(), end, step)

        val actual = arrayRange(start, end, step)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_RANGE extension type number number`() {
        val start = someNumberField()
        val end = someNumber()
        val step = someNumber()
        val expected = ArrayRangeExpression(start, end.toDopeType(), step.toDopeType())

        val actual = arrayRange(start, end, step)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_RANGE extension number type number`() {
        val start = someNumber()
        val end = someNumberField()
        val step = someNumber()
        val expected = ArrayRangeExpression(start.toDopeType(), end, step.toDopeType())

        val actual = arrayRange(start, end, step)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_RANGE extension number number type`() {
        val start = someNumber()
        val end = someNumber()
        val step = someNumberField()
        val expected = ArrayRangeExpression(start.toDopeType(), end.toDopeType(), step)

        val actual = arrayRange(start, end, step)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_RANGE extension number number number`() {
        val start = someNumber()
        val end = someNumber()
        val step = someNumber()
        val expected = ArrayRangeExpression(start.toDopeType(), end.toDopeType(), step.toDopeType())

        val actual = arrayRange(start, end, step)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
