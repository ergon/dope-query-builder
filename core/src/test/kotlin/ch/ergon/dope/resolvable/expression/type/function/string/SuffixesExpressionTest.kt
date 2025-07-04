package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class SuffixesExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support split`() {
        val expected = DopeQuery(
            queryString = "SUFFIXES(`stringField`)",
        )
        val underTest = SuffixesExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support suffixes with positional parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            queryString = "SUFFIXES($1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = SuffixesExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support suffixes with named parameter`() {
        val parameterValue = "test"
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "SUFFIXES(\$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = SuffixesExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support suffix function type`() {
        val inStr = someStringField("inStr")
        val expected = SuffixesExpression(inStr)

        val actual = suffixes(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support suffix function string`() {
        val inStr = someString()
        val expected = SuffixesExpression(inStr.toDopeType())

        val actual = suffixes(inStr)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
