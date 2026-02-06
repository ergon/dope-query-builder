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

class ArrayIfNullExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support ARRAY_IFNULL`() {
        val array = someNumberArrayField()
        val expected = CouchbaseDopeQuery(
            queryString = "ARRAY_IFNULL(`numberArrayField`)",
        )
        val underTest = ArrayIfNullExpression(array)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_IFNULL with positional parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val expected = CouchbaseDopeQuery(
            queryString = "ARRAY_IFNULL($1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = ArrayIfNullExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_IFNULL with named parameter`() {
        val parameterValue = listOf(1, 2, 3)
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "ARRAY_IFNULL(\$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = ArrayIfNullExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ARRAY_IFNULL extension type`() {
        val array = someNumberArrayField()
        val expected = ArrayIfNullExpression(array)

        val actual = array.ifNull()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support ARRAY_IFNULL extension select`() {
        val selectClause = someSelectRawClause()
        val expected = ArrayIfNullExpression(selectClause.asExpression())

        val actual = selectClause.ifNull()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
