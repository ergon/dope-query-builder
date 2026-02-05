package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberSelectRawClause
import ch.ergon.dope.resolvable.expression.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class ArraySymmetricDifferenceTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support ARRAY_SYMDIFF`() {
        val expected = CouchbaseDopeQuery(
            queryString = "ARRAY_SYMDIFF(`numberArrayField`, `numberArrayField`)",
        )
        val underTest = ArraySymmetricDifferenceExpression(someNumberArrayField(), someNumberArrayField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SYMDIFF with positional parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = CouchbaseDopeQuery(
            queryString = "ARRAY_SYMDIFF($1, `numberArrayField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArraySymmetricDifferenceExpression(parameterValue.asParameter(), someNumberArrayField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SYMDIFF with positional parameter as secondArray`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = CouchbaseDopeQuery(
            queryString = "ARRAY_SYMDIFF(`numberArrayField`, $1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArraySymmetricDifferenceExpression(someNumberArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SYMDIFF with all positional parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = listOf(4, 5, 6)
        val expected = CouchbaseDopeQuery(
            queryString = "ARRAY_SYMDIFF($1, $2)",
            DopeParameters(positionalParameters = listOf(parameterValueCollection, parameterValue)),
        )
        val underTest = ArraySymmetricDifferenceExpression(parameterValueCollection.asParameter(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SYMDIFF with named parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "ARRAY_SYMDIFF(\$$parameterName, `numberArrayField`)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArraySymmetricDifferenceExpression(parameterValue.asParameter(parameterName), someNumberArrayField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SYMDIFF with named parameter as value`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "ARRAY_SYMDIFF(`numberArrayField`, \$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArraySymmetricDifferenceExpression(someNumberArrayField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SYMDIFF with all named parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterName = "param1"
        val parameterValue = listOf(4, 5, 6)
        val parameterName2 = "param2"
        val expected = CouchbaseDopeQuery(
            queryString = "ARRAY_SYMDIFF(\$$parameterName, \$$parameterName2)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValueCollection, parameterName2 to parameterValue)),
        )
        val underTest =
            ArraySymmetricDifferenceExpression(parameterValueCollection.asParameter(parameterName), parameterValue.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SYMDIFF with mixed named and positional parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterName = "param"
        val parameterValue = listOf(4, 5, 6)
        val expected = CouchbaseDopeQuery(
            queryString = "ARRAY_SYMDIFF(\$$parameterName, $1)",

            DopeParameters(namedParameters = mapOf(parameterName to parameterValueCollection), positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArraySymmetricDifferenceExpression(parameterValueCollection.asParameter(parameterName), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_SYMDIFF extension type type`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberArrayField()
        val expected = ArraySymmetricDifferenceExpression(firstArray, secondArray)

        val actual = firstArray.symDiff(secondArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFF extension select type`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberArrayField()
        val expected = ArraySymmetricDifferenceExpression(firstArray.asExpression(), secondArray)

        val actual = firstArray.symDiff(secondArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFF extension type select`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifferenceExpression(firstArray, secondArray.asExpression())

        val actual = firstArray.symDiff(secondArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFF extension select select`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifferenceExpression(firstArray.asExpression(), secondArray.asExpression())

        val actual = firstArray.symDiff(secondArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFF extension type type type`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberArrayField()
        val expected = ArraySymmetricDifferenceExpression(firstArray, secondArray, listOf(thirdArray))

        val actual = firstArray.symDiff(secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFF extension select type type`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberArrayField()
        val expected = ArraySymmetricDifferenceExpression(firstArray.asExpression(), secondArray, listOf(thirdArray))

        val actual = firstArray.symDiff(secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFF extension type select type`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberArrayField()
        val expected = ArraySymmetricDifferenceExpression(firstArray, secondArray.asExpression(), listOf(thirdArray))

        val actual = firstArray.symDiff(secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFF extension type type select`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifferenceExpression(firstArray, secondArray, listOf(thirdArray.asExpression()))

        val actual = firstArray.symDiff(secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFF extension select select type`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberArrayField()
        val expected = ArraySymmetricDifferenceExpression(firstArray.asExpression(), secondArray.asExpression(), listOf(thirdArray))

        val actual = firstArray.symDiff(secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFF extension select type select`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifferenceExpression(firstArray.asExpression(), secondArray, listOf(thirdArray.asExpression()))

        val actual = firstArray.symDiff(secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFF extension type select select`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifferenceExpression(firstArray, secondArray.asExpression(), listOf(thirdArray.asExpression()))

        val actual = firstArray.symDiff(secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFF extension select select select`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifferenceExpression(
            firstArray.asExpression(),
            secondArray.asExpression(),
            listOf(thirdArray.asExpression()),
        )

        val actual = firstArray.symDiff(secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFF1 extension type type`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberArrayField()
        val expected = ArraySymmetricDifference1Expression(firstArray, secondArray)

        val actual = firstArray.symDiff1(secondArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFF1 extension select type`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberArrayField()
        val expected = ArraySymmetricDifference1Expression(firstArray.asExpression(), secondArray)

        val actual = firstArray.symDiff1(secondArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFF1 extension type select`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifference1Expression(firstArray, secondArray.asExpression())

        val actual = firstArray.symDiff1(secondArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFF1 extension select select`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifference1Expression(firstArray.asExpression(), secondArray.asExpression())

        val actual = firstArray.symDiff1(secondArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFF1 extension type type type`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberArrayField()
        val expected = ArraySymmetricDifference1Expression(firstArray, secondArray, listOf(thirdArray))

        val actual = firstArray.symDiff1(secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFF1 extension select type type`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberArrayField()
        val expected = ArraySymmetricDifference1Expression(firstArray.asExpression(), secondArray, listOf(thirdArray))

        val actual = firstArray.symDiff1(secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFF1 extension type select type`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberArrayField()
        val expected = ArraySymmetricDifference1Expression(firstArray, secondArray.asExpression(), listOf(thirdArray))

        val actual = firstArray.symDiff1(secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFF1 extension type type select`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifference1Expression(firstArray, secondArray, listOf(thirdArray.asExpression()))

        val actual = firstArray.symDiff1(secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFF1 extension select select type`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberArrayField()
        val expected = ArraySymmetricDifference1Expression(firstArray.asExpression(), secondArray.asExpression(), listOf(thirdArray))

        val actual = firstArray.symDiff1(secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFF1 extension select type select`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifference1Expression(firstArray.asExpression(), secondArray, listOf(thirdArray.asExpression()))

        val actual = firstArray.symDiff1(secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFF1 extension type select select`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifference1Expression(firstArray, secondArray.asExpression(), listOf(thirdArray.asExpression()))

        val actual = firstArray.symDiff1(secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFF1 extension select select select`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifference1Expression(
            firstArray.asExpression(),
            secondArray.asExpression(),
            listOf(thirdArray.asExpression()),
        )

        val actual = firstArray.symDiff1(secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFFN extension type type`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberArrayField()
        val expected = ArraySymmetricDifferenceNExpression(firstArray, secondArray)

        val actual = firstArray.symDiffN(secondArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFFN extension select type`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberArrayField()
        val expected = ArraySymmetricDifferenceNExpression(firstArray.asExpression(), secondArray)

        val actual = firstArray.symDiffN(secondArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFFN extension type select`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifferenceNExpression(firstArray, secondArray.asExpression())

        val actual = firstArray.symDiffN(secondArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFFN extension select select`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifferenceNExpression(firstArray.asExpression(), secondArray.asExpression())

        val actual = firstArray.symDiffN(secondArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFFN extension type type type`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberArrayField()
        val expected = ArraySymmetricDifferenceNExpression(firstArray, secondArray, listOf(thirdArray))

        val actual = firstArray.symDiffN(secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFFN extension select type type`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberArrayField()
        val expected = ArraySymmetricDifferenceNExpression(firstArray.asExpression(), secondArray, listOf(thirdArray))

        val actual = firstArray.symDiffN(secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFFN extension type select type`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberArrayField()
        val expected = ArraySymmetricDifferenceNExpression(firstArray, secondArray.asExpression(), listOf(thirdArray))

        val actual = firstArray.symDiffN(secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFFN extension type type select`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifferenceNExpression(firstArray, secondArray, listOf(thirdArray.asExpression()))

        val actual = firstArray.symDiffN(secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFFN extension select select type`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberArrayField()
        val expected = ArraySymmetricDifferenceNExpression(firstArray.asExpression(), secondArray.asExpression(), listOf(thirdArray))

        val actual = firstArray.symDiffN(secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFFN extension select type select`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberArrayField()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifferenceNExpression(firstArray.asExpression(), secondArray, listOf(thirdArray.asExpression()))

        val actual = firstArray.symDiffN(secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFFN extension type select select`() {
        val firstArray = someNumberArrayField()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifferenceNExpression(firstArray, secondArray.asExpression(), listOf(thirdArray.asExpression()))

        val actual = firstArray.symDiffN(secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_SYMDIFFN extension select select select`() {
        val firstArray = someNumberSelectRawClause()
        val secondArray = someNumberSelectRawClause()
        val thirdArray = someNumberSelectRawClause()
        val expected = ArraySymmetricDifferenceNExpression(
            firstArray.asExpression(),
            secondArray.asExpression(),
            listOf(thirdArray.asExpression()),
        )

        val actual = firstArray.symDiffN(secondArray, thirdArray)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
