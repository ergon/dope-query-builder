package ch.ergon.dope.functions

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.integrationTest.BaseIntegrationTest
import ch.ergon.dope.integrationTest.toMapValues
import ch.ergon.dope.resolvable.expression.alias
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.sub
import ch.ergon.dope.resolvable.expression.unaliased.type.function.numeric.abs
import ch.ergon.dope.resolvable.expression.unaliased.type.function.numeric.ceil
import ch.ergon.dope.resolvable.expression.unaliased.type.function.numeric.pi
import ch.ergon.dope.resolvable.expression.unaliased.type.function.numeric.power
import ch.ergon.dope.resolvable.expression.unaliased.type.function.numeric.sqrt
import ch.ergon.dope.resolvable.expression.unaliased.type.function.numeric.trunc
import kotlin.test.Test
import kotlin.test.assertEquals

class NumberFunctionsIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `use number functions arithmetically`() {
        val dopeQuery = QueryBuilder()
            .select(
                abs(-1).add(ceil(3.14)).sub(sqrt(9)).alias("arithmetic"),
                trunc(pi(), 4).alias("pi"),
                power(2, 3).alias("power"),
            ).build()

        val queryResult = queryWithoutParameters(dopeQuery)
        val result = queryResult.toMapValues()

        assertEquals(2, result["arithmetic"])
        assertEquals(3.1415, result["pi"])
        assertEquals(8, result["power"])
    }
}
