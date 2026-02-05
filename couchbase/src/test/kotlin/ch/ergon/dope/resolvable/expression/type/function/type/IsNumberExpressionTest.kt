package ch.ergon.dope.resolvable.expression.type.function.type

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

class IsNumberExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support is number expression with no parameters`() {
        val expected = CouchbaseDopeQuery(
            queryString = "ISNUMBER(`numberField`)",
        )
        val underTest = IsNumberExpression(someNumberField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is number expression with positional parameter`() {
        val parameterValue = someNumber()
        val expected = CouchbaseDopeQuery(
            queryString = "ISNUMBER($1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = IsNumberExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is number expression with named parameter`() {
        val parameterValue = someNumber()
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "ISNUMBER(\$$parameterName)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = IsNumberExpression(parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support is number extension`() {
        val number = someNumber().toDopeType()
        val expected = IsNumberExpression(number)

        val actual = number.isNumber()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
