package ch.ergon.dope.resolvable.expression.unaliased.type.typefunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.ToNumberExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.toNumber
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ToNumberExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support to number expression`() {
        val expected = DopeQuery(
            "TONUMBER(`stringField`)",
            emptyMap(),
        )
        val underTest = ToNumberExpression(someStringField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to number expression with parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "TONUMBER($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ToNumberExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to number extension`() {
        val string = someStringField()
        val expected = ToNumberExpression(string)

        val actual = string.toNumber()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support to number extension with string`() {
        val string = someString()
        val expected = ToNumberExpression(string.toDopeType())

        val actual = string.toNumber()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support to number extension with boolean`() {
        val boolean = someBoolean()
        val expected = ToNumberExpression(boolean.toDopeType())

        val actual = boolean.toNumber()

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support to number extension with filterChars`() {
        val string = someStringField()
        val filterChars = someStringField()
        val expected = ToNumberExpression(string, filterChars)

        val actual = string.toNumber(filterChars)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support to number extension with filterChars as string`() {
        val string = someStringField()
        val filterChars = someString()
        val expected = ToNumberExpression(string, filterChars.toDopeType())

        val actual = string.toNumber(filterChars)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support to number extension with string and filterChars`() {
        val string = someString()
        val filterChars = someStringField()
        val expected = ToNumberExpression(string.toDopeType(), filterChars)

        val actual = string.toNumber(filterChars)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support to number extension with string and filterChars as string`() {
        val string = someString()
        val filterChars = someString()
        val expected = ToNumberExpression(string.toDopeType(), filterChars.toDopeType())

        val actual = string.toNumber(filterChars)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
