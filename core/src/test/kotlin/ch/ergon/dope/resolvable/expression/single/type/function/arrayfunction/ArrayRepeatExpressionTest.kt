package ch.ergon.dope.resolvable.expression.single.type.function.arrayfunction

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.single.type.asParameter
import ch.ergon.dope.resolvable.expression.single.type.function.array.ArrayRepeatExpression
import ch.ergon.dope.resolvable.expression.single.type.function.array.arrayRepeat
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayRepeatExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_REPEAT`() {
        val expected = DopeQuery(
            queryString = "ARRAY_REPEAT(`numberField`, `numberField`)",
        )
        val underTest = ArrayRepeatExpression(someNumberField(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPEAT with positional parameter`() {
        val parameterValue = someNumber()
        val expected = DopeQuery(
            queryString = "ARRAY_REPEAT($1, `numberField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayRepeatExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPEAT with positional parameter as value`() {
        val parameterValue = someNumber()
        val expected = DopeQuery(
            queryString = "ARRAY_REPEAT(`numberField`, $1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayRepeatExpression(someNumberField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPEAT with all positional parameters`() {
        val parameterValue = someNumber()
        val parameterValue2 = someNumber()
        val expected = DopeQuery(
            queryString = "ARRAY_REPEAT($1, $2)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = ArrayRepeatExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPEAT with named parameter`() {
        val parameterValue = someNumber()
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "ARRAY_REPEAT(\$$parameterName, `numberField`)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayRepeatExpression(parameterValue.asParameter(parameterName), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPEAT with named parameter as value`() {
        val parameterValue = someNumber()
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "ARRAY_REPEAT(`numberField`, \$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayRepeatExpression(someNumberField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPEAT with named and positional parameters`() {
        val parameterValue = someNumber()
        val parameterValue2 = someNumber()
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "ARRAY_REPEAT(\$$parameterName, $1)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue), positionalParameters = listOf(parameterValue2)),
        )
        val underTest = ArrayRepeatExpression(parameterValue.asParameter(parameterName), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REPEAT extension type type`() {
        val value = someNumberField()
        val repetitions = someNumberField()
        val expected = ArrayRepeatExpression(value, repetitions)

        val actual = arrayRepeat(value, repetitions)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_REPEAT extension type number`() {
        val value = someNumberField()
        val repetitions = someNumber()
        val expected = ArrayRepeatExpression(value, repetitions.toDopeType())

        val actual = arrayRepeat(value, repetitions)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
