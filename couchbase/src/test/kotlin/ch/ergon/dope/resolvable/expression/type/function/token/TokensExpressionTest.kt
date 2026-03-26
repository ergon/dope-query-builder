package ch.ergon.dope.resolvable.expression.type.function.token

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import ch.ergon.dope.helper.someStringArrayField
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class TokensExpressionTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support tokens`() {
        val expected = CouchbaseDopeQuery(
            queryString = "TOKENS([\"test\", \"test2\"])",
        )
        val underTest = TokensExpression(listOf("test", "test2").toDopeType())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support tokens with options`() {
        val expected = CouchbaseDopeQuery(
            queryString = "TOKENS([\"test\"], {\"name\": true, \"specials\": true})",
        )
        val underTest = TokensExpression(listOf("test").toDopeType(), CustomTokenOptions(name = true, specials = true))

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support tokens with all options`() {
        val expected = CouchbaseDopeQuery(
            queryString = "TOKENS([\"test\"], {\"name\": true, \"case\": \"UPPER\", \"specials\": true})",
        )
        val underTest = TokensExpression(
            listOf("test").toDopeType(),
            CustomTokenOptions(name = true, case = TokenCases.UPPER, specials = true),
        )

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support tokens with empty options`() {
        val expected = CouchbaseDopeQuery(
            queryString = "TOKENS([\"test\"])",
        )
        val underTest = TokensExpression(listOf("test").toDopeType(), CustomTokenOptions())

        val actual = underTest.toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support tokens extension function`() {
        val inString = listOf("test1", "test2")
        val expected = TokensExpression(inString.toDopeType())

        val actual = inString.tokens()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support tokens extension function with options`() {
        val inString = listOf("test1", "test2")
        val options = CustomTokenOptions(name = true, specials = true)
        val expected = TokensExpression(inString.toDopeType(), options)

        val actual = inString.tokens(options)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support tokens type extension function`() {
        val inString = someStringArrayField()
        val expected = TokensExpression(inString)

        val actual = inString.tokens()

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }

    @Test
    fun `should support tokens type extension function with options`() {
        val inString = someStringArrayField()
        val options = CustomTokenOptions(name = true, specials = true)
        val expected = TokensExpression(inString, options)

        val actual = inString.tokens(options)

        assertEquals(expected.toDopeQuery(resolver), actual.toDopeQuery(resolver))
    }
}
