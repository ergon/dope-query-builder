package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NotEqualsExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support not equals`() {
        val expected = DopeQuery(
            "`numberField` != `numberField`",
            emptyMap(),
            emptyList(),
        )
        val underTest = NotEqualsExpression(someNumberField(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not equals with positional parameter`() {
        val parameterValue = 5
        val expected = DopeQuery(
            "$1 != `numberField`",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = NotEqualsExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not equals with all positional parameters`() {
        val parameterValue = 5
        val parameterValue2 = 6
        val expected = DopeQuery(
            "$1 != $2",
            emptyMap(),
            listOf(parameterValue, parameterValue2),
        )
        val underTest = NotEqualsExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not equals with second positional parameter`() {
        val parameterValue = someNumber()
        val expected = DopeQuery(
            "`numberField` != $1",
            emptyMap(),
            listOf(parameterValue),
        )
        val underTest = NotEqualsExpression(someNumberField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not equals with named parameter`() {
        val parameterValue = 5
        val parameterName = "param"
        val expected = DopeQuery(
            "$$parameterName != `numberField`",
            mapOf(parameterName to parameterValue),
            emptyList(),
        )
        val underTest = NotEqualsExpression(parameterValue.asParameter(parameterName), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not equals with all named parameters`() {
        val parameterValue = 5
        val parameterValue2 = 6
        val parameterName1 = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            "$$parameterName1 != $$parameterName2",
            mapOf(parameterName1 to parameterValue, parameterName2 to parameterValue2),
            emptyList(),
        )
        val underTest = NotEqualsExpression(parameterValue.asParameter(parameterName1), parameterValue2.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not equals function type type`() {
        val left = someNumberField()
        val right = someNumberField()
        val expected = NotEqualsExpression(left, right)

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals function type number`() {
        val left = someNumberField()
        val right = someNumber()
        val expected = NotEqualsExpression(left, right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals function number type`() {
        val left = someNumber()
        val right = someNumberField()
        val expected = NotEqualsExpression(left.toDopeType(), right)

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals function number number`() {
        val left = someNumber()
        val right = someNumber()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals function type string`() {
        val left = someStringField()
        val right = someString()
        val expected = NotEqualsExpression(left, right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals function string type`() {
        val left = someString()
        val right = someStringField()
        val expected = NotEqualsExpression(left.toDopeType(), right)

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals function string string`() {
        val left = someString()
        val right = someString()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals function type boolean`() {
        val left = someBooleanField()
        val right = someBoolean()
        val expected = NotEqualsExpression(left, right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals function boolean type`() {
        val left = someBoolean()
        val right = someBooleanField()
        val expected = NotEqualsExpression(left.toDopeType(), right)

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support not equals function boolean boolean`() {
        val left = someBoolean()
        val right = someBoolean()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
