package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.clause.model.OrderType.ASC
import ch.ergon.dope.resolvable.clause.model.asWindowDeclaration
import ch.ergon.dope.resolvable.expression.rowscope.alias
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.Between
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.CurrentRow
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.Following
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.NullsOrder.NULLS_FIRST
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.NullsOrder.NULLS_LAST
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OrderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.UnboundedFollowing
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowFrameClause
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowFrameExclusion.EXCLUDE_NO_OTHERS
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowFrameType.ROWS
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.FromModifier.LAST
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.NullsModifier.IGNORE
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.cumeDist
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.denseRank
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.firstValue
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.lag
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.lastValue
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.nthValue
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.rowNumber
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class WindowFunctionTest {
    private lateinit var create: QueryBuilder

    @BeforeTest
    fun setup() {
        create = QueryBuilder()
    }

    @Test
    fun `should support window functions`() {
        val expected = "SELECT ROW_NUMBER() OVER () AS `row`, " +
            "CUME_DIST() OVER `ref`, " +
            "DENSE_RANK() OVER (ORDER BY `stringField` ASC), " +
            "FIRST_VALUE(`stringField`) OVER (ORDER BY `stringField` NULLS LAST ROWS BETWEEN CURRENT ROW AND 1 FOLLOWING EXCLUDE NO OTHERS), " +
            "NTH_VALUE(`numberField`, 10) FROM LAST OVER (ORDER BY `stringField` NULLS FIRST), " +
            "LAG(`numberField`) OVER (ORDER BY `stringField` ASC), " +
            "LAST_VALUE(`last`) IGNORE NULLS OVER () " +
            "FROM `someBucket` " +
            "WINDOW `ref` AS (ROWS BETWEEN CURRENT ROW AND UNBOUNDED FOLLOWING)"

        val actual = create
            .select(
                rowNumber().alias("row"),
                cumeDist("ref"),
                denseRank(listOf(OrderingTerm(someStringField(), ASC))),
                firstValue(
                    someStringField(),
                    windowOrderClause = listOf(OrderingTerm(someStringField(), nullsOrder = NULLS_LAST)),
                    windowFrameClause = WindowFrameClause(
                        ROWS,
                        Between(CurrentRow(), Following(1)),
                        EXCLUDE_NO_OTHERS,
                    ),
                ),
                nthValue(
                    someNumberField(),
                    10,
                    fromModifier = LAST,
                    windowOrderClause = listOf(
                        OrderingTerm(
                            someStringField(),
                            nullsOrder = NULLS_FIRST,
                        ),
                    ),
                ),
                lag(someNumberField(), windowOrderClause = listOf(OrderingTerm(someStringField(), ASC))),
                lastValue(
                    someStringField("last"),
                    IGNORE,
                ),
            )
            .from(someBucket())
            .referenceWindow(
                "ref".asWindowDeclaration(
                    WindowDefinition(
                        windowFrameClause = WindowFrameClause(
                            ROWS,
                            Between(CurrentRow(), UnboundedFollowing()),
                        ),
                    ),
                ),
            )
            .build().queryString

        assertEquals(expected, actual)
    }
}
