package ch.ergon.dope.functions

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.couchbase.resolvable.expression.type.meta
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.integrationTest.BaseIntegrationTest
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.testKeyspace
import ch.ergon.dope.integrationTest.toRawValues
import ch.ergon.dope.integrationTest.toSingleValue
import ch.ergon.dope.resolvable.expression.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.type.arithmetic.sub
import ch.ergon.dope.resolvable.expression.type.function.array.append
import ch.ergon.dope.resolvable.expression.type.function.array.arrayRange
import ch.ergon.dope.resolvable.expression.type.function.array.arrayRepeat
import ch.ergon.dope.resolvable.expression.type.function.array.average
import ch.ergon.dope.resolvable.expression.type.function.array.contains
import ch.ergon.dope.resolvable.expression.type.function.array.count
import ch.ergon.dope.resolvable.expression.type.function.array.length
import ch.ergon.dope.resolvable.expression.type.function.array.min
import ch.ergon.dope.resolvable.expression.type.function.array.put
import ch.ergon.dope.resolvable.expression.type.function.array.sort
import ch.ergon.dope.resolvable.expression.type.get
import ch.ergon.dope.resolvable.expression.type.logic.and
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.resolvable.keyspace.useKeys
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayFunctionsIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `use number array functions arithmetically`() {
        val array = listOf(1.toDopeType(), 2.toDopeType(), 3.toDopeType()).toDopeType()
        val dopeQuery = QueryBuilder
            .select(
                array.count().add(array.length()).sub(array.average()),
            )
            .where(
                array.contains(array.min()).and(arrayRange(1, 4).isEqualTo(array)),
            )
            .limit(
                array.get(0),
            ).build(CouchbaseResolver())

        val queryResult = queryWithoutParameters(dopeQuery)
        val result = queryResult.toSingleValue()

        assertEquals(4, result)
    }

    @Test
    fun `use nested array functions`() {
        val keys = listOf("employee:1".toDopeType(), "employee:2".toDopeType(), "employee:3".toDopeType()).toDopeType()
        val dopeQuery = QueryBuilder
            .selectRaw(
                meta().id,
            )
            .from(
                testKeyspace.useKeys(
                    keys.append(
                        "employee:5",
                    ).put(
                        "employee:1",
                        "employee:4",
                    ).sort(),
                ),
            )
            .where(
                arrayRange(1, 11).count().isEqualTo(
                    arrayRepeat("value".toDopeType(), 10).length(),
                ),
            ).orderBy(
                meta().id,
            )
            .build(CouchbaseResolver())

        val queryResult = queryWithoutParameters(dopeQuery)

        assertEquals("employee:1", queryResult.toRawValues(rowNumber = 0))
        assertEquals("employee:2", queryResult.toRawValues(rowNumber = 1))
        assertEquals("employee:3", queryResult.toRawValues(rowNumber = 2))
        assertEquals("employee:4", queryResult.toRawValues(rowNumber = 3))
        assertEquals("employee:5", queryResult.toRawValues(rowNumber = 4))
    }
}
