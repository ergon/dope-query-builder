package ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic

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

class NegationExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support negation`() {
        val expected = DopeQuery(
            queryString = "-`numberField`",
        )
        val underTest = NegationExpression(someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support negation with positional parameter`() {
        val parameterValue = 4
        val expected = DopeQuery(
            queryString = "-$1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = NegationExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support negation with named parameter`() {
        val parameterValue = 4
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "-\$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = NegationExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support negation function type`() {
        val type = someNumberField()
        val expected = NegationExpression(type)

        val actual = neg(type)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support negation function number`() {
        val number = someNumber()
        val expected = NegationExpression(number.toDopeType())

        val actual = neg(number)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
