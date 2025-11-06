package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class TrimExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support trim`() {
        val expected = CouchbaseDopeQuery(
            queryString = "TRIM(`stringField`)",
        )
        val underTest = TrimExpression(someStringField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support trim with positional parameter`() {
        val parameterValue = "test"
        val expected = CouchbaseDopeQuery(
            queryString = "TRIM($1)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = TrimExpression(parameterValue.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support trim with extra positional parameters`() {
        val expected = CouchbaseDopeQuery(
            queryString = "TRIM(`stringField`, `stringField`)",
        )
        val underTest = TrimExpression(someStringField(), someStringField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support trim with extra positional parameter and named parameter`() {
        val parameterValue = "test"
        val expected = CouchbaseDopeQuery(
            queryString = "TRIM($1, `stringField`)",
            DopeParameters(positionalParameters = listOf(parameterValue)),
        )
        val underTest = TrimExpression(parameterValue.asParameter(), someStringField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support trim with all positional parameters`() {
        val parameterValue = "test"
        val parameterValue2 = "test2"
        val expected = CouchbaseDopeQuery(
            queryString = "TRIM($1, $2)",
            DopeParameters(positionalParameters = listOf(parameterValue, parameterValue2)),
        )
        val underTest = TrimExpression(parameterValue.asParameter(), parameterValue2.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support trim with mixed parameters`() {
        val parameterValue = "test"
        val parameterName = "param"
        val expected = CouchbaseDopeQuery(
            queryString = "TRIM(\$$parameterName, `stringField`)",
            DopeParameters(namedParameters = mapOf(parameterName to parameterValue)),
        )
        val underTest = TrimExpression(parameterValue.asParameter(parameterName), someStringField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support trim function type type`() {
        val inStr = someStringField("inStr")
        val char = someStringField("extra")
        val expected = TrimExpression(inStr, char)

        val actual = trim(inStr, char)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support trim function type`() {
        val inStr = someStringField("inStr")
        val char = null
        val expected = TrimExpression(inStr, char)

        val actual = trim(inStr)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support trim function type string`() {
        val inStr = someStringField("inStr")
        val char = someString()
        val expected = TrimExpression(inStr, char.toDopeType())

        val actual = trim(inStr, char)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support trim function string type`() {
        val inStr = someString("inStr")
        val char = someStringField("extra")
        val expected = TrimExpression(inStr.toDopeType(), char)

        val actual = trim(inStr, char)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support trim function string`() {
        val inStr = someString("inStr")
        val char = null
        val expected = TrimExpression(inStr.toDopeType(), char)

        val actual = trim(inStr)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support trim function string string`() {
        val inStr = someString("inStr")
        val char = someString("extra")
        val expected = TrimExpression(inStr.toDopeType(), char.toDopeType())

        val actual = trim(inStr, char)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
