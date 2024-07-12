package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class NotEqualsExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support not equals`() {
        val expected = DopeQuery(
            "`numberField` != `numberField`",
            emptyMap(),
        )
        val underTest = NotEqualsExpression(someNumberField(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not equals with parameter`() {
        val parameterValue = 5
        val expected = DopeQuery(
            "$1 != `numberField`",
            mapOf("$1" to parameterValue),
        )
        val underTest = NotEqualsExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not equals with all parameters`() {
        val parameterValue = 5
        val parameterValue2 = 6
        val expected = DopeQuery(
            "$1 != $2",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = NotEqualsExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not equals with second parameter`() {
        val parameterValue = someNumber()
        val expected = DopeQuery(
            "`numberField` != $1",
            mapOf("$1" to parameterValue),
        )
        val underTest = NotEqualsExpression(someNumberField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not equals function type type`() {
        val left = someNumberField()
        val right = someNumberField()
        val expected = NotEqualsExpression(left, right)

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals function type number`() {
        val left = someNumberField()
        val right = someNumber()
        val expected = NotEqualsExpression(left, right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals function number type`() {
        val left = someNumber()
        val right = someNumberField()
        val expected = NotEqualsExpression(left.toDopeType(), right)

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals function number number`() {
        val left = someNumber()
        val right = someNumber()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals function type string`() {
        val left = someStringField()
        val right = someString()
        val expected = NotEqualsExpression(left, right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals function string type`() {
        val left = someString()
        val right = someStringField()
        val expected = NotEqualsExpression(left.toDopeType(), right)

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals function string string`() {
        val left = someString()
        val right = someString()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals function type boolean`() {
        val left = someBooleanField()
        val right = someBoolean()
        val expected = NotEqualsExpression(left, right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals function boolean type`() {
        val left = someBoolean()
        val right = someBooleanField()
        val expected = NotEqualsExpression(left.toDopeType(), right)

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support not equals function boolean boolean`() {
        val left = someBoolean()
        val right = someBoolean()
        val expected = NotEqualsExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotEqualTo(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
