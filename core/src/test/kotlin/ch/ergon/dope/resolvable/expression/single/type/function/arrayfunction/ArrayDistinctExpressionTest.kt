package ch.ergon.dope.resolvable.expression.single.type.function.arrayfunction

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someSelectRawClause
import ch.ergon.dope.resolvable.expression.single.type.asParameter
import ch.ergon.dope.resolvable.expression.single.type.function.array.ArrayDistinctExpression
import ch.ergon.dope.resolvable.expression.single.type.function.array.arrayDistinct
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayDistinctExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_DISTINCT`() {
        val expected = DopeQuery(
            queryString = "ARRAY_DISTINCT(`numberArrayField`)",
        )
        val underTest = ArrayDistinctExpression(someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_DISTINCT with positional parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            queryString = "ARRAY_DISTINCT($1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayDistinctExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_DISTINCT with named parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "ARRAY_DISTINCT(\$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayDistinctExpression(parameterValue.asParameter(parameterName))

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

    @Test
    fun `should support ARRAY_DISTINCT extension select`() {
        val selectClause = someSelectRawClause()
        val expected = ArrayDistinctExpression(selectClause.asExpression())

        val actual = arrayDistinct(selectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
