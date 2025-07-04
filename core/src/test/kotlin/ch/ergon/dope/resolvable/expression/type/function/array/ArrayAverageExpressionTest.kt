package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberSelectRawClause
import ch.ergon.dope.resolvable.expression.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayAverageExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_AVG`() {
        val expected = DopeQuery(
            queryString = "ARRAY_AVG(`numberArrayField`)",
        )
        val underTest = ArrayAverageExpression(someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_AVG with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            queryString = "ARRAY_AVG($1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayAverageExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_AVG extension`() {
        val array = someNumberArrayField()
        val expected = ArrayAverageExpression(array)

        val actual = arrayAverage(array)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_AVG extension select`() {
        val selectClause = someNumberSelectRawClause()
        val expected = ArrayAverageExpression(selectClause.asExpression())

        val actual = arrayAverage(selectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
