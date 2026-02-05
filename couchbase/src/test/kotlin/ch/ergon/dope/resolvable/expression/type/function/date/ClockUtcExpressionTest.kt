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

class ClockUtcExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support CLOCK_UTC without format`() {
        val expected = CouchbaseDopeQuery(
            queryString = "CLOCK_UTC()",
        )
        val underTest = ClockUtcExpression()

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support CLOCK_UTC with format field`() {
        val fmtField = someStringField()
        val expected = CouchbaseDopeQuery(
            queryString = "CLOCK_UTC(`stringField`)",
        )
        val underTest = ClockUtcExpression(fmtField)

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support CLOCK_UTC with positional parameter format`() {
        val fmt = "dd/MM/yyyy"
        val expected = CouchbaseDopeQuery(
            queryString = "CLOCK_UTC($1)",
            DopeParameters(positionalParameters = listOf(fmt)),
        )
        val underTest = ClockUtcExpression(fmt.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support CLOCK_UTC with named parameter format`() {
        val fmt = "dd/MM/yyyy"
        val name = "f"
        val expected = CouchbaseDopeQuery(
            queryString = "CLOCK_UTC(\$$name)",
            DopeParameters(namedParameters = mapOf(name to fmt)),
        )
        val underTest = ClockUtcExpression(fmt.asParameter(name))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support utcClockString extension with raw string`() {
        val raw = someString()
        val expected = ClockUtcExpression(raw.toDopeType())
        val actual = utcClockString(raw)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
