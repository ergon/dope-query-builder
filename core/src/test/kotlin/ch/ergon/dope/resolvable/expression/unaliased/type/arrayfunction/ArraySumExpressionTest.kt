package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.ArraySumExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arraySum
import kotlin.test.Test
import kotlin.test.assertEquals

class ArraySumExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_SUM`() {
        val expected = DopeQuery(
            "ARRAY_SUM(`numberArrayField`)",
            emptyMap(),
        )
        val underTest = ArraySumExpression(someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SUM with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_SUM($1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArraySumExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SUM extension`() {
        val array = someNumberArrayField()
        val expected = ArraySumExpression(array)

        val actual = arraySum(array)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
