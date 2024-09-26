package ch.ergon.dope.resolvable.expression.unaliased.type.function.comparison

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class GreatestExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support greatest expression`() {
        val expected = DopeQuery(
            "GREATEST(`numberField`, `anotherNumberField`)",
            emptyMap(),
            emptyList(),
        )
        val underTest = GreatestExpression(someNumberField(), someNumberField("anotherNumberField"))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greatest expression with positional parameter`() {
        val parameterValue = someNumber()
        val expected = DopeQuery(
            "GREATEST($1, `numberField`)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = GreatestExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greatest expression with positional parameter as value`() {
        val parameterValue = someNumber()
        val expected = DopeQuery(
            "GREATEST(`numberField`, $1)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = GreatestExpression(someNumberField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greatest expression with all positional parameters`() {
        val parameterValue = someNumber()
        val parameterValue2 = someNumber()
        val expected = DopeQuery(
            "GREATEST($1, $2)",
            emptyMap(),
            listOf(parameterValue, parameterValue2),
        )
        val underTest = GreatestExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greatest expression with named parameter`() {
        val parameterValue = someNumber()
        val parameterName = "param"
        val expected = DopeQuery(
            "GREATEST(\$$parameterName, `numberField`)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = GreatestExpression(parameterValue.asParameter(parameterName), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greatest expression with named parameter as value`() {
        val parameterValue = someNumber()
        val parameterName = "param"
        val expected = DopeQuery(
            "GREATEST(`numberField`, \$$parameterName)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = GreatestExpression(someNumberField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greatest expression with all named parameters`() {
        val parameterValue = someNumber()
        val parameterName1 = "param1"
        val parameterValue2 = someNumber()
        val parameterName2 = "param2"
        val expected = DopeQuery(
            "GREATEST(\$$parameterName1, \$$parameterName2)",
            mapOf(parameterName1 to parameterValue, parameterName2 to parameterValue2),
            emptyList(),
        )
        val underTest = GreatestExpression(parameterValue.asParameter(parameterName1), parameterValue2.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greatest expression with mixed named and positional parameters`() {
        val parameterValue = someNumber()
        val parameterName = "param"
        val parameterValue2 = someNumber()
        val expected = DopeQuery(
            "GREATEST(\$$parameterName, $1)",
            mapOf(parameterName to parameterValue),
            listOf(parameterValue2),
        )
        val underTest = GreatestExpression(parameterValue.asParameter(parameterName), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greatest expression function`() {
        val numberField = someNumberField()
        val numberField2 = someNumberField()
        val expected = GreatestExpression(numberField, numberField2)

        val actual = greatestOf(numberField, numberField2)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support greatest expression function with additional expressions`() {
        val numberField = someNumberField()
        val numberField2 = someNumberField()
        val numberField3 = someNumberField()
        val numberField4 = someNumberField()
        val expected = GreatestExpression(numberField, numberField2, numberField3, numberField4)

        val actual = greatestOf(numberField, numberField2, numberField3, numberField4)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
