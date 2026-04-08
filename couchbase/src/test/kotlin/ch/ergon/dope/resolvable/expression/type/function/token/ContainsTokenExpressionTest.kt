package ch.ergon.dope.resolvable.expression.type.function.token

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ContainsTokenExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support contains token`() {
        val expected = CouchbaseDopeQuery(
            queryString = "CONTAINS_TOKEN(`stringField`, \"Inn\")",
        )
        val underTest = ContainsTokenExpression(someStringField(), "Inn".toDopeType())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support contains token with options`() {
        val expected = CouchbaseDopeQuery(
            queryString = "CONTAINS_TOKEN(`stringField`, \"Inn\", {\"specials\": true})",
        )
        val underTest = ContainsTokenExpression(
            someStringField(),
            "Inn".toDopeType(),
            ContainsTokenOptions(includeSpecialCharacters = true),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support contains token with all options`() {
        val expected = CouchbaseDopeQuery(
            queryString = "CONTAINS_TOKEN(`stringField`, \"Inn\", " +
                "{\"names\": true, \"case\": \"LOWER\", \"specials\": false, \"split\": true, \"trim\": false})",
        )
        val underTest = ContainsTokenExpression(
            someStringField(),
            "Inn".toDopeType(),
            ContainsTokenOptions(hasNames = true, case = TokenCase.LOWER, includeSpecialCharacters = false, split = true, trim = false),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support contains token with empty options`() {
        val expected = CouchbaseDopeQuery(
            queryString = "CONTAINS_TOKEN(`stringField`, \"Inn\")",
        )
        val underTest = ContainsTokenExpression(
            someStringField(),
            "Inn".toDopeType(),
            ContainsTokenOptions(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support contains token extension function with type expression`() {
        val expected = ContainsTokenExpression(someStringField(), "Inn".toDopeType())

        val actual = someStringField().containsToken("Inn".toDopeType())

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support contains token extension function with string`() {
        val expected = ContainsTokenExpression(someStringField(), "Inn".toDopeType())

        val actual = someStringField().containsToken("Inn")

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support contains token extension function with options`() {
        val options = ContainsTokenOptions(includeSpecialCharacters = true)
        val expected = ContainsTokenExpression(someStringField(), "Inn".toDopeType(), options)

        val actual = someStringField().containsToken("Inn", options)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support contains token extension function with type expression and options`() {
        val options = ContainsTokenOptions(includeSpecialCharacters = true)
        val expected = ContainsTokenExpression(someStringField(), "Inn".toDopeType(), options)

        val actual = someStringField().containsToken("Inn".toDopeType(), options)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
