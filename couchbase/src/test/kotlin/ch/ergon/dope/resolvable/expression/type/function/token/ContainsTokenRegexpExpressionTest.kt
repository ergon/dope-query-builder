package ch.ergon.dope.resolvable.expression.type.function.token

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ContainsTokenRegexpExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support contains token regexp`() {
        val expected = CouchbaseDopeQuery(
            queryString = "CONTAINS_TOKEN_REGEXP(`stringField`, \"In+.*\")",
        )
        val underTest = ContainsTokenRegexpExpression(someStringField(), "In+.*".toDopeType())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support contains token regexp with options`() {
        val expected = CouchbaseDopeQuery(
            queryString = "CONTAINS_TOKEN_REGEXP(`stringField`, \"In+.*\", {\"specials\": true})",
        )
        val underTest = ContainsTokenRegexpExpression(
            someStringField(),
            "In+.*".toDopeType(),
            ContainsTokenOptions(specials = true),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support contains token regexp with all options`() {
        val expected = CouchbaseDopeQuery(
            queryString = "CONTAINS_TOKEN_REGEXP(`stringField`, \"In+.*\", " +
                "{\"names\": true, \"case\": \"LOWER\", \"specials\": true, \"split\": true, \"trim\": true})",
        )
        val underTest = ContainsTokenRegexpExpression(
            someStringField(),
            "In+.*".toDopeType(),
            ContainsTokenOptions(names = true, case = TokenCases.LOWER, specials = true, split = true, trim = true),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support contains token regexp with empty options`() {
        val expected = CouchbaseDopeQuery(
            queryString = "CONTAINS_TOKEN_REGEXP(`stringField`, \"In+.*\")",
        )
        val underTest = ContainsTokenRegexpExpression(
            someStringField(),
            "In+.*".toDopeType(),
            ContainsTokenOptions(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support contains token regexp extension function with type expression`() {
        val expected = ContainsTokenRegexpExpression(someStringField(), "In+.*".toDopeType())

        val actual = someStringField().containsTokenRegexp("In+.*".toDopeType())

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support contains token regexp extension function with string`() {
        val expected = ContainsTokenRegexpExpression(someStringField(), "In+.*".toDopeType())

        val actual = someStringField().containsTokenRegexp("In+.*")

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support contains token regexp extension function with options`() {
        val options = ContainsTokenOptions(specials = true)
        val expected = ContainsTokenRegexpExpression(someStringField(), "In+.*".toDopeType(), options)

        val actual = someStringField().containsTokenRegexp("In+.*", options)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support contains token regexp extension function with type expression and options`() {
        val options = ContainsTokenOptions(specials = true)
        val expected = ContainsTokenRegexpExpression(someStringField(), "In+.*".toDopeType(), options)

        val actual = someStringField().containsTokenRegexp("In+.*".toDopeType(), options)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
