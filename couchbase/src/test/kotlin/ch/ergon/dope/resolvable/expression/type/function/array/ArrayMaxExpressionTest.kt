package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someSelectRawClause
import ch.ergon.dope.resolvable.expression.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayMaxExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support ARRAY_MAX`() {
        val expected = CouchbaseDopeQuery(
            queryString = "ARRAY_MAX(`numberArrayField`)",
        )
        val underTest = ArrayMaxExpression(someNumberArrayField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MAX with positional parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = CouchbaseDopeQuery(
            queryString = "ARRAY_MAX($1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayMaxExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MAX with named parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "ARRAY_MAX(\$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayMaxExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_MAX extension type`() {
        val array = someNumberArrayField()
        val expected = ArrayMaxExpression(array)

        val actual = array.max()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_MAX extension select`() {
        val selectClause = someSelectRawClause()
        val expected = ArrayMaxExpression(selectClause.asExpression())

        val actual = selectClause.max()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
