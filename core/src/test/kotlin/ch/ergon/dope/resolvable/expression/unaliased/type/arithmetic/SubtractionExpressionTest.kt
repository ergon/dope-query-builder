package ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class SubtractionExpressionTest {

    @BeforeEach
    fun setUp() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support subtraction`() {
        val expected = DopeQuery(
            "(`numberField` - `numberField`)",
            emptyMap(),
        )
        val underTest = SubtractionExpression(someNumberField(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support subtraction with parameter`() {
        val parameterValue = 4
        val expected = DopeQuery(
            "($1 - `numberField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = SubtractionExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support subtraction with all parameters`() {
        val parameterValue = 4
        val parameterValue2 = 5
        val expected = DopeQuery(
            "($1 - $2)",
            mapOf("$1" to parameterValue, "$2" to parameterValue2),
        )
        val underTest = SubtractionExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support subtraction with second parameter`() {
        val parameterValue = 4
        val expected = DopeQuery(
            "(`numberField` - $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = SubtractionExpression(someNumberField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support subtraction function type type`() {
        val left = someNumberField()
        val right = someNumberField()
        val expected = SubtractionExpression(left, right)

        val actual = left.sub(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support subtraction function type number`() {
        val left = someNumberField()
        val right = someNumber()
        val expected = SubtractionExpression(left, right.toDopeType())

        val actual = left.sub(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support subtraction function number type`() {
        val left = someNumber()
        val right = someNumberField()
        val expected = SubtractionExpression(left.toDopeType(), right)

        val actual = left.sub(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }

    @Test
    fun `should support subtraction function number number`() {
        val left = someNumber()
        val right = someNumber()
        val expected = SubtractionExpression(left.toDopeType(), right.toDopeType())

        val actual = left.sub(right)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
