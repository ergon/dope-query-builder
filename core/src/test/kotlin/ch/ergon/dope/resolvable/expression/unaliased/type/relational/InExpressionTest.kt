package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someBoolean
import ch.ergon.dope.helper.someBooleanArrayField
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.CMNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class InExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support in`() {
        val expected = DopeQuery(
            "`stringField` IN `stringArrayField`",
            emptyMap(),
        )
        val underTest = InExpression(someStringField(), someStringArrayField())

        val actual = underTest.toDopeQuery()

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

        val actual = underTest.toDopeQuery()

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

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in with second parameter`() {
        val parameterValue = listOf(5)
        val expected = DopeQuery(
            "`numberField` IN $1",
            mapOf("$1" to parameterValue),
        )
        val underTest = InExpression(CMNumberField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support in function type type`() {
        val left = CMNumberField()
        val right = someNumberArrayField()
        val expected = InExpression(left, right)

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support in function type with collection of type`() {
        val left = CMNumberField()
        val right = listOf(CMNumberField())
        val expected = InExpression(left, right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support in function number type`() {
        val left = someNumber()
        val right = someNumberArrayField()
        val expected = InExpression(left.toDopeType(), right)

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support in function string type`() {
        val left = someString()
        val right = someStringArrayField()
        val expected = InExpression(left.toDopeType(), right)

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support in function boolean type`() {
        val left = someBoolean()
        val right = someBooleanArrayField()
        val expected = InExpression(left.toDopeType(), right)

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support in function number collection of type`() {
        val left = someNumber()
        val right = listOf(CMNumberField())
        val expected = InExpression(left.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support in function string collection of type`() {
        val left = someString()
        val right = listOf(someStringField())
        val expected = InExpression(left.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support in function boolean collection of type`() {
        val left = someBoolean()
        val right = listOf(someBooleanField())
        val expected = InExpression(left.toDopeType(), right.toDopeType())

        val actual = left.inArray(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
