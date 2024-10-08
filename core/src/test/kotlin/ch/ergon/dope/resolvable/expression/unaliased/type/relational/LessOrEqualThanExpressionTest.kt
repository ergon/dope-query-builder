package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class LessOrEqualThanExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support less or equals`() {
        val expected = DopeQuery(
            "`numberField` <= `numberField`",
            emptyMap(),
        )
        val underTest = LessOrEqualThanExpression(someNumberField(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support less or equals with parameter`() {
        val parameterValue = 5
        val expected = DopeQuery(
            "$1 <= `numberField`",
            mapOf("$1" to parameterValue),
        )
        val underTest = LessOrEqualThanExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support less or equals with all parameters`() {
        val parameterValue = 5
        val parameterValue2 = 6
        val expected = DopeQuery(
            "$1 <= $2",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = LessOrEqualThanExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support less or equal with second parameter`() {
        val parameterValue = someNumber()
        val expected = DopeQuery(
            "`numberField` <= $1",
            mapOf("$1" to parameterValue),
        )
        val underTest = LessOrEqualThanExpression(someNumberField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support less or equal function type type`() {
        val left = someNumberField()
        val right = someNumberField()
        val expected = LessOrEqualThanExpression(left, right)

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support less or equal function type number`() {
        val left = someNumberField()
        val right = someNumber()
        val expected = LessOrEqualThanExpression(left, right.toDopeType())

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support less or equal function number type`() {
        val left = someNumber()
        val right = someNumberField()
        val expected = LessOrEqualThanExpression(left.toDopeType(), right)

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support less or equal function number number`() {
        val left = someNumber()
        val right = someNumber()
        val expected = LessOrEqualThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support less or equal function type string`() {
        val left = someStringField()
        val right = someString()
        val expected = LessOrEqualThanExpression(left, right.toDopeType())

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support less or equal function string type`() {
        val left = someString()
        val right = someStringField()
        val expected = LessOrEqualThanExpression(left.toDopeType(), right)

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support less or equal function string string`() {
        val left = someString()
        val right = someString()
        val expected = LessOrEqualThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isLessOrEqualThan(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
