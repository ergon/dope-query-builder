package ch.ergon.dope.functions

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.integrationTest.BaseIntegrationTest
import ch.ergon.dope.integrationTest.toSingleValue
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.concat
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.length
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.ltrim
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.rtrim
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.title
import kotlin.test.Test
import kotlin.test.assertEquals

class StringFunctionsIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `use string functions to create a new string`() {
        val dopeQuery = QueryBuilder()
            .select(
                concat(ltrim("   test"), title(rtrim("string   "))),
            )
            .offset(
                length(""),
            ).build()

        val queryResult = queryWithoutParameters(dopeQuery)
        val result = queryResult.toSingleValue()

        assertEquals("testString", result)
    }
}
