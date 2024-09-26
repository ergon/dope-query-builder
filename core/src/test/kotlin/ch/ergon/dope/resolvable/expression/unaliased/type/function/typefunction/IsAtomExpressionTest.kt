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

class IsAtomExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support is atom expression with no parameters`() {
        val expected = DopeQuery(
            "ISATOM(`stringField`)",
            emptyMap(),
            emptyList(),
        )
        val underTest = IsAtomExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is atom expression with positional parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "ISATOM($1)",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = IsAtomExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is atom expression with named parameter`() {
        val parameterValue = someString()
        val parameterName = "param"
        val expected = DopeQuery(
            "ISATOM(\$$parameterName)",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = IsAtomExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is atom extension`() {
        val atom = someString().toDopeType()
        val expected = IsAtomExpression(atom)

        val actual = atom.isAtom()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
