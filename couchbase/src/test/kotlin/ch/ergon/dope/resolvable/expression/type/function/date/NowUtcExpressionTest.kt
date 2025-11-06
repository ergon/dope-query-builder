package ch.ergon.dope.resolvable.expression.type.function.date

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

class NowUtcExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support NOW_UTC without format`() {
        val expected = CouchbaseDopeQuery(
            queryString = "NOW_UTC()",
        )
        val underTest = NowUtcExpression()

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOW_UTC with positional parameter format`() {
        val fmt = "yyyy-MM-dd"
        val expected = CouchbaseDopeQuery(
            queryString = "NOW_UTC($1)",
            DopeParameters(positionalParameters = listOf(fmt)),
        )
        val underTest = NowUtcExpression(fmt.asParameter())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support NOW_UTC with named parameter format`() {
        val fmt = "yyyy-MM-dd"
        val name = "f"
        val expected = CouchbaseDopeQuery(
            queryString = "NOW_UTC(\$$name)",
            DopeParameters(namedParameters = mapOf(name to fmt)),
        )
        val underTest = NowUtcExpression(fmt.asParameter(name))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support utcNowString extension`() {
        val expected = NowUtcExpression()
        val actual = utcNowString()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support utcNowString extension with field`() {
        val fmtField = someStringField()
        val expected = NowUtcExpression(fmtField)
        val actual = utcNowString(fmtField)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support utcNowString extension with raw string`() {
        val raw = someString()
        val expected = NowUtcExpression(raw.toDopeType())
        val actual = utcNowString(raw)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
