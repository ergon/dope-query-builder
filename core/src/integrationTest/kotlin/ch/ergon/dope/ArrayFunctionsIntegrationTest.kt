package ch.ergon.dope

import ch.ergon.dope.helper.BaseIntegrationTest
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.sub
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayAppend
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayAverage
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayCount
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayLength
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayReverse
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arraySort
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayFunctionsIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `use number array functions in number functions`() {
        val array = listOf(1.toDopeType(), 2.toDopeType(), 3.toDopeType()).toDopeType()
        val dopeQuery = QueryBuilder()
            .select(
                arrayCount(array).add(arrayLength(array)).sub(arrayAverage(array)),
            ).build()

        val actualQueryResult = queryWithoutParameters(dopeQuery)
        val actual = actualQueryResult.valueAs<Number>()

        assertEquals((3 + 3 - 2), actual)
    }

    @Test
    fun `use nested array functions`() {
        val array = listOf("value1".toDopeType(), "value2".toDopeType(), "value3".toDopeType()).toDopeType()
        val dopeQuery = QueryBuilder()
            .select(
                arraySort(arrayReverse(arrayAppend(array, "value4", "value5"))),
            ).build()

        val actualQueryResult = queryWithoutParameters(dopeQuery)
        val actual = actualQueryResult.valueAs<List<String>>()

        assertEquals(listOf("value1", "value2", "value3", "value4", "value5"), actual)
    }
}
