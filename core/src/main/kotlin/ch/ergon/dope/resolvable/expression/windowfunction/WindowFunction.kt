package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.clause.model.OrderType
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

enum class FromModifier(val queryString: String) {
    FIRST("FROM FIRST"),
    LAST("FROM LAST"),
}

enum class NullsOrder(val queryString: String) {
    NULLS_FIRST("NULLS FIRST"),
    NULLS_LAST("NULLS LAST"),
}

enum class NullsModifier(val queryString: String) {
    RESPECT("RESPECT NULLS"),
    IGNORE("IGNORE NULLS"),
}

sealed class WindowFunction(
    private val functionName: String,
    private val windowFunctionArguments: WindowFunctionArguments? = null,
    private val fromModifier: FromModifier? = null,
    private val nullsModifier: NullsModifier? = null,
    private val overClause: OverClause,
) : Expression {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val windowFunctionArgumentsDopeQuery = windowFunctionArguments?.toDopeQuery(manager)
        val overClauseDopeQuery = overClause.toDopeQuery(manager)
        return DopeQuery(
            queryString = functionName +
                (windowFunctionArgumentsDopeQuery?.queryString?.let { " ($it)" } ?: " ()") +
                (fromModifier?.let { " ${it.queryString}" }.orEmpty()) +
                (nullsModifier?.let { " ${it.queryString}" }.orEmpty()) +
                overClauseDopeQuery.queryString.let { " $it" },
            parameters = windowFunctionArgumentsDopeQuery?.parameters?.merge(overClauseDopeQuery.parameters)
                ?: overClauseDopeQuery.parameters,
        )
    }

    fun alias(alias: String): AliasedWindowFunction = AliasedWindowFunction(this, alias)
}

class AliasedWindowFunction(private val windowFunction: WindowFunction, private val alias: String) : Expression {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val windowFunctionDopeQuery = windowFunction.toDopeQuery(manager)
        return DopeQuery(windowFunctionDopeQuery.queryString + " AS `$alias`", windowFunctionDopeQuery.parameters)
    }
}

data class WindowFunctionArguments(
    private val firstArg: UnaliasedExpression<out ValidType>? = null,
    private val secondArg: UnaliasedExpression<out ValidType>? = null,
    private val thirdArg: UnaliasedExpression<out ValidType>? = null,
) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val firstArgDopeQuery = firstArg?.toDopeQuery(manager)
        val secondArgDopeQuery = secondArg?.toDopeQuery(manager)
        val thirdArgDopeQuery = thirdArg?.toDopeQuery(manager)
        return DopeQuery(
            queryString = buildString {
                if (firstArgDopeQuery != null) {
                    append(firstArgDopeQuery.queryString)
                }
                if (secondArgDopeQuery != null) {
                    if (isNotEmpty()) append(", ")
                    append(secondArgDopeQuery.queryString)
                }
                if (thirdArgDopeQuery != null) {
                    if (isNotEmpty()) append(", ")
                    append(thirdArgDopeQuery.queryString)
                }
            },
            parameters = (firstArgDopeQuery?.parameters ?: DopeParameters()).merge(
                secondArgDopeQuery?.parameters,
                thirdArgDopeQuery?.parameters,
            ),
        )
    }
}

sealed interface OverClause : Resolvable

class OverClauseWindowDefinition(private val windowDefinition: WindowDefinition) : OverClause {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val windowDefinitionDopeQuery = windowDefinition.toDopeQuery(manager)
        return DopeQuery(
            queryString = "OVER (${windowDefinitionDopeQuery.queryString})",
            parameters = windowDefinitionDopeQuery.parameters,
        )
    }
}

class OverClauseWindowReference(private val windowReference: String) : OverClause {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        return DopeQuery(
            queryString = "OVER `$windowReference`",
        )
    }
}

class WindowDefinition(
    private val windowReference: UnaliasedExpression<StringType>? = null,
    private val windowPartitionClause: List<UnaliasedExpression<out ValidType>>? = null,
    private val windowOrderClause: List<OrderingTerm>? = null,
    private val windowFrameClause: WindowFrameClause? = null,
) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val windowReferenceDopeQuery = windowReference?.toDopeQuery(manager)
        val windowPartitionClauseDopeQueries = windowPartitionClause?.map { it.toDopeQuery(manager) }
        val windowOrderClauseDopeQueries = windowOrderClause?.map { it.toDopeQuery(manager) }
        val windowFrameClauseDopeQuery = windowFrameClause?.toDopeQuery(manager)

        return DopeQuery(
            queryString = buildString {
                windowReferenceDopeQuery?.queryString?.let {
                    append(it)
                }

                windowPartitionClauseDopeQueries?.takeIf { it.isNotEmpty() }?.let { queries ->
                    if (isNotEmpty()) append(" ")
                    append("PARTITION BY ")
                    append(queries.joinToString(", ") { it.queryString })
                }

                windowOrderClauseDopeQueries?.takeIf { it.isNotEmpty() }?.let { queries ->
                    if (isNotEmpty()) append(" ")
                    append("ORDER BY ")
                    append(queries.joinToString(", ") { it.queryString })
                }

                windowFrameClauseDopeQuery?.queryString?.let {
                    if (isNotEmpty()) append(" ")
                    append(it)
                }
            },
        )
    }
}

class WindowFrameClause(
    private val windowFrameType: WindowFrameType,
    private val windowFrameExtent: WindowFrameExtent,
    private val windowFrameExclusion: WindowFrameExclusion? = null,
) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val windowFrameExtentDopeQuery = windowFrameExtent.toDopeQuery(manager)
        return DopeQuery(
            queryString = "$windowFrameType " +
                "${windowFrameExtentDopeQuery.queryString}${windowFrameExclusion?.let { " ${it.queryString}" }.orEmpty()}",
            parameters = windowFrameExtentDopeQuery.parameters,
        )
    }
}

interface WindowFrameExtent : Resolvable

interface FrameBetween : Resolvable

interface FrameAndBetween : Resolvable

class Between(private val between: FrameBetween, private val and: FrameAndBetween) : WindowFrameExtent {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val betweenDopeQuery = between.toDopeQuery(manager)
        val andDopeQuery = and.toDopeQuery(manager)
        return DopeQuery(
            "BETWEEN ${betweenDopeQuery.queryString} AND ${andDopeQuery.queryString}",
            betweenDopeQuery.parameters.merge(andDopeQuery.parameters),
        )
    }
}

class UnboundedFollowing : FrameAndBetween {
    override fun toDopeQuery(manager: DopeQueryManager) = DopeQuery("UNBOUNDED FOLLOWING")
}

class UnboundedPreceding : FrameBetween, WindowFrameExtent {
    override fun toDopeQuery(manager: DopeQueryManager) = DopeQuery("UNBOUNDED PRECEDING")
}

class CurrentRow : FrameBetween, FrameAndBetween, WindowFrameExtent {
    override fun toDopeQuery(manager: DopeQueryManager) = DopeQuery("CURRENT ROW")
}

class Following(val offset: UnaliasedExpression<NumberType>) : FrameBetween, FrameAndBetween {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val offsetDopeQuery = offset.toDopeQuery(manager)
        return DopeQuery("${offsetDopeQuery.queryString} FOLLOWING", offsetDopeQuery.parameters)
    }
}

class Preceding(val offset: UnaliasedExpression<NumberType>) : FrameBetween, FrameAndBetween, WindowFrameExtent {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val offsetDopeQuery = offset.toDopeQuery(manager)
        return DopeQuery("${offsetDopeQuery.queryString} PRECEDING", offsetDopeQuery.parameters)
    }
}

enum class WindowFrameExclusion(val queryString: String) {
    EXCLUDE_CURRENT_ROW("EXCLUDE CURRENT ROW"),
    EXCLUDE_GROUP("EXCLUDE GROUP"),
    EXCLUDE_TIES("EXCLUDE TIES"),
    EXCLUDE_NO_OTHERS("EXCLUDE NO OTHERS"),
}

enum class WindowFrameType {
    ROWS,
    RANGE,
    GROUPS,
}

class OrderingTerm(
    private val expression: Expression,
    private val orderType: OrderType? = null,
    private val nullsOrder: NullsOrder? = null,
) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val expressionDopeQuery = expression.toDopeQuery(manager)
        return DopeQuery(
            queryString = expressionDopeQuery.queryString +
                (orderType?.let { " $it" }.orEmpty()) +
                (nullsOrder?.let { " ${it.queryString}" }.orEmpty()),
            parameters = expressionDopeQuery.parameters,
        )
    }
}
