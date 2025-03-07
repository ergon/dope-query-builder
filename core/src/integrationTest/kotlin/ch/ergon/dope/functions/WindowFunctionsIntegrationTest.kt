package ch.ergon.dope.functions

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.integrationTest.BaseIntegrationTest
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.deliveryDateField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.idField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.nameField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.orderNumberField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.testBucket
import ch.ergon.dope.integrationTest.toMapValues
import ch.ergon.dope.resolvable.clause.model.OrderType.ASC
import ch.ergon.dope.resolvable.clause.model.OrderType.DESC
import ch.ergon.dope.resolvable.clause.model.asWindowDeclaration
import ch.ergon.dope.resolvable.expression.rowscope.alias
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.Between
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.CurrentRow
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.Following
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.FromModifier.LAST
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.NullsModifier.IGNORE
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.NullsOrder.NULLS_FIRST
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.NullsOrder.NULLS_LAST
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.OrderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.WindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.WindowFrameClause
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.WindowFrameExclusion.EXCLUDE_NO_OTHERS
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.WindowFrameType.ROWS
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.cumeDist
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.denseRank
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.firstValue
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.lag
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.lastValue
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.nthValue
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.rowNumber
import kotlin.test.Test
import kotlin.test.assertEquals

class WindowFunctionsIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `select window functions`() {
        val windowReference = "ref".asWindowDeclaration(
            WindowDefinition(
                windowPartitionClause = listOf(nameField),
                windowOrderClause = listOf(OrderingTerm(idField, DESC)),
            ),
        )
        val rowNumber = "rowNumber"
        val cumeDist = "cumeDist"
        val denseRank = "denseRank"
        val firstValue = "firstValue"
        val nthValue = "nthValue"
        val lag = "lag"
        val lastValue = "lastValue"
        val dopeQuery = QueryBuilder()
            .select(
                rowNumber().alias(rowNumber),
                cumeDist(windowReference.reference).alias(cumeDist),
                denseRank(listOf(OrderingTerm(nameField, ASC))).alias(denseRank),
                firstValue(
                    orderNumberField,
                    windowOrderClause = listOf(OrderingTerm(nameField, nullsOrder = NULLS_LAST)),
                    windowFrameClause = WindowFrameClause(
                        ROWS,
                        Between(CurrentRow(), Following(1)),
                        EXCLUDE_NO_OTHERS,
                    ),
                ).alias(firstValue),
                nthValue(
                    idField,
                    10,
                    fromModifier = LAST,
                    windowOrderClause = listOf(
                        OrderingTerm(
                            nameField,
                            nullsOrder = NULLS_FIRST,
                        ),
                    ),
                ).alias(nthValue),
                lag(idField, windowOrderClause = listOf(OrderingTerm(deliveryDateField, ASC))).alias(lag),
                lastValue(nameField, IGNORE).alias(lastValue),
            )
            .from(
                testBucket,
            )
            .windowReference(windowReference)
            .orderBy(nameField)
            .thenOrderBy(idField)
            .build()

        val queryResult = queryWithoutParameters(dopeQuery)
        val result = queryResult.toMapValues()

        assertEquals(15, queryResult.rows.size)
        assertEquals(result[rowNumber], 11)
        assertEquals(result[cumeDist], 1)
        assertEquals(result[denseRank], 1)
        assertEquals(result[firstValue], "order1")
        assertEquals(result[nthValue], 1)
        assertEquals(result[lag], 5)
        assertEquals(result[lastValue], "employee5")
    }
}
