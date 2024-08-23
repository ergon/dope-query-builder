package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.ParameterManager
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.ArrayMinExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayMin
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArrayMinExpressionTest {
    @BeforeEach
    fun reset() {
        ParameterManager.resetCounter()
    }

    @Test
    fun `should support ARRAY_MIN`() {
        val expected = DopeQuery(
            "ARRAY_MIN(`numberArrayField`)",
            emptyMap(),
        )
        val underTest = ArrayMinExpression(someNumberArrayField())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MIN with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_MIN($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayMinExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery()

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MIN extension`() {
        val array = someNumberArrayField()
        val expected = ArrayMinExpression(array)

        val actual = arrayMin(array)

        assertEquals(expected.toDopeQuery(), actual.toDopeQuery())
    }
}
