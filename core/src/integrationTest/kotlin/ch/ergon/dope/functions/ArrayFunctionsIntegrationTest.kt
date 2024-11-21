package ch.ergon.dope.functions

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.integrationTest.BaseIntegrationTest
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.testBucket
import ch.ergon.dope.integrationTest.toRawValues
import ch.ergon.dope.integrationTest.toSingleValue
import ch.ergon.dope.resolvable.expression.unaliased.type.access.get
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.sub
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayAppend
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayAverage
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayContains
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayCount
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayLength
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayMin
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayPut
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayRange
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arrayRepeat
import ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction.arraySort
import ch.ergon.dope.resolvable.expression.unaliased.type.logical.and
import ch.ergon.dope.resolvable.expression.unaliased.type.meta.meta
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.fromable.useKeys
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayFunctionsIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `use number array functions arithmetically`() {
        val array = listOf(1.toDopeType(), 2.toDopeType(), 3.toDopeType()).toDopeType()
        val dopeQuery = QueryBuilder()
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
        val dopeQuery = QueryBuilder()
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
            ).build()

        val queryResult = queryWithoutParameters(dopeQuery)
        val result = queryResult.toRawValues(rowNumber = 2)

        assertEquals("employee:3", result)
    }
}
