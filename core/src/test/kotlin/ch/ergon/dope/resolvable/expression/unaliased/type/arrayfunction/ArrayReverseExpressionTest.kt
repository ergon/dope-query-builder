package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ArrayReverseExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_REVERSE`() {
        val expected = DopeQuery(
            "ARRAY_REVERSE(`numberArrayField`)",
            emptyMap(),
            manager,
        )
        val underTest = ArrayReverseExpression(someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REVERSE with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_REVERSE($1)",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = ArrayReverseExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_REVERSE extension`() {
        val array = someNumberArrayField()
        val expected = ArrayReverseExpression(array)

        val actual = arrayReverse(array)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
