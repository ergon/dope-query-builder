package ch.ergon.dope.resolvable.expression.unaliased.type.typefunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ToNumberExpressionTest : ParameterDependentTest {
    @Test
    fun `should support to number expression`() {
        val expected = DopeQuery(
            "TONUMBER(`stringField`)",
            emptyMap(),
        )
        val underTest = ToNumberExpression(someStringField())

        val actual = underTest.toDopeQuery()

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

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support to number extension`() {
        val string = someStringField()
        val expected = ToNumberExpression(string)

        val actual = string.toNumber()

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support to number extension with string`() {
        val string = someString()
        val expected = ToNumberExpression(string.toDopeType())

        val actual = string.toNumber()

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support to number extension with boolean`() {
        val boolean = someBoolean()
        val expected = ToNumberExpression(boolean.toDopeType())

        val actual = boolean.toNumber()

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support to number extension with options`() {
        val string = someStringField()
        val options = someStringField()
        val expected = ToNumberExpression(string, options)

        val actual = string.toNumber(options)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support to number extension with options as string`() {
        val string = someStringField()
        val options = someString()
        val expected = ToNumberExpression(string, options.toDopeType())

        val actual = string.toNumber(options)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support to number extension with string and options`() {
        val string = someString()
        val options = someStringField()
        val expected = ToNumberExpression(string.toDopeType(), options)

        val actual = string.toNumber(options)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support to number extension with string and options as string`() {
        val string = someString()
        val options = someString()
        val expected = ToNumberExpression(string.toDopeType(), options.toDopeType())

        val actual = string.toNumber(options)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
