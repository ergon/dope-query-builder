package ch.ergon.dope.functions

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.integrationTest.BaseIntegrationTest
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.testBucket
import ch.ergon.dope.integrationTest.toMapValues
import ch.ergon.dope.resolvable.expression.type.function.search.fullTextSearch
import ch.ergon.dope.resolvable.expression.type.meta
import kotlin.test.Test
import kotlin.test.assertEquals

class SearchFunctionsIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `full text search type field`() {
        val dopeQuery = QueryBuilder.select(
            meta().id,
        ).from(
            testBucket,
        ).where(
            fullTextSearch(
                testBucket,
                mapOf(
                    "query" to mapOf(
                        "field" to "type",
                        "match" to "order",
                    ),
                ),
            ),
        ).build()

        val queryResult = queryWithoutParameters(dopeQuery)

        assertEquals(5, queryResult.rows.size)
        assertEquals(mapOf("id" to "order:1"), queryResult.toMapValues(rowNumber = 0))
        assertEquals(mapOf("id" to "order:2"), queryResult.toMapValues(rowNumber = 1))
        assertEquals(mapOf("id" to "order:3"), queryResult.toMapValues(rowNumber = 2))
        assertEquals(mapOf("id" to "order:4"), queryResult.toMapValues(rowNumber = 3))
        assertEquals(mapOf("id" to "order:5"), queryResult.toMapValues(rowNumber = 4))
    }
}
