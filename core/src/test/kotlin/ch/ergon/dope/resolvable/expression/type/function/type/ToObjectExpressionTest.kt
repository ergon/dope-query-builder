package ch.ergon.dope.resolvable.expression.type.function.type

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ToObjectExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support to object expression`() {
        val expected = DopeQuery(
            "TOOBJECT(`stringField`)",
            DopeParameters(),
        )
        val underTest = ToObjectExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to object expression with parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "TOOBJECT($1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ToObjectExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to object extension`() {
        val number = someNumberField()
        val expected = ToObjectExpression(number)

        val actual = number.toObject()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support to object extension with number`() {
        val number = someNumber()
        val expected = ToObjectExpression(number.toDopeType())

        val actual = number.toObject()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support to object extension with string`() {
        val string = someString()
        val expected = ToObjectExpression(string.toDopeType())

        val actual = string.toObject()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support to object extension with boolean`() {
        val boolean = someBoolean()
        val expected = ToObjectExpression(boolean.toDopeType())

        val actual = boolean.toObject()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
