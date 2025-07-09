package ch.ergon.dope.functions

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.integrationTest.BaseIntegrationTest
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.testBucket
import ch.ergon.dope.integrationTest.toRawValues
import ch.ergon.dope.integrationTest.toSingleValue
import ch.ergon.dope.resolvable.bucket.useKeys
import ch.ergon.dope.resolvable.expression.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.type.arithmetic.sub
import ch.ergon.dope.resolvable.expression.type.function.array.arrayAppend
import ch.ergon.dope.resolvable.expression.type.function.array.arrayAverage
import ch.ergon.dope.resolvable.expression.type.function.array.arrayContains
import ch.ergon.dope.resolvable.expression.type.function.array.arrayCount
import ch.ergon.dope.resolvable.expression.type.function.array.arrayLength
import ch.ergon.dope.resolvable.expression.type.function.array.arrayMin
import ch.ergon.dope.resolvable.expression.type.function.array.arrayPut
import ch.ergon.dope.resolvable.expression.type.function.array.arrayRange
import ch.ergon.dope.resolvable.expression.type.function.array.arrayRepeat
import ch.ergon.dope.resolvable.expression.type.function.array.arraySort
import ch.ergon.dope.resolvable.expression.type.get
import ch.ergon.dope.resolvable.expression.type.logic.and
import ch.ergon.dope.resolvable.expression.type.meta
import ch.ergon.dope.resolvable.expression.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayFunctionsIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `use number array functions arithmetically`() {
        val array = listOf(1.toDopeType(), 2.toDopeType(), 3.toDopeType()).toDopeType()
        val dopeQuery = QueryBuilder
            .select(
                arrayCount(array).add(arrayLength(array)).sub(arrayAverage(array)),
            )
            .where(
                arrayContains(array, arrayMin(array)).and(arrayRange(1, 4).isEqualTo(array)),
            )
            .limit(
                array.get(0),
            ).build()

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
                testBucket.useKeys(
                    arraySort(
                        arrayPut(
                            arrayAppend(
                                keys,
                                "employee:5",
                            ),
                            "employee:1",
                            "employee:4",
                        ),
                    ),
                ),
            )
            .where(
                arrayCount(
                    arrayRange(1, 11),
                ).isEqualTo(
                    arrayLength(
                        arrayRepeat("value".toDopeType(), 10),
                    ),
                ),
            ).orderBy(
                meta().id,
            )
            .build()

        val queryResult = queryWithoutParameters(dopeQuery)

        assertEquals("employee:1", queryResult.toRawValues(rowNumber = 0))
        assertEquals("employee:2", queryResult.toRawValues(rowNumber = 1))
        assertEquals("employee:3", queryResult.toRawValues(rowNumber = 2))
        assertEquals("employee:4", queryResult.toRawValues(rowNumber = 3))
        assertEquals("employee:5", queryResult.toRawValues(rowNumber = 4))
    }
}
