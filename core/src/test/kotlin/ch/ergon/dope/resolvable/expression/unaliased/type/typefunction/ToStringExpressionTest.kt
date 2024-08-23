package ch.ergon.dope.resolvable.expression.unaliased.type.typefunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ToStringExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support to string expression`() {
        val expected = DopeQuery(
            "TOSTRING(`stringField`)",
            emptyMap(),
            manager,
        )
        val underTest = ToStringExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to string expression with parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "TOSTRING($1)",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = ToStringExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to string extension`() {
        val number = someNumberField()
        val expected = ToStringExpression(number)

        val actual = number.toStr()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support to string extension with number`() {
        val number = someNumber()
        val expected = ToStringExpression(number.toDopeType())

        val actual = number.toStr()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support to string extension with boolean`() {
        val boolean = someBoolean()
        val expected = ToStringExpression(boolean.toDopeType())

        val actual = boolean.toStr()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
