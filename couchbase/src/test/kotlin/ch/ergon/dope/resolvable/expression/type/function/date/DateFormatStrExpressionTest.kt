package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.asParameter
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class DateFormatStrExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support DATE_FORMAT_STR with fields`() {
        val expected = CouchbaseDopeQuery(
            queryString = "DATE_FORMAT_STR(`stringField`, `stringField`)",
        )
        val underTest = DateFormatStrExpression(
            someStringField(),
            someStringField(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support DATE_FORMAT_STR with positional parameter date`() {
        val dateValue = "2019-12-31"
        val expected = CouchbaseDopeQuery(
            queryString = "DATE_FORMAT_STR($1, `stringField`)",
            DopeParameters(positionalParameters = listOf(dateValue)),
        )
        val underTest = DateFormatStrExpression(
            dateValue.asParameter(),
            someStringField(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support DATE_FORMAT_STR with named parameter format`() {
        val fmtValue = "yyyy"
        val name = "fmt"
        val expected = CouchbaseDopeQuery(
            queryString = "DATE_FORMAT_STR(`stringField`, \$$name)",
            DopeParameters(namedParameters = mapOf(name to fmtValue)),
        )
        val underTest = DateFormatStrExpression(
            someStringField(),
            fmtValue.asParameter(name),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support formatDate extension with field`() {
        val expr = someStringField().formatDate(someStringField())
        val expected = DateFormatStrExpression(someStringField(), someStringField())

        assertEquals(expected.toDopeQuery(resolver), expr.toDopeQuery(resolver))
    }

    @Test
    fun `should support formatDate extension with raw string`() {
        val expr = someStringField().formatDate("MM/dd/yyyy")
        val expected = DateFormatStrExpression(someStringField(), "MM/dd/yyyy".toDopeType())

        assertEquals(expected.toDopeQuery(resolver), expr.toDopeQuery(resolver))
    }

    @Test
    fun `should support formatDate extension with raw type`() {
        val raw = "2020-06-01T00:00:00Z"
        val expr = raw.formatDate("MM/dd/yyyy".toDopeType())
        val expected = DateFormatStrExpression(raw.toDopeType(), "MM/dd/yyyy".toDopeType())

        assertEquals(expected.toDopeQuery(resolver), expr.toDopeQuery(resolver))
    }

    @Test
    fun `should support String formatDate extension`() {
        val raw = "2020-01-01"
        val expr = raw.formatDate("yyyy-MM-dd")
        val expected = DateFormatStrExpression(raw.toDopeType(), "yyyy-MM-dd".toDopeType())

        assertEquals(expected.toDopeQuery(resolver), expr.toDopeQuery(resolver))
    }
}
