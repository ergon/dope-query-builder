package ch.ergon.dope.resolvable.expression.type.function.token

import ch.ergon.dope.couchbase.CouchbaseDopeQuery
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.helper.ResolverDependentTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ContainsTokenOptionsTest : ResolverDependentTest {
    override lateinit var resolver: CouchbaseResolver

    @Test
    fun `should support containsTokenOptions factory function`() {
        val expected = ContainsTokenOptions(
            hasNames = true,
            case = TokenCase.LOWER,
            includeSpecialCharacters = true,
            split = false,
            trim = true,
        )

        val actual = containsTokenOptions(
            hasNames = true,
            case = TokenCase.LOWER,
            includeSpecialCharacters = true,
            split = false,
            trim = true,
        )

        assertEquals(expected, actual)
    }

    @Test
    fun `should support containsTokenOptions with no arguments`() {
        val expected = ContainsTokenOptions()

        val actual = containsTokenOptions()

        assertEquals(expected, actual)
    }

    @Test
    fun `should resolve containsTokenOptions with all options`() {
        val expected = CouchbaseDopeQuery(
            queryString = "{\"names\": true, \"case\": \"LOWER\", \"specials\": false, \"split\": true, \"trim\": false}",
        )

        val actual = containsTokenOptions(
            hasNames = true,
            case = TokenCase.LOWER,
            includeSpecialCharacters = false,
            split = true,
            trim = false,
        ).toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should resolve containsTokenOptions with partial options`() {
        val expected = CouchbaseDopeQuery(
            queryString = "{\"specials\": true}",
        )

        val actual = containsTokenOptions(includeSpecialCharacters = true).toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should resolve containsTokenOptions with empty options`() {
        val expected = CouchbaseDopeQuery(
            queryString = "",
        )

        val actual = containsTokenOptions().toDopeQuery(resolver)

        assertEquals(expected, actual)
    }

    @Test
    fun `should resolve containsTokenOptions with upper case`() {
        val expected = CouchbaseDopeQuery(
            queryString = "{\"case\": \"UPPER\"}",
        )

        val actual = containsTokenOptions(case = TokenCase.UPPER).toDopeQuery(resolver)

        assertEquals(expected, actual)
    }
}
