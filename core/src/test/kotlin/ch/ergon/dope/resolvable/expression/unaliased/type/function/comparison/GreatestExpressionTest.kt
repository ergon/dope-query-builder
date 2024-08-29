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
        )
        val underTest = GreatestExpression(someNumberField(), someNumberField("anotherNumberField"))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greatest expression with first parameter`() {
        val parameterValue = someNumber()
        val expected = DopeQuery(
            "GREATEST($1, `numberField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = GreatestExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greatest expression with second parameter`() {
        val parameterValue = someNumber()
        val expected = DopeQuery(
            "GREATEST(`numberField`, $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = GreatestExpression(someNumberField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greatest expression with all parameters`() {
        val parameterValue = someNumber()
        val parameterValue2 = someNumber()
        val expected = DopeQuery(
            "GREATEST($1, $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = GreatestExpression(parameterValue.asParameter(), parameterValue2.asParameter())

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