package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.clause.model.OrderType.ASC
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.expression.windowfunction.Between
import ch.ergon.dope.resolvable.expression.windowfunction.CurrentRow
import ch.ergon.dope.resolvable.expression.windowfunction.Following
import ch.ergon.dope.resolvable.expression.windowfunction.FromModifier.LAST
import ch.ergon.dope.resolvable.expression.windowfunction.NullsModifier.IGNORE
import ch.ergon.dope.resolvable.expression.windowfunction.NullsOrder.NULLS_FIRST
import ch.ergon.dope.resolvable.expression.windowfunction.NullsOrder.NULLS_LAST
import ch.ergon.dope.resolvable.expression.windowfunction.OrderingTerm
import ch.ergon.dope.resolvable.expression.windowfunction.WindowFrameClause
import ch.ergon.dope.resolvable.expression.windowfunction.WindowFrameExclusion.EXCLUDE_NO_OTHERS
import ch.ergon.dope.resolvable.expression.windowfunction.WindowFrameType.ROWS
import ch.ergon.dope.resolvable.expression.windowfunction.cumeDist
import ch.ergon.dope.resolvable.expression.windowfunction.denseRank
import ch.ergon.dope.resolvable.expression.windowfunction.firstValue
import ch.ergon.dope.resolvable.expression.windowfunction.lag
import ch.ergon.dope.resolvable.expression.windowfunction.lastValue
import ch.ergon.dope.resolvable.expression.windowfunction.nthValue
import ch.ergon.dope.resolvable.expression.windowfunction.rowNumber
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
        val expected = "SELECT ROW_NUMBER () OVER () AS `row`, " +
            "CUME_DIST () OVER `ref`, " +
            "DENSE_RANK () OVER (ORDER BY `stringField` ASC), " +
            "FIRST_VALUE (`stringField`) OVER (ORDER BY `stringField` NULLS LAST ROWS BETWEEN CURRENT ROW AND 1 FOLLOWING EXCLUDE NO OTHERS), " +
            "NTH_VALUE (`numberField`, 10) FROM LAST OVER (ORDER BY `stringField` NULLS FIRST), " +
            "LAG (`numberField`) OVER (ORDER BY `stringField` ASC), " +
            "LAST_VALUE (`last`) IGNORE NULLS OVER () " +
            "FROM `someBucket`"

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
                        Between(CurrentRow(), Following(1.toDopeType())),
                        EXCLUDE_NO_OTHERS,
                    ),
                ),
                nthValue(
                    someNumberField(),
                    10.toDopeType(),
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
            ).from(someBucket()).build().queryString

        assertEquals(expected, actual)
    }
}
