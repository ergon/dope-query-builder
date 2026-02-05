package ch.ergon.dope.resolvable.expression.type.arithmetic

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NegationExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support negation`() {
        val expected = CouchbaseDopeQuery(
            queryString = "-`numberField`",
        )
        val underTest = NegationExpression(someNumberField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support negation with positional parameter`() {
        val parameterValue = 4
        val expected = CouchbaseDopeQuery(
            queryString = "-$1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = NegationExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support negation with named parameter`() {
        val parameterValue = 4
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "-\$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = NegationExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support negation function type`() {
        val type = someNumberField()
        val expected = NegationExpression(type)

        val actual = neg(type)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support negation function number`() {
        val number = someNumber()
        val expected = NegationExpression(number.toDopeType())

        val actual = neg(number)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
