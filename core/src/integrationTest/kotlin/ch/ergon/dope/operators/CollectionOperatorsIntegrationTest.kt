package ch.ergon.dope.operators

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.integrationTest.BaseIntegrationTest
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.idField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.orderNumberField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.quantitiesField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.testBucket
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.typeField
import ch.ergon.dope.integrationTest.toSingleValue
import ch.ergon.dope.resolvable.expression.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.type.arithmetic.mul
import ch.ergon.dope.resolvable.expression.type.arithmetic.sub
import ch.ergon.dope.resolvable.expression.type.collection.every
import ch.ergon.dope.resolvable.expression.type.collection.exists
import ch.ergon.dope.resolvable.expression.type.function.string.concat
import ch.ergon.dope.resolvable.expression.type.function.type.toStr
import ch.ergon.dope.resolvable.expression.type.logic.and
import ch.ergon.dope.resolvable.expression.type.range.filterIndexed
import ch.ergon.dope.resolvable.expression.type.range.mapIndexed
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.relational.isLessThan
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class CollectionOperatorsIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `use range predicates and transformations`() {
        val array = listOf(1.toDopeType(), 2.toDopeType(), 3.toDopeType()).toDopeType()
        val dopeQuery = QueryBuilder
            .select(
                array.filterIndexed { i, value -> exists(array).and(array.every { value.sub(1).isEqualTo(i) }) }
                    .map { i, value -> value.mul(i.add(1)) },
            ).build()

        val queryResult = queryWithoutParameters(dopeQuery)
        val result = queryResult.toSingleValue()

        assertEquals(listOf(1, 4, 9), result)
    }

    @Test
    fun `use range transformations to create an object`() {
        val dopeQuery = QueryBuilder
            .select(
                quantitiesField.mapIndexed(indexName = "i", iteratorName = "it") { _, it -> it.toStr() }
                    .toObject { i, _ -> concat(orderNumberField, "-", i.toStr()) },
            ).from(
                testBucket,
            ).where(
                typeField.isEqualTo("order").and(idField.isLessThan(2)),
            ).build()

        val queryResult = queryWithoutParameters(dopeQuery)
        val result = queryResult.toSingleValue()

        assertEquals(mapOf("order1-0" to "1", "order1-1" to "2", "order1-2" to "3"), result)
    }
}
