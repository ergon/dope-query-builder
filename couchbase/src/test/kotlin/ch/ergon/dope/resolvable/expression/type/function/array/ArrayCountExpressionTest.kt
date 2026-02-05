package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someSelectRawClause
import ch.ergon.dope.resolvable.expression.type.asParameter
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayCountExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support ARRAY_COUNT without parameters`() {
        val expected = CouchbaseDopeQuery(
            queryString = "ARRAY_COUNT(`numberArrayField`)",
        )
        val underTest = ArrayCountExpression(someNumberArrayField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_COUNT with positional parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = CouchbaseDopeQuery(
            queryString = "ARRAY_COUNT($1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayCountExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_COUNT with named parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "ARRAY_COUNT(\$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayCountExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_COUNT extension`() {
        val array = someNumberArrayField()
        val expected = ArrayCountExpression(array)

        val actual = array.count()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_COUNT extension select`() {
        val selectClause = someSelectRawClause()
        val expected = ArrayCountExpression(selectClause.asExpression())

        val actual = selectClause.count()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
