package ch.ergon.dope.resolvable.expression.type.relational

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class NotLikeExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support not like with no parameters`() {
        val expected = CouchbaseDopeQuery(
            queryString = "`stringField` NOT LIKE `stringField`",
        )
        val underTest = NotLikeExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not like with positional parameter`() {
        val parameterValue = "test"
        val expected = CouchbaseDopeQuery(
            queryString = "`stringField` NOT LIKE $1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = NotLikeExpression(someStringField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not like with named parameter`() {
        val parameterValue = "test"
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "`stringField` NOT LIKE \$$parameterName",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = NotLikeExpression(someStringField(), parameterValue.asParameter(parameterName))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support not like function with type`() {
        val left = someStringField("left")
        val right = someStringField("right")
        val expected = NotLikeExpression(left, right)

        val actual = left.isNotLike(right)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support not like function with string`() {
        val left = someStringField("left")
        val right = someString("right")
        val expected = NotLikeExpression(left, right.toDopeType())

        val actual = left.isNotLike(right)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support not like function with string type`() {
        val left = someString("left")
        val right = someStringField("right")
        val expected = NotLikeExpression(left.toDopeType(), right)

        val actual = left.isNotLike(right)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support not like function with string string`() {
        val left = someString("left")
        val right = someString("right")
        val expected = NotLikeExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isNotLike(right)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
