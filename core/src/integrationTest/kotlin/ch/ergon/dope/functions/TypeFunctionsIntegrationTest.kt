package ch.ergon.dope.functions

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.integrationTest.BaseIntegrationTest
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.isNumber
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.toBool
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.toNumber
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.and
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.not
import kotlin.test.Test
import kotlin.test.assertEquals

class TypeFunctionsIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `return type conversions of type functions`() {
        val dopeQuery = QueryBuilder()
            .select(
                not("".toBool()).and("3!".toNumber("!").isNumber()),
            ).build()

        val actual = queryWithoutParameters(dopeQuery)
        val actualQueryResult = actual.valueAs<Boolean>()

        assertEquals(true, actualQueryResult)
    }
}
