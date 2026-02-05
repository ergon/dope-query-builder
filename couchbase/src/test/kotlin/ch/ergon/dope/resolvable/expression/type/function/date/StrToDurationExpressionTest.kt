package ch.ergon.dope.resolvable.expression.type.function.date

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

class StrToDurationExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support STR_TO_DURATION with field`() {
        val expected = CouchbaseDopeQuery(
            queryString = "STR_TO_DURATION(`stringField`)",
        )
        val underTest = StringToDurationExpression(someStringField())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support STR_TO_DURATION with positional parameter duration`() {
        val dur = "1h"
        val expected = CouchbaseDopeQuery(
            queryString = "STR_TO_DURATION($1)",
            DopeParameters(positionalParameters = listOf(dur)),
        )
        val underTest = StringToDurationExpression(dur.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support STR_TO_DURATION with named parameter duration`() {
        val dur = "5m"
        val name = "d"
        val expected = CouchbaseDopeQuery(
            queryString = "STR_TO_DURATION(\$$name)",
            DopeParameters(namedParameters = mapOf(name to dur)),
        )
        val underTest = StringToDurationExpression(dur.asParameter(name))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support toDurationMillis extension on TypeExpression`() {
        val expr = someStringField().toDurationNanos()
        val expected = StringToDurationExpression(someStringField())

        assertEquals(expected.toDopeQuery(resolver), expr.toDopeQuery(resolver))
    }

    @Test
    fun `should support String toDurationMillis extension`() {
        val raw = someString()
        val expr = raw.toDurationNanos()
        val expected = StringToDurationExpression(raw.toDopeType())

        assertEquals(expected.toDopeQuery(resolver), expr.toDopeQuery(resolver))
    }
}
