package ch.ergon.dope.resolvable.expression.type.function.string

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

class SplitExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support split`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SPLIT(`stringField`)",
        )
        val underTest = SplitExpression(someStringField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support split with positional parameter`() {
        val parameterValue = "test"
        val expected = CouchbaseDopeQuery(
            queryString = "SPLIT($1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = SplitExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support split with substring`() {
        val expected = CouchbaseDopeQuery(
            queryString = "SPLIT(`stringField`, `stringField`)",
        )
        val underTest = SplitExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support split with substring and positional parameter`() {
        val parameterValue = "test"
        val expected = CouchbaseDopeQuery(
            queryString = "SPLIT($1, `stringField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = SplitExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support split with all positional parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test"
        val expected = CouchbaseDopeQuery(
            queryString = "SPLIT($1, $2)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = SplitExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support split with mixed parameters`() {
        val parameterValue = "test"
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "SPLIT(\$$parameterName, `stringField`)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = SplitExpression(parameterValue.asParameter(parameterName), someStringField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support split function type type`() {
        val inStr = someStringField("inStr")
        val inSubstring = someStringField("inSubstring")
        val expected = SplitExpression(inStr, inSubstring)

        val actual = inStr.split(inSubstring)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support split function type string`() {
        val inStr = someStringField("inStr")
        val inSubstring = someString("inSubstring")
        val expected = SplitExpression(inStr, inSubstring.toDopeType())

        val actual = inStr.split(inSubstring)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support split function type`() {
        val inStr = someStringField("inStr")
        val inSubstring = null
        val expected = SplitExpression(inStr, inSubstring)

        val actual = inStr.split()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support split function string type`() {
        val inStr = someString("inStr")
        val inSubstring = someStringField("inSubstring")
        val expected = SplitExpression(inStr.toDopeType(), inSubstring)

        val actual = inStr.split(inSubstring)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support split function string`() {
        val inStr = someString("inStr")
        val inSubstring = null
        val expected = SplitExpression(inStr.toDopeType(), inSubstring)

        val actual = inStr.split()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support split function string string`() {
        val inStr = someString("inStr")
        val inSubstring = someString("inSubstring")
        val expected = SplitExpression(inStr.toDopeType(), inSubstring.toDopeType())

        val actual = inStr.split(inSubstring)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
