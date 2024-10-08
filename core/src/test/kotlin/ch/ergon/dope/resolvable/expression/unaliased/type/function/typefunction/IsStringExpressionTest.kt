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

class IsStringExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support is string expression`() {
        val expected = DopeQuery(
            "ISSTRING(`stringField`)",
            emptyMap(),
        )
        val underTest = IsStringExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is string expression with parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "ISSTRING($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = IsStringExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is string extension`() {
        val string = someString().toDopeType()
        val expected = IsStringExpression(string)

        val actual = string.isString()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
