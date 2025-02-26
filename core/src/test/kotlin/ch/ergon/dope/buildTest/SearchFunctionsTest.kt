package ch.ergon.dope.buildTest

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.function.search.fullTextSearch
import ch.ergon.dope.resolvable.expression.type.function.search.fullTextSearchMeta
import ch.ergon.dope.resolvable.expression.type.function.search.fullTextSearchScore
import ch.ergon.dope.resolvable.expression.type.meta
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SearchFunctionsTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager
    private lateinit var create: QueryBuilder

    @BeforeTest
    fun setup() {
        create = QueryBuilder()
    }

    @Test
    fun `should support search function on field and string query`() {
        val expected = "SELECT META().`id` FROM `someBucket` WHERE SEARCH(`someBucket`.`stringField`, \"+something\")"

        val actual = create.select(
            meta().id,
        ).from(
            someBucket(),
        ).where(
            fullTextSearch(
                someStringField(bucket = someBucket()),
                "+something",
            ),
        ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search function on bucket with string query`() {
        val expected = "SELECT META().`id` FROM `someBucket` WHERE SEARCH(`someBucket`, \"stringField:\"something\"\")"

        val actual = create.select(
            meta().id,
        ).from(
            someBucket(),
        ).where(
            fullTextSearch(
                someBucket(),
                "stringField:\"something\"",
            ),
        ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search function on field with object query`() {
        val expected = "SELECT META().`id` FROM `someBucket` WHERE SEARCH(`stringField`, {\"match\" : \"something\"})"

        val actual = create.select(
            meta().id,
        ).from(
            someBucket(),
        ).where(
            fullTextSearch(
                someStringField(),
                mapOf(
                    "match" to "something",
                ),
            ),
        ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search function on bucket with object query`() {
        val expected = "SELECT META().`id` FROM `someBucket` WHERE SEARCH(`someBucket`, {\"disjuncts\" : " +
            "[{\"regexp\" : \"(?i).*123.*\", \"field\" : \"someField\"}, " +
            "{\"regexp\" : \"(?i).*123.*\", \"field\" : \"anotherField\"}]}) " +
            "LIMIT 10"

        val actual = create
            .select(
                meta().id,
            ).from(
                someBucket(),
            ).where(
                fullTextSearch(
                    someBucket(),
                    mapOf(
                        "disjuncts" to listOf(
                            mapOf("regexp" to "(?i).*123.*", "field" to "someField"),
                            mapOf("regexp" to "(?i).*123.*", "field" to "anotherField"),
                        ),
                    ),
                ),
            ).limit(10).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search score function on specified out name`() {
        val expected = "SELECT META().`id`, SEARCH_SCORE(`outName`) AS `score` FROM `someBucket` " +
            "WHERE SEARCH(`stringField`, \"+something\", {\"out\" : \"outName\"})"

        val actual = create.select(
            meta().id,
            fullTextSearchScore("outName").alias("score"),
        ).from(
            someBucket(),
        ).where(
            fullTextSearch(
                someStringField(),
                "+something",
                mapOf(
                    "out" to "outName",
                ),
            ),
        ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support search score function on implicit out name`() {
        val expected = "SELECT SEARCH_META() AS `meta` FROM `someBucket` " +
            "WHERE SEARCH(`stringField`, \"+something\")"

        val actual = create.select(
            fullTextSearchMeta().alias("meta"),
        ).from(
            someBucket(),
        ).where(
            fullTextSearch(
                someStringField(),
                "+something",
            ),
        ).build().queryString

        assertEquals(expected, actual)
    }
}
