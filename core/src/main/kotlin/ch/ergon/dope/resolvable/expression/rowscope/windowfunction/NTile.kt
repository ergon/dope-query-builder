package ch.ergon.dope.resolvable.expression.rowscope.windowfunction

import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OrderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowReference
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowDefinition
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

private const val NTILE = "NTILE"

class NTile : WindowFunctionExpression<NumberType> {
    constructor(
        numTiles: TypeExpression<NumberType>,
        windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>,
    ) : super(
        functionName = NTILE,
        functionArguments = listOf(numTiles),
        overDefinition = OverWindowDefinition(
            WindowDefinition(
                windowPartitionClause = windowPartitionClause,
                windowOrderClause = windowOrderClause,
            ),
        ),
    )

    constructor(numTiles: TypeExpression<NumberType>, windowReference: String) : super(
        functionName = NTILE,
        functionArguments = listOf(numTiles),
        overDefinition = OverWindowReference(windowReference),
    )
}

fun ntile(
    numTiles: TypeExpression<NumberType>,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>,
) = NTile(numTiles, windowPartitionClause, windowOrderClause)

fun ntile(
    numTiles: Number,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>,
) = ntile(numTiles.toDopeType(), windowPartitionClause, windowOrderClause)

fun ntile(
    numTiles: TypeExpression<NumberType>,
    windowReference: String,
) = NTile(numTiles, windowReference)

fun ntile(
    numTiles: Number,
    windowReference: String,
) = ntile(numTiles.toDopeType(), windowReference)
