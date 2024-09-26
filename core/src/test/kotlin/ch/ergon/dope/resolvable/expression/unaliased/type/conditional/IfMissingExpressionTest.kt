package ch.ergon.dope.resolvable.expression.unaliased.type.conditional

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.IfMissingExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.ifMissing
import kotlin.test.Test
import kotlin.test.assertEquals

class IfMissingExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support if missing`() {
        val expected = DopeQuery(
            "IFMISSING(`stringField`, `stringField`)",
            emptyMap(),
            emptyList(),
        )
        val underTest = IfMissingExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing with positional parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "IFMISSING($1, `stringField`)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = IfMissingExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing with named parameter`() {
        val parameterValue = someString()
        val parameterName = "param"
        val expected = DopeQuery(
            "IFMISSING(\$$parameterName, `stringField`)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = IfMissingExpression(parameterValue.asParameter(parameterName), someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing with positional second parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "IFMISSING(`stringField`, $1)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = IfMissingExpression(someStringField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing with named second parameter`() {
        val parameterValue = someString()
        val parameterName = "param"
        val expected = DopeQuery(
            "IFMISSING(`stringField`, \$$parameterName)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = IfMissingExpression(someStringField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing with positional all parameters`() {
        val parameterValue = someString()
        val parameterValue2 = someString()
        val expected = DopeQuery(
            "IFMISSING($1, $2)",
            emptyMap(),
            listOf(parameterValue, parameterValue2),
        )
        val underTest = IfMissingExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing with named all parameters`() {
        val parameterValue = someString()
        val parameterValue2 = someString()
        val parameterName1 = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            "IFMISSING(\$$parameterName1, \$$parameterName2)",
            mapOf(parameterName1 to parameterValue, parameterName2 to parameterValue2),
            emptyList(),
        )
        val underTest = IfMissingExpression(parameterValue.asParameter(parameterName1), parameterValue2.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support if missing function`() {
        val firstExpression = someStringField()
        val secondExpression = someStringField()
        val expected = IfMissingExpression(firstExpression, secondExpression)

        val actual = ifMissing(firstExpression, secondExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
