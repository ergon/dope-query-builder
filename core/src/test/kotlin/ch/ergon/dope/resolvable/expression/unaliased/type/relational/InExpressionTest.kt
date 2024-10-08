package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanArrayField
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.InExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.inArray
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class InExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support in`() {
        val expected = DopeQuery(
            "`stringField` IN `stringArrayField`",
            emptyMap(),
        )
        val underTest = InExpression(someStringField(), someStringArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in with parameter`() {
        val parameterValue = "test"
        val expected = DopeQuery(
            "$1 IN `stringArrayField`",
            mapOf("$1" to parameterValue),
        )
        val underTest = InExpression(parameterValue.asParameter(), someStringArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in with all parameters`() {
        val parameterValue = "test"
        val parameterValue2 = listOf("test")
        val expected = DopeQuery(
            "$1 IN $2",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = InExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in with second parameter`() {
        val parameterValue = listOf(5)
        val expected = DopeQuery(
            "`numberField` IN $1",
            mapOf("$1" to parameterValue),
        )
        val underTest = InExpression(someNumberField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in function type type`() {
        val left = someNumberField()
        val right = someNumberArrayField()
        val expected = InExpression(left, right)

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support in function type with collection of type`() {
        val left = someNumberField()
        val right = listOf(someNumberField())
        val expected = InExpression(left, right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support in function number type`() {
        val left = someNumber()
        val right = someNumberArrayField()
        val expected = InExpression(left.toDopeType(), right)

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support in function string type`() {
        val left = someString()
        val right = someStringArrayField()
        val expected = InExpression(left.toDopeType(), right)

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support in function boolean type`() {
        val left = someBoolean()
        val right = someBooleanArrayField()
        val expected = InExpression(left.toDopeType(), right)

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support in function number collection of type`() {
        val left = someNumber()
        val right = listOf(someNumberField())
        val expected = InExpression(left.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support in function string collection of type`() {
        val left = someString()
        val right = listOf(someStringField())
        val expected = InExpression(left.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support in function boolean collection of type`() {
        val left = someBoolean()
        val right = listOf(someBooleanField())
        val expected = InExpression(left.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
