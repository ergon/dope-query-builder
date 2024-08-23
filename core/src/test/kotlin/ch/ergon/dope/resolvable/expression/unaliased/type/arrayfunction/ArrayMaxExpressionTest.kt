package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ArrayMaxExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_MAX`() {
        val expected = DopeQuery(
            "ARRAY_MAX(`numberArrayField`)",
            emptyMap(),
            manager,
        )
        val underTest = ArrayMaxExpression(someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MAX with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_MAX($1)",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = ArrayMaxExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MAX extension`() {
        val array = someNumberArrayField()
        val expected = ArrayMaxExpression(array)

        val actual = arrayMax(array)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
