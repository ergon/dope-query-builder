package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someNumberSelectRawClause
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayFlattenExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support ARRAY_FLATTEN`() {
        val expected = CouchbaseDopeQuery(
            queryString = "ARRAY_FLATTEN(`numberArrayField`, `numberField`)",
        )
        val underTest = ArrayFlattenExpression(someNumberArrayField(), someNumberField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_FLATTEN with positional parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = CouchbaseDopeQuery(
            queryString = "ARRAY_FLATTEN($1, `numberField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayFlattenExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_FLATTEN with named parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "ARRAY_FLATTEN(\$$parameterName, `numberField`)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayFlattenExpression(parameterValue.asParameter(parameterName), someNumberField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_FLATTEN with positional parameter as value`() {
        val parameterValue = 1
        val expected = CouchbaseDopeQuery(
            queryString = "ARRAY_FLATTEN(`numberArrayField`, $1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayFlattenExpression(someNumberArrayField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_FLATTEN with named parameter as value`() {
        val parameterValue = 1
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "ARRAY_FLATTEN(`numberArrayField`, \$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayFlattenExpression(someNumberArrayField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_FLATTEN with positional all parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = 1
        val expected = CouchbaseDopeQuery(
            queryString = "ARRAY_FLATTEN($1, $2)",
            DopeParameters(positionalParameters = listOf(parameterValueCollection, parameterValue)),
        )
        val underTest = ArrayFlattenExpression(parameterValueCollection.asParameter(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_FLATTEN with named all parameters`() {
        val parameterValueCollection = listOf(1, 2, 3)
        val parameterValue = 1
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = CouchbaseDopeQuery(
            queryString = "ARRAY_FLATTEN(\$$parameterName, \$$parameterName2)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValueCollection, parameterName2 to parameterValue)),
        )
        val underTest = ArrayFlattenExpression(
            parameterValueCollection.asParameter(parameterName),
            parameterValue.asParameter(parameterName2),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_FLATTEN extension type`() {
        val array = someNumberArrayField()
        val depth = someNumberField()
        val expected = ArrayFlattenExpression(array, depth)

        val actual = array.flatten(depth)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_FLATTEN extension number`() {
        val array = someNumberArrayField()
        val depth = 1
        val expected = ArrayFlattenExpression(array, depth.toDopeType())

        val actual = array.flatten(depth)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_FLATTEN extension select type`() {
        val selectClause = someNumberSelectRawClause()
        val depth = someNumberField()
        val expected = ArrayFlattenExpression(selectClause.asExpression(), depth)

        val actual = selectClause.flatten(depth)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_FLATTEN extension select number`() {
        val selectClause = someNumberSelectRawClause()
        val depth = 1
        val expected = ArrayFlattenExpression(selectClause.asExpression(), depth.toDopeType())

        val actual = selectClause.flatten(depth)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
