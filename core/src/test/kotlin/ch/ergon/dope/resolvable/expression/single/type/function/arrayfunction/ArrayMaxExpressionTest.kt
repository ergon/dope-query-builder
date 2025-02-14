package ch.ergon.dope.resolvable.expression.single.type.function.arrayfunction

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someSelectRawClause
import ch.ergon.dope.resolvable.expression.single.type.asParameter
import ch.ergon.dope.resolvable.expression.single.type.function.array.ArrayMaxExpression
import ch.ergon.dope.resolvable.expression.single.type.function.array.arrayMax
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayMaxExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_MAX`() {
        val expected = DopeQuery(
            queryString = "ARRAY_MAX(`numberArrayField`)",
        )
        val underTest = ArrayMaxExpression(someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MAX with positional parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            queryString = "ARRAY_MAX($1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayMaxExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MAX with named parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "ARRAY_MAX(\$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayMaxExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MAX extension type`() {
        val array = someNumberArrayField()
        val expected = ArrayMaxExpression(array)

        val actual = arrayMax(array)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_MAX extension select`() {
        val selectClause = someSelectRawClause()
        val expected = ArrayMaxExpression(selectClause.asExpression())

        val actual = arrayMax(selectClause)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
