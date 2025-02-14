package ch.ergon.dope.resolvable.expression.single.type.function.arrayfunction

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberSelectRawClause
import ch.ergon.dope.resolvable.expression.single.type.asParameter
import ch.ergon.dope.resolvable.expression.single.type.function.array.ArrayExceptExpression
import ch.ergon.dope.resolvable.expression.single.type.function.array.arrayExcept
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayExceptExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_EXCEPT`() {
        val expected = DopeQuery(
            queryString = "ARRAY_EXCEPT(`numberArrayField`, `numberArrayField`)",
        )
        val underTest = ArrayExceptExpression(someNumberArrayField(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_EXCEPT with positional parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            queryString = "ARRAY_EXCEPT($1, `numberArrayField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayExceptExpression(parameterValue.asParameter(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_EXCEPT with named parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "ARRAY_EXCEPT(\$$parameterName, `numberArrayField`)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayExceptExpression(parameterValue.asParameter(parameterName), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_EXCEPT with positional parameter as value`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            queryString = "ARRAY_EXCEPT(`numberArrayField`, $1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayExceptExpression(someNumberArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_EXCEPT with named parameter as value`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "ARRAY_EXCEPT(`numberArrayField`, \$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayExceptExpression(someNumberArrayField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_EXCEPT with positional all parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = listOf(4, 5, 6)
        val expected = DopeQuery(
            queryString = "ARRAY_EXCEPT($1, $2)",
            DopeParameters(positionalParameters = listOf(parameterValueCollection, parameterValue)),
        )
        val underTest = ArrayExceptExpression(parameterValueCollection.asParameter(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_EXCEPT with named all parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = listOf(4, 5, 6)
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "ARRAY_EXCEPT(\$$parameterName, \$$parameterName2)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValueCollection, parameterName2 to parameterValue)),
        )
        val underTest = ArrayExceptExpression(
            parameterValueCollection.asParameter(parameterName),
            parameterValue.asParameter(parameterName2),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_EXCEPT extension type type`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberArrayField()
        val expected = ArrayExceptExpression(firstArray, secondArray)

        val actual = arrayExcept(firstArray, secondArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_EXCEPT extension select type`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberArrayField()
        val expected = ArrayExceptExpression(firstArray.asExpression(), secondArray)

        val actual = arrayExcept(firstArray, secondArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_EXCEPT extension type select`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberSelectRawClause()
        val expected = ArrayExceptExpression(firstArray, secondArray.asExpression())

        val actual = arrayExcept(firstArray, secondArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_EXCEPT extension select select`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberSelectRawClause()
        val expected = ArrayExceptExpression(firstArray.asExpression(), secondArray.asExpression())

        val actual = arrayExcept(firstArray, secondArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
