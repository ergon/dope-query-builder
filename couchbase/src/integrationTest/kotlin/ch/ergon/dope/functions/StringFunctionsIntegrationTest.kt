package ch.ergon.dope.functions

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.integrationTest.BaseIntegrationTest
import ch.ergon.dope.integrationTest.toSingleValue
import ch.ergon.dope.resolvable.expression.type.function.string.concat
import ch.ergon.dope.resolvable.expression.type.function.string.length
import ch.ergon.dope.resolvable.expression.type.function.string.ltrim
import ch.ergon.dope.resolvable.expression.type.function.string.rtrim
import ch.ergon.dope.resolvable.expression.type.function.string.title
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class StringFunctionsIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `use string functions to create a new string`() {
        val dopeQuery = QueryBuilder
            .select(
                "   test".ltrim().concat("string   ".rtrim().title()),
            )
            .offset(
                "".toDopeType().length(),
            ).build(CouchbaseResolver())

        val queryResult = queryWithoutParameters(dopeQuery)
        val result = queryResult.toSingleValue()

        assertEquals("testString", result)
    }
}
