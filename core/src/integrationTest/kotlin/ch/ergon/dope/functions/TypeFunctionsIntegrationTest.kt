package ch.ergon.dope.functions

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.integrationTest.BaseIntegrationTest
import ch.ergon.dope.integrationTest.toSingleValue
import ch.ergon.dope.resolvable.expression.type.function.type.isNumber
import ch.ergon.dope.resolvable.expression.type.function.type.toBool
import ch.ergon.dope.resolvable.expression.type.function.type.toNumber
import ch.ergon.dope.resolvable.expression.type.logic.and
import ch.ergon.dope.resolvable.expression.type.logic.not
import kotlin.test.Test
import kotlin.test.assertEquals

class TypeFunctionsIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `return type conversions of type functions`() {
        val dopeQuery = QueryBuilder()
            .select(
                not("".toBool()).and("3!".toNumber("!").isNumber()),
            ).build()

        val queryResult = queryWithoutParameters(dopeQuery)
        val result = queryResult.toSingleValue()

        assertEquals(true, result)
    }
}
