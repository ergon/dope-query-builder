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

class ArrayUnionExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_UNION`() {
        val expected = DopeQuery(
            queryString = "ARRAY_UNION(`numberArrayField`, `numberArrayField`)",
        )
        val underTest = ArrayUnionExpression(someNumberArrayField(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_UNION with positional parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            queryString = "ARRAY_UNION($1, `numberArrayField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayUnionExpression(parameterValue.asParameter(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_UNION with positional parameter as value`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            queryString = "ARRAY_UNION(`numberArrayField`, $1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayUnionExpression(someNumberArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_UNION with all positional parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = listOf(4, 5, 6)
        val expected = DopeQuery(
            queryString = "ARRAY_UNION($1, $2)",
            DopeParameters(positionalParameters = listOf(parameterValueCollection, parameterValue)),
        )
        val underTest = ArrayUnionExpression(parameterValueCollection.asParameter(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_UNION with named parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "ARRAY_UNION(\$$parameterName, `numberArrayField`)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayUnionExpression(parameterValue.asParameter(parameterName), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_UNION with named parameter as value`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = DopeQuery(
            queryString = "ARRAY_UNION(`numberArrayField`, \$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayUnionExpression(someNumberArrayField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_UNION with all named parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterName = "param1"
        val parameterValue = listOf(4, 5, 6)
        val parameterName2 = "param2"
        val expected = DopeQuery(
            queryString = "ARRAY_UNION(\$$parameterName, \$$parameterName2)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValueCollection, parameterName2 to parameterValue)),
        )
        val underTest = ArrayUnionExpression(parameterValueCollection.asParameter(parameterName), parameterValue.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_UNION with mixed named and positional parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterName = "param"
        val parameterValue = listOf(4, 5, 6)
        val expected = DopeQuery(
            queryString = "ARRAY_UNION(\$$parameterName, $1)",

            DopeParameters(namedParameters = mapOf(parameterName to parameterValueCollection), positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayUnionExpression(parameterValueCollection.asParameter(parameterName), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_UNION extension type type`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberArrayField()
        val expected = ArrayUnionExpression(firstArray, secondArray)

        val actual = arrayUnion(firstArray, secondArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_UNION extension select type`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberArrayField()
        val expected = ArrayUnionExpression(firstArray.asExpression(), secondArray)

        val actual = arrayUnion(firstArray, secondArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_UNION extension type select`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberSelectRawClause()
        val expected = ArrayUnionExpression(firstArray, secondArray.asExpression())

        val actual = arrayUnion(firstArray, secondArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_UNION extension select select`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberSelectRawClause()
        val expected = ArrayUnionExpression(firstArray.asExpression(), secondArray.asExpression())

        val actual = arrayUnion(firstArray, secondArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_UNION extension type type type`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberArrayField()
        val expected = ArrayUnionExpression(firstArray, secondArray, thirdArray)

        val actual = arrayUnion(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_UNION extension select type type`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberArrayField()
        val expected = ArrayUnionExpression(firstArray.asExpression(), secondArray, thirdArray)

        val actual = arrayUnion(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_UNION extension type select type`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberArrayField()
        val expected = ArrayUnionExpression(firstArray, secondArray.asExpression(), thirdArray)

        val actual = arrayUnion(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_UNION extension type type select`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArrayUnionExpression(firstArray, secondArray, thirdArray.asExpression())

        val actual = arrayUnion(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_UNION extension select select type`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberArrayField()
        val expected = ArrayUnionExpression(firstArray.asExpression(), secondArray.asExpression(), thirdArray)

        val actual = arrayUnion(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_UNION extension select type select`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArrayUnionExpression(firstArray.asExpression(), secondArray, thirdArray.asExpression())

        val actual = arrayUnion(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_UNION extension type select select`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArrayUnionExpression(firstArray, secondArray.asExpression(), thirdArray.asExpression())

        val actual = arrayUnion(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_UNION extension select select select`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArrayUnionExpression(
            firstArray.asExpression(),
            secondArray.asExpression(),
            thirdArray.asExpression(),
        )

        val actual = arrayUnion(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
