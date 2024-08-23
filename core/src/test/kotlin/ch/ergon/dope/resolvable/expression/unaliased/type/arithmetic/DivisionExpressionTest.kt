package ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class DivisionExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support division`() {
        val expected = DopeQuery(
            "(`numberField` / `numberField`)",
            emptyMap(),
            manager,
        )
        val underTest = DivisionExpression(someNumberField(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support division with parameter`() {
        val parameterValue = 4
        val expected = DopeQuery(
            "($1 / `numberField`)",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = DivisionExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support division with all parameters`() {
        val parameterValue = 4
        val parameterValue2 = 5
        val expected = DopeQuery(
            "($1 / $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
            manager,
        )
        val underTest = DivisionExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support division with second parameter`() {
        val parameterValue = 4
        val expected = DopeQuery(
            "(`numberField` / $1)",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = DivisionExpression(someNumberField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support division function type type`() {
        val left = someNumberField()
        val right = someNumberField()
        val expected = DivisionExpression(left, right)

        val actual = left.div(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support division function type number`() {
        val left = someNumberField()
        val right = someNumber()
        val expected = DivisionExpression(left, right.toDopeType())

        val actual = left.div(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support division function number type`() {
        val left = someNumber()
        val right = someNumberField()
        val expected = DivisionExpression(left.toDopeType(), right)

        val actual = left.div(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
