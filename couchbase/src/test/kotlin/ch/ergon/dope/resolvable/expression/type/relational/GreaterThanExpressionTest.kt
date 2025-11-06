package ch.ergon.dope.resolvable.expression.type.relational

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class GreaterThanExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support greater than`() {
        val expected = CouchbaseDopeQuery(
            queryString = "`numberField` > `numberField`",
        )
        val underTest = GreaterThanExpression(someNumberField(), someNumberField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greater than with positional parameter`() {
        val parameterValue = 5
        val expected = CouchbaseDopeQuery(
            queryString = "$1 > `numberField`",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = GreaterThanExpression(parameterValue.asParameter(), someNumberField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greater than with all positional parameters`() {
        val parameterValue = 5
        val parameterValue2 = 6
        val expected = CouchbaseDopeQuery(
            queryString = "$1 > $2",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = GreaterThanExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greater than with second positional parameter`() {
        val parameterValue = someNumber()
        val expected = CouchbaseDopeQuery(
            queryString = "`numberField` > $1",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = GreaterThanExpression(someNumberField(), parameterValue.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greater than with named parameter`() {
        val parameterValue = 5
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "$$parameterName > `numberField`",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = GreaterThanExpression(parameterValue.asParameter(parameterName), someNumberField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greater than with all named parameters`() {
        val parameterValue = 5
        val parameterValue2 = 6
        val parameterName = "param1"
        val parameterName2 = "param2"
        val expected = CouchbaseDopeQuery(
            queryString = "$$parameterName > $$parameterName2",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue, parameterName2 to parameterValue2)),
        )
        val underTest = GreaterThanExpression(parameterValue.asParameter(parameterName), parameterValue2.asParameter(parameterName2))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support greater function type type`() {
        val left = someNumberField()
        val right = someNumberField()
        val expected = GreaterThanExpression(left, right)

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support greater function type number`() {
        val left = someNumberField()
        val right = someNumber()
        val expected = GreaterThanExpression(left, right.toDopeType())

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support greater function number type`() {
        val left = someNumber()
        val right = someNumberField()
        val expected = GreaterThanExpression(left.toDopeType(), right)

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support greater function number number`() {
        val left = someString()
        val right = someString()
        val expected = GreaterThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support greater function type string`() {
        val left = someStringField()
        val right = someString()
        val expected = GreaterThanExpression(left, right.toDopeType())

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support greater function string type`() {
        val left = someString()
        val right = someStringField()
        val expected = GreaterThanExpression(left.toDopeType(), right)

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support greater function string string`() {
        val left = someString()
        val right = someString()
        val expected = GreaterThanExpression(left.toDopeType(), right.toDopeType())

        val actual = left.isGreaterThan(right)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
