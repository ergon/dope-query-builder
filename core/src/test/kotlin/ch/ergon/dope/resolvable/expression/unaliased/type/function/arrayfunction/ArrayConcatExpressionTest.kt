package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberSelectRawClause
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayConcatExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_CONCAT`() {
        val expected = DopeQuery(
            "ARRAY_CONCAT(`numberArrayField`, `numberArrayField`)",
            emptyMap(),
        )
        val underTest = ArrayConcatExpression(someNumberArrayField(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONCAT with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_CONCAT($1, `numberArrayField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayConcatExpression(parameterValue.asParameter(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONCAT with parameter as value`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_CONCAT(`numberArrayField`, $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArrayConcatExpression(someNumberArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONCAT with all parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = listOf(4, 5, 6)
        val expected = DopeQuery(
            "ARRAY_CONCAT($1, $2)",
            mapOf("$1" to parameterValueCollection, "$2" to parameterValue),
        )
        val underTest = ArrayConcatExpression(parameterValueCollection.asParameter(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_CONCAT extension type type`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberArrayField()
        val expected = ArrayConcatExpression(firstArray, secondArray)

        val actual = arrayConcat(firstArray, secondArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_CONCAT extension select type`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberArrayField()
        val expected = ArrayConcatExpression(firstArray.asExpression(), secondArray)

        val actual = arrayConcat(firstArray, secondArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_CONCAT extension type select`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberSelectRawClause()
        val expected = ArrayConcatExpression(firstArray, secondArray.asExpression())

        val actual = arrayConcat(firstArray, secondArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_CONCAT extension select select`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberSelectRawClause()
        val expected = ArrayConcatExpression(firstArray.asExpression(), secondArray.asExpression())

        val actual = arrayConcat(firstArray, secondArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_CONCAT extension type type type`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberArrayField()
        val expected = ArrayConcatExpression(firstArray, secondArray, thirdArray)

        val actual = arrayConcat(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_CONCAT extension select type type`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberArrayField()
        val expected = ArrayConcatExpression(firstArray.asExpression(), secondArray, thirdArray)

        val actual = arrayConcat(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_CONCAT extension type select type`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberArrayField()
        val expected = ArrayConcatExpression(firstArray, secondArray.asExpression(), thirdArray)

        val actual = arrayConcat(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_CONCAT extension type type select`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArrayConcatExpression(firstArray, secondArray, thirdArray.asExpression())

        val actual = arrayConcat(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_CONCAT extension select select type`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberArrayField()
        val expected = ArrayConcatExpression(firstArray.asExpression(), secondArray.asExpression(), thirdArray)

        val actual = arrayConcat(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_CONCAT extension select type select`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArrayConcatExpression(firstArray.asExpression(), secondArray, thirdArray.asExpression())

        val actual = arrayConcat(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_CONCAT extension type select select`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArrayConcatExpression(firstArray, secondArray.asExpression(), thirdArray.asExpression())

        val actual = arrayConcat(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_CONCAT extension select select select`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArrayConcatExpression(
            firstArray.asExpression(),
            secondArray.asExpression(),
            thirdArray.asExpression(),
        )

        val actual = arrayConcat(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
