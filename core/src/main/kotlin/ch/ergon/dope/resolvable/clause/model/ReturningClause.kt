package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.IDeleteOffsetClause
import ch.ergon.dope.resolvable.clause.IDeleteReturningClause
import ch.ergon.dope.resolvable.clause.IUpdateLimitClause
import ch.ergon.dope.resolvable.clause.IUpdateReturningClause
import ch.ergon.dope.resolvable.expression.SingleExpression
import ch.ergon.dope.resolvable.formatToQueryStringWithSymbol
import ch.ergon.dope.resolvable.fromable.AliasedSelectClause
import ch.ergon.dope.resolvable.fromable.Returnable
import ch.ergon.dope.validtype.ValidType

private const val RETURNING = "RETURNING"

enum class ReturningType(val queryString: String) {
    ELEMENT("ELEMENT"),
    RAW("RAW"),
    VALUE("VALUE"),
}

sealed class ReturningClause(
    private val returnable: Returnable,
    private vararg val additionalReturnables: Returnable,
    private val parentClause: Clause,
) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val returnables = arrayOf(returnable) + additionalReturnables
        val parentDopeQuery = parentClause.toDopeQuery(manager)
        val returnablesDopeQuery = returnables.map {
            when (it) {
                is AliasedSelectClause<*> -> it.asAliasedSelectClauseDefinition().toDopeQuery(manager)
                else -> it.toDopeQuery(manager)
            }
        }
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(
                parentDopeQuery.queryString,
                RETURNING,
                *returnablesDopeQuery.map { it.queryString }.toTypedArray(),
            ),
            parameters = parentDopeQuery.parameters.merge(
                *returnablesDopeQuery.map { it.parameters }.toTypedArray(),
            ),
        )
    }
}

class DeleteReturningClause(
    returnable: Returnable,
    vararg additionalReturnables: Returnable,
    parentClause: IDeleteOffsetClause,
) : IDeleteReturningClause, ReturningClause(
    returnable,
    *additionalReturnables,
    parentClause = parentClause,
)

class UpdateReturningClause(
    returnable: Returnable,
    vararg additionalReturnables: Returnable,
    parentClause: IUpdateLimitClause,
) : IUpdateReturningClause, ReturningClause(
    returnable,
    *additionalReturnables,
    parentClause = parentClause,
)

sealed class ReturningSingleClause(
    private val singleReturnable: SingleExpression<out ValidType>,
    private val returningType: ReturningType,
    private val parentClause: Clause,
) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery(manager)
        val singleReturnableDopeQuery = singleReturnable.toDopeQuery(manager)
        return DopeQuery(
            queryString = "${parentDopeQuery.queryString} $RETURNING " +
                "${returningType.queryString} ${singleReturnableDopeQuery.queryString}",
            parameters = parentDopeQuery.parameters.merge(
                singleReturnableDopeQuery.parameters,
            ),
        )
    }
}

class DeleteReturningSingleClause(
    singleReturnable: SingleExpression<out ValidType>,
    returningType: ReturningType,
    parentClause: IDeleteOffsetClause,
) : IDeleteReturningClause, ReturningSingleClause(singleReturnable, returningType, parentClause = parentClause)

class UpdateReturningSingleClause(
    singleReturnable: SingleExpression<out ValidType>,
    returningType: ReturningType,
    parentClause: IUpdateLimitClause,
) : IUpdateReturningClause, ReturningSingleClause(singleReturnable, returningType, parentClause = parentClause)
