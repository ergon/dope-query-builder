package ch.ergon.dope.resolvable.expression.rowscope.windowfunction

import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.model.OrderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.model.OverClauseWindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.model.OverClauseWindowReference
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.model.WindowDefinition
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

private const val RANK = "RANK"

class Rank : WindowFunctionExpression<NumberType> {
    constructor(
        windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>,
    ) : super(
        functionName = RANK,
        overClause = OverClauseWindowDefinition(
            WindowDefinition(
                windowPartitionClause = windowPartitionClause,
                windowOrderClause = windowOrderClause,
            ),
        ),
    )

    constructor(windowReference: String) : super(
        functionName = RANK,
        overClause = OverClauseWindowReference(windowReference),
    )
}

fun rank(windowReference: String) = Rank(windowReference)

fun rank(
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>,
) = Rank(windowPartitionClause, windowOrderClause)
