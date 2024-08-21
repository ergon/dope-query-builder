package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.DopeQuery
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
    override fun toDopeQuery(): DopeQuery {
        val windowFunctionArgumentsDopeQuery = windowFunctionArguments?.toDopeQuery()
        val overClauseDopeQuery = overClause.toDopeQuery()
        return DopeQuery(
            queryString = functionName +
                (windowFunctionArgumentsDopeQuery?.queryString?.let { " ($it)" } ?: " ()") +
                (fromModifier?.let { " ${it.queryString}" }.orEmpty()) +
                (nullsModifier?.let { " ${it.queryString}" }.orEmpty()) +
                overClauseDopeQuery.queryString.let { " $it" },
            parameters = windowFunctionArgumentsDopeQuery?.parameters.orEmpty() + overClauseDopeQuery.parameters,
        )
    }

    fun alias(alias: String): AliasedWindowFunction = AliasedWindowFunction(this, alias)
}

class AliasedWindowFunction(private val windowFunction: WindowFunction, private val alias: String) : Expression {
    override fun toDopeQuery(): DopeQuery {
        val windowFunctionDopeQuery = windowFunction.toDopeQuery()
        return DopeQuery(windowFunctionDopeQuery.queryString + " AS `$alias`", windowFunctionDopeQuery.parameters)
    }
}

data class WindowFunctionArguments(
    private val firstArg: UnaliasedExpression<out ValidType>? = null,
    private val secondArg: UnaliasedExpression<out ValidType>? = null,
    private val thirdArg: UnaliasedExpression<out ValidType>? = null,
) : Resolvable {
    override fun toDopeQuery(): DopeQuery {
        val firstArgDopeQuery = firstArg?.toDopeQuery()
        val secondArgDopeQuery = secondArg?.toDopeQuery()
        val thirdArgDopeQuery = thirdArg?.toDopeQuery()
        return DopeQuery(
            queryString = firstArgDopeQuery?.let { first ->
                secondArgDopeQuery?.let { second ->
                    thirdArgDopeQuery?.let { third ->
                        "${first.queryString}, ${second.queryString}, ${third.queryString}"
                    } ?: "${first.queryString}, ${second.queryString}"
                } ?: first.queryString
            }.orEmpty(),
            parameters = firstArgDopeQuery?.parameters.orEmpty() +
                secondArgDopeQuery?.parameters.orEmpty() +
                thirdArgDopeQuery?.parameters.orEmpty(),
        )
    }
}

sealed interface OverClause : Resolvable

class OverClauseWindowDefinition(private val windowDefinition: WindowDefinition) : OverClause {
    override fun toDopeQuery(): DopeQuery {
        val windowDefinitionDopeQuery = windowDefinition.toDopeQuery()
        return DopeQuery(
            queryString = "OVER (${windowDefinitionDopeQuery.queryString})",
            parameters = windowDefinitionDopeQuery.parameters,
        )
    }
}

class OverClauseWindowReference(private val windowReference: String) : OverClause {
    override fun toDopeQuery(): DopeQuery {
        return DopeQuery(
            queryString = "OVER `$windowReference`",
            parameters = emptyMap(),
        )
    }
}

class WindowDefinition(
    private val windowReference: UnaliasedExpression<StringType>? = null,
    private val windowPartitionClause: List<UnaliasedExpression<out ValidType>>? = null,
    private val windowOrderClause: List<OrderingTerm>? = null,
    private val windowFrameClause: WindowFrameClause? = null,
) : Resolvable {
    override fun toDopeQuery(): DopeQuery {
        val windowReferenceDopeQuery = windowReference?.toDopeQuery()
        val windowPartitionClauseDopeQueries = windowPartitionClause?.map { it.toDopeQuery() }
        val windowOrderClauseDopeQueries = windowOrderClause?.map { it.toDopeQuery() }
        val windowFrameClauseDopeQuery = windowFrameClause?.toDopeQuery()

        return DopeQuery(
            queryString = buildString {
                windowReferenceDopeQuery?.queryString?.let {
                    append(" $it")
                }

                windowPartitionClauseDopeQueries?.takeIf { it.isNotEmpty() }?.let { queries ->
                    append(" PARTITION BY ")
                    append(queries.joinToString(", ") { it.queryString })
                }

                windowOrderClauseDopeQueries?.takeIf { it.isNotEmpty() }?.let { queries ->
                    append(" ORDER BY ")
                    append(queries.joinToString(", ") { it.queryString })
                }

                windowFrameClauseDopeQuery?.queryString?.let {
                    append(" $it")
                }
            },
            parameters = emptyMap(),
        )
    }
}

class WindowFrameClause(
    private val windowFrameType: WindowFrameType,
    private val windowFrameExtent: WindowFrameExtent,
    private val windowFrameExclusion: WindowFrameExclusion? = null,
) : Resolvable {
    override fun toDopeQuery(): DopeQuery {
        val windowFrameExtentDopeQuery = windowFrameExtent.toDopeQuery()
        return DopeQuery(
            queryString = "$windowFrameType " +
                "${windowFrameExtentDopeQuery.queryString}${windowFrameExclusion?.let { " ${it.queryString}" }.orEmpty()}",
            parameters = windowFrameExtentDopeQuery.parameters,
        )
    }
}

sealed class WindowFrameExtent : Resolvable {
    data object UnboundedPreceding : WindowFrameExtent()
    class Preceding(val offset: UnaliasedExpression<NumberType>) : WindowFrameExtent()
    data object CurrentRow : WindowFrameExtent()
    class Between(val between: FrameBetween, val and: FrameBetween) : WindowFrameExtent()

    override fun toDopeQuery(): DopeQuery {
        return when (this) {
            is UnboundedPreceding -> DopeQuery("UNBOUNDED PRECEDING", emptyMap())
            is Preceding -> {
                val offsetDopeQuery = offset.toDopeQuery()
                DopeQuery("${offsetDopeQuery.queryString} PRECEDING", offsetDopeQuery.parameters)
            }

            is CurrentRow -> DopeQuery("CURRENT ROW", emptyMap())
            is Between -> {
                val betweenDopeQuery = between.toDopeQuery()
                val andDopeQuery = and.toDopeQuery()
                DopeQuery(
                    "BETWEEN ${betweenDopeQuery.queryString} AND ${andDopeQuery.queryString}",
                    betweenDopeQuery.parameters + andDopeQuery.parameters,
                )
            }
        }
    }
}

sealed class FrameBetween : Resolvable {
    data object UnboundedPreceding : FrameBetween()
    data object UnboundedFollowing : FrameBetween()
    data object CurrentRow : FrameBetween()
    class Following(val offset: UnaliasedExpression<NumberType>) : FrameBetween()
    class Preceding(val offset: UnaliasedExpression<NumberType>) : FrameBetween()

    override fun toDopeQuery(): DopeQuery {
        return when (this) {
            is UnboundedPreceding -> DopeQuery("UNBOUNDED PRECEDING", emptyMap())
            is UnboundedFollowing -> DopeQuery("UNBOUNDED FOLLOWING", emptyMap())
            is CurrentRow -> DopeQuery("CURRENT ROW", emptyMap())
            is Following -> {
                val offsetDopeQuery = offset.toDopeQuery()
                DopeQuery("${offsetDopeQuery.queryString} FOLLOWING", offsetDopeQuery.parameters)
            }

            is Preceding -> {
                val offsetDopeQuery = offset.toDopeQuery()
                DopeQuery("${offsetDopeQuery.queryString} PRECEDING", offsetDopeQuery.parameters)
            }
        }
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
    override fun toDopeQuery(): DopeQuery {
        val expressionDopeQuery = expression.toDopeQuery()
        return DopeQuery(
            queryString = expressionDopeQuery.queryString +
                (orderType?.let { " $it" }.orEmpty()) +
                (nullsOrder?.let { " ${it.queryString}" }.orEmpty()),
            parameters = expressionDopeQuery.parameters,
        )
    }
}
