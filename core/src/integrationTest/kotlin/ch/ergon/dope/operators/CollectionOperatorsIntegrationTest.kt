package ch.ergon.dope.operators

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.integrationTest.BaseIntegrationTest
import ch.ergon.dope.integrationTest.toSingleValue
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.sub
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.every
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.exists
import ch.ergon.dope.resolvable.expression.unaliased.type.collection.filter
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.and
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class CollectionOperatorsIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `use range predicates and transformations`() {
        val array = listOf(1.toDopeType(), 2.toDopeType(), 3.toDopeType()).toDopeType()
        val dopeQuery = QueryBuilder()
            .select(
                array.filter { value, i -> exists(array).and(array.every { value.sub(1).isEqualTo(i) }) }
                    .mapIndexed { value, _ -> value },
            ).build()

        val queryResult = queryWithoutParameters(dopeQuery)
        val result = queryResult.toSingleValue()

        assertEquals(listOf(1, 2, 3), result)
    }
}
