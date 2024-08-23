package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ArrayDistinctExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_DISTINCT`() {
        val expected = DopeQuery(
            "ARRAY_DISTINCT(`numberArrayField`)",
            emptyMap(),
            manager,
        )
        val underTest = ArrayDistinctExpression(someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_DISTINCT with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_DISTINCT($1)",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = ArrayDistinctExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_DISTINCT extension`() {
        val array = someNumberArrayField()
        val expected = ArrayDistinctExpression(array)

        val actual = arrayDistinct(array)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
