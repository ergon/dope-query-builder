package ch.ergon.dope.resolvable.expression.single.type.function.arrayfunction

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someNumberSelectRawClause
import ch.ergon.dope.resolvable.expression.single.type.asParameter
import ch.ergon.dope.resolvable.expression.single.type.function.array.ArrayPositionExpression
import ch.ergon.dope.resolvable.expression.single.type.function.array.arrayPosition
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayPositionExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_POSITION`() {
        val expected = DopeQuery(
            queryString = "ARRAY_POSITION(`numberArrayField`, `numberField`)",
        )
        val underTest = ArrayPositionExpression(someNumberArrayField(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_POSITION with named parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "ARRAY_POSITION(\$$parameterName, `numberField`)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayPositionExpression(parameterValue.asParameter(parameterName), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_POSITION with positional parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            queryString = "ARRAY_POSITION($1, `numberField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayPositionExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_POSITION with named parameter as value`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "ARRAY_POSITION(`numberArrayField`, \$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayPositionExpression(someNumberArrayField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_POSITION with positional parameter as value`() {
        val parameterValue = 1
        val expected = DopeQuery(
            queryString = "ARRAY_POSITION(`numberArrayField`, $1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayPositionExpression(someNumberArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_POSITION with all named parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = 1
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "ARRAY_POSITION(\$$parameterName, \$$parameterName2)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValueCollection, parameterName2 to parameterValue)),
        )
        val underTest = ArrayPositionExpression(parameterValueCollection.asParameter(parameterName), parameterValue.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_POSITION with all positional parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = 1
        val expected = DopeQuery(
            queryString = "ARRAY_POSITION($1, $2)",
            DopeParameters(positionalParameters = listOf(parameterValueCollection, parameterValue)),
        )
        val underTest = ArrayPositionExpression(parameterValueCollection.asParameter(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_POSITION extension type`() {
        val array = someNumberArrayField()
        val value = someNumberField()
        val expected = ArrayPositionExpression(array, value)

        val actual = arrayPosition(array, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_POSITION extension select`() {
        val selectClause = someNumberSelectRawClause()
        val value = someNumberField()
        val expected = ArrayPositionExpression(selectClause.asExpression(), value)

        val actual = arrayPosition(selectClause, value)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
