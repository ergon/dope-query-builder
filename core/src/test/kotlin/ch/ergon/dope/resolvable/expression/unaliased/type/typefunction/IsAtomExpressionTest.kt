package ch.ergon.dope.resolvable.expression.unaliased.type.typefunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.IsAtomExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.isAtom
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class IsAtomExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support is atom expression`() {
        val expected = DopeQuery(
            "ISATOM(`stringField`)",
            emptyMap(),
        )
        val underTest = IsAtomExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is atom expression with parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "ISATOM($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = IsAtomExpression(parameterValue.asParameter())

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
