package ch.ergon.dope

import ch.ergon.dope.helper.BaseIntegrationTest
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.concat
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
            ).build()

        val actual = queryWithoutParameters(dopeQuery)
        val actualQueryResult = actual.valueAs<String>()

        assertEquals("testString", actualQueryResult)
    }
}
