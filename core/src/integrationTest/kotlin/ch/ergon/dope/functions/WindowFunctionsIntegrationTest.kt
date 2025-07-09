package ch.ergon.dope.functions

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.integrationTest.BaseIntegrationTest
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.deliveryDateField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.idField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.nameField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.orderNumberField
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.testBucket
import ch.ergon.dope.integrationTest.toMapValues
import ch.ergon.dope.integrationTest.tryUntil
import ch.ergon.dope.resolvable.clause.model.OrderType.ASC
import ch.ergon.dope.resolvable.clause.model.OrderType.DESC
import ch.ergon.dope.resolvable.clause.model.asWindowDeclaration
import ch.ergon.dope.resolvable.expression.rowscope.alias
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.NullsOrder.NULLS_FIRST
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.NullsOrder.NULLS_LAST
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OrderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowFrameClause
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowFrameExclusion.EXCLUDE_NO_OTHERS
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowFrameType.ROWS
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.between
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.currentRow
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.following
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.orderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.FromModifier.LAST
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.NullsModifier.IGNORE
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.cumeDist
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.denseRank
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.firstValue
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.lag
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.lastValue
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.nthValue
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.rowNumber
import junit.framework.TestCase.assertNotNull
import kotlin.test.Test
import kotlin.test.assertEquals

class WindowFunctionsIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `select window functions`() {
        val windowReference = "ref".asWindowDeclaration(
            WindowDefinition(
                windowPartitionClause = listOf(nameField),
                windowOrderClause = listOf(
                    OrderingTerm(idField, DESC),
                    OrderingTerm(nameField, ASC),
                ),
            ),
        )

        val dopeQuery = QueryBuilder
            .select(
                rowNumber().alias("rowNumber"),
                cumeDist(windowReference.reference).alias("cumeDist"),
                denseRank(listOf(orderingTerm(nameField, ASC))).alias("denseRank"),
                firstValue(
                    orderNumberField,
                    windowOrderClause = listOf(orderingTerm(nameField, nullsOrder = NULLS_LAST)),
                    windowFrameClause = WindowFrameClause(
                        ROWS,
                        between(currentRow(), following(1)),
                        EXCLUDE_NO_OTHERS,
                    ),
                ).alias("firstValue"),
                nthValue(
                    idField,
                    10,
                    fromModifier = LAST,
                    windowOrderClause = listOf(
                        orderingTerm(
                            nameField,
                            nullsOrder = NULLS_FIRST,
                        ),
                    ),
                ).alias("nthValue"),
                lag(idField, windowOrderClause = listOf(orderingTerm(deliveryDateField, ASC)))
                    .alias("lag"),
                lastValue(nameField, IGNORE).alias("lastValue"),
            )
            .from(testBucket)
            .referenceWindow(windowReference)
            .orderBy(nameField)
            .thenOrderBy(idField)
            .build()

        tryUntil {
            val queryResult = queryWithoutParameters(dopeQuery)
            val result = queryResult.toMapValues()

            assertEquals(15, queryResult.rows.size)
            assertNotNull(result["rowNumber"])
            assertNotNull(result["cumeDist"])
            assertNotNull(result["denseRank"])
            assertNotNull(result["firstValue"])
            assertNotNull(result["nthValue"])
            assertNotNull(result["lag"])
            assertNotNull(result["lastValue"])
        }
    }
}
