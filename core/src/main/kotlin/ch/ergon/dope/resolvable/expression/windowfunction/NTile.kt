package ch.ergon.dope.resolvable.expression.windowfunction

import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

private const val NTILE = "NTILE"

class NTile : WindowFunction {
    constructor(
        numTiles: UnaliasedExpression<NumberType>,
        windowPartitionClause: List<UnaliasedExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>,
    ) : super(
        functionName = NTILE,
        windowFunctionArguments = WindowFunctionArguments(numTiles),
        overClause = OverClauseWindowDefinition(
            WindowDefinition(
                windowPartitionClause = windowPartitionClause,
                windowOrderClause = windowOrderClause,
            ),
        ),
    )

    constructor(numTiles: UnaliasedExpression<NumberType>, windowReference: String) : super(
        functionName = NTILE,
        windowFunctionArguments = WindowFunctionArguments(numTiles),
        overClause = OverClauseWindowReference(windowReference),
    )
}

fun ntile(
    numTiles: UnaliasedExpression<NumberType>,
    windowPartitionClause: List<UnaliasedExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>,
) = NTile(numTiles, windowPartitionClause, windowOrderClause)

fun ntile(numTiles: UnaliasedExpression<NumberType>, windowReference: String) = NTile(numTiles, windowReference)
