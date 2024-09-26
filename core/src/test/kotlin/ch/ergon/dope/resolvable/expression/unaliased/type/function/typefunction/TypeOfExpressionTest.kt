package ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class TypeOfExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support type of expression with no parameters`() {
        val expected = DopeQuery(
            "TYPE(`stringField`)",
            emptyMap(),
            emptyList(),
        )
        val underTest = TypeOfExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support type of expression with positional parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "TYPE($1)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = TypeOfExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support type of expression with named parameter`() {
        val parameterValue = someString()
        val parameterName = "param"
        val expected = DopeQuery(
            "TYPE(\$$parameterName)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = TypeOfExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support type of extension`() {
        val string = someString().toDopeType()
        val expected = TypeOfExpression(string)

        val actual = typeOf(string)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
