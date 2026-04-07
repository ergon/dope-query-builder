package ch.ergon.dope.resolvable.expression.type.function.token

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ContainsTokenLikeExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support contains token like`() {
        val expected = CouchbaseDopeQuery(
            queryString = "CONTAINS_TOKEN_LIKE(`stringField`, \"%uk\")",
        )
        val underTest = ContainsTokenLikeExpression(someStringField(), "%uk".toDopeType())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support contains token like with options`() {
        val expected = CouchbaseDopeQuery(
            queryString = "CONTAINS_TOKEN_LIKE(`stringField`, \"%uk\", {\"specials\": true})",
        )
        val underTest = ContainsTokenLikeExpression(
            someStringField(),
            "%uk".toDopeType(),
            ContainsTokenOptions(includeSpecialCharacters = true),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support contains token like with all options`() {
        val expected = CouchbaseDopeQuery(
            queryString = "CONTAINS_TOKEN_LIKE(`stringField`, \"%uk\", " +
                "{\"names\": false, \"case\": \"UPPER\", \"specials\": true, \"split\": false, \"trim\": true})",
        )
        val underTest = ContainsTokenLikeExpression(
            someStringField(),
            "%uk".toDopeType(),
            ContainsTokenOptions(hasNames = false, case = TokenCase.UPPER, includeSpecialCharacters = true, split = false, trim = true),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support contains token like with empty options`() {
        val expected = CouchbaseDopeQuery(
            queryString = "CONTAINS_TOKEN_LIKE(`stringField`, \"%uk\")",
        )
        val underTest = ContainsTokenLikeExpression(
            someStringField(),
            "%uk".toDopeType(),
            ContainsTokenOptions(),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support contains token like extension function with type expression`() {
        val expected = ContainsTokenLikeExpression(someStringField(), "%uk".toDopeType())

        val actual = someStringField().containsTokenLike("%uk".toDopeType())

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support contains token like extension function with string`() {
        val expected = ContainsTokenLikeExpression(someStringField(), "%uk".toDopeType())

        val actual = someStringField().containsTokenLike("%uk")

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support contains token like extension function with options`() {
        val options = ContainsTokenOptions(includeSpecialCharacters = true)
        val expected = ContainsTokenLikeExpression(someStringField(), "%uk".toDopeType(), options)

        val actual = someStringField().containsTokenLike("%uk", options)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support contains token like extension function with type expression and options`() {
        val options = ContainsTokenOptions(includeSpecialCharacters = true)
        val expected = ContainsTokenLikeExpression(someStringField(), "%uk".toDopeType(), options)

        val actual = someStringField().containsTokenLike("%uk".toDopeType(), options)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
