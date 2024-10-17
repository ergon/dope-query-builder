package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberSelectRawClause
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class ArraySymmetricDifferenceTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support ARRAY_SYMDIFF`() {
        val expected = DopeQuery(
            "ARRAY_SYMDIFF(`numberArrayField`, `numberArrayField`)",
            emptyMap(),
        )
        val underTest = ArraySymmetricDifferenceExpression(someNumberArrayField(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SYMDIFF with parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_SYMDIFF($1, `numberArrayField`)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArraySymmetricDifferenceExpression(parameterValue.asParameter(), someNumberArrayField())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SYMDIFF with parameter as secondArray`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = DopeQuery(
            "ARRAY_SYMDIFF(`numberArrayField`, $1)",
            mapOf("$1" to parameterValue),
        )
        val underTest = ArraySymmetricDifferenceExpression(someNumberArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SYMDIFF with all parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = listOf(4, 5, 6)
        val expected = DopeQuery(
            "ARRAY_SYMDIFF($1, $2)",
            mapOf("$1" to parameterValueCollection, "$2" to parameterValue),
        )
        val underTest = ArraySymmetricDifferenceExpression(parameterValueCollection.asParameter(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SYMDIFF extension type type`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberArrayField()
        val expected = ArraySymmetricDifferenceExpression(firstArray, secondArray)

        val actual = arraySymDiff(firstArray, secondArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFF extension select type`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberArrayField()
        val expected = ArraySymmetricDifferenceExpression(firstArray.asExpression(), secondArray)

        val actual = arraySymDiff(firstArray, secondArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFF extension type select`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifferenceExpression(firstArray, secondArray.asExpression())

        val actual = arraySymDiff(firstArray, secondArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFF extension select select`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifferenceExpression(firstArray.asExpression(), secondArray.asExpression())

        val actual = arraySymDiff(firstArray, secondArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFF extension type type type`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberArrayField()
        val expected = ArraySymmetricDifferenceExpression(firstArray, secondArray, thirdArray)

        val actual = arraySymDiff(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFF extension select type type`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberArrayField()
        val expected = ArraySymmetricDifferenceExpression(firstArray.asExpression(), secondArray, thirdArray)

        val actual = arraySymDiff(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFF extension type select type`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberArrayField()
        val expected = ArraySymmetricDifferenceExpression(firstArray, secondArray.asExpression(), thirdArray)

        val actual = arraySymDiff(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFF extension type type select`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifferenceExpression(firstArray, secondArray, thirdArray.asExpression())

        val actual = arraySymDiff(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFF extension select select type`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberArrayField()
        val expected = ArraySymmetricDifferenceExpression(firstArray.asExpression(), secondArray.asExpression(), thirdArray)

        val actual = arraySymDiff(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFF extension select type select`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifferenceExpression(firstArray.asExpression(), secondArray, thirdArray.asExpression())

        val actual = arraySymDiff(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFF extension type select select`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifferenceExpression(firstArray, secondArray.asExpression(), thirdArray.asExpression())

        val actual = arraySymDiff(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFF extension select select select`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifferenceExpression(
            firstArray.asExpression(),
            secondArray.asExpression(),
            thirdArray.asExpression(),
        )

        val actual = arraySymDiff(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFF1 extension type type`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberArrayField()
        val expected = ArraySymmetricDifference1Expression(firstArray, secondArray)

        val actual = arraySymDiff1(firstArray, secondArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFF1 extension select type`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberArrayField()
        val expected = ArraySymmetricDifference1Expression(firstArray.asExpression(), secondArray)

        val actual = arraySymDiff1(firstArray, secondArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFF1 extension type select`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifference1Expression(firstArray, secondArray.asExpression())

        val actual = arraySymDiff1(firstArray, secondArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFF1 extension select select`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifference1Expression(firstArray.asExpression(), secondArray.asExpression())

        val actual = arraySymDiff1(firstArray, secondArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFF1 extension type type type`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberArrayField()
        val expected = ArraySymmetricDifference1Expression(firstArray, secondArray, thirdArray)

        val actual = arraySymDiff1(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFF1 extension select type type`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberArrayField()
        val expected = ArraySymmetricDifference1Expression(firstArray.asExpression(), secondArray, thirdArray)

        val actual = arraySymDiff1(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFF1 extension type select type`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberArrayField()
        val expected = ArraySymmetricDifference1Expression(firstArray, secondArray.asExpression(), thirdArray)

        val actual = arraySymDiff1(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFF1 extension type type select`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifference1Expression(firstArray, secondArray, thirdArray.asExpression())

        val actual = arraySymDiff1(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFF1 extension select select type`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberArrayField()
        val expected = ArraySymmetricDifference1Expression(firstArray.asExpression(), secondArray.asExpression(), thirdArray)

        val actual = arraySymDiff1(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFF1 extension select type select`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifference1Expression(firstArray.asExpression(), secondArray, thirdArray.asExpression())

        val actual = arraySymDiff1(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFF1 extension type select select`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifference1Expression(firstArray, secondArray.asExpression(), thirdArray.asExpression())

        val actual = arraySymDiff1(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFF1 extension select select select`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifference1Expression(
            firstArray.asExpression(),
            secondArray.asExpression(),
            thirdArray.asExpression(),
        )

        val actual = arraySymDiff1(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFFN extension type type`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberArrayField()
        val expected = ArraySymmetricDifferenceNExpression(firstArray, secondArray)

        val actual = arraySymDiffN(firstArray, secondArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFFN extension select type`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberArrayField()
        val expected = ArraySymmetricDifferenceNExpression(firstArray.asExpression(), secondArray)

        val actual = arraySymDiffN(firstArray, secondArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFFN extension type select`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifferenceNExpression(firstArray, secondArray.asExpression())

        val actual = arraySymDiffN(firstArray, secondArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFFN extension select select`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifferenceNExpression(firstArray.asExpression(), secondArray.asExpression())

        val actual = arraySymDiffN(firstArray, secondArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFFN extension type type type`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberArrayField()
        val expected = ArraySymmetricDifferenceNExpression(firstArray, secondArray, thirdArray)

        val actual = arraySymDiffN(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFFN extension select type type`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberArrayField()
        val expected = ArraySymmetricDifferenceNExpression(firstArray.asExpression(), secondArray, thirdArray)

        val actual = arraySymDiffN(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFFN extension type select type`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberArrayField()
        val expected = ArraySymmetricDifferenceNExpression(firstArray, secondArray.asExpression(), thirdArray)

        val actual = arraySymDiffN(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFFN extension type type select`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifferenceNExpression(firstArray, secondArray, thirdArray.asExpression())

        val actual = arraySymDiffN(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFFN extension select select type`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberArrayField()
        val expected = ArraySymmetricDifferenceNExpression(firstArray.asExpression(), secondArray.asExpression(), thirdArray)

        val actual = arraySymDiffN(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFFN extension select type select`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifferenceNExpression(firstArray.asExpression(), secondArray, thirdArray.asExpression())

        val actual = arraySymDiffN(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFFN extension type select select`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifferenceNExpression(firstArray, secondArray.asExpression(), thirdArray.asExpression())

        val actual = arraySymDiffN(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support ARRAY_SYMDIFFN extension select select select`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifferenceNExpression(
            firstArray.asExpression(),
            secondArray.asExpression(),
            thirdArray.asExpression(),
        )

        val actual = arraySymDiffN(firstArray, secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
