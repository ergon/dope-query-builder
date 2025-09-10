package ch.ergon.dope.resolvable.expression.rowscope.windowfunction

import ch.ergon.dope.resolvable.Selectable
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OrderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowReference
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowDefinition
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

private const val NTILE = "NTILE"

data class NTile(
    val numTiles: TypeExpression<NumberType>,
    val windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    val windowOrderClause: List<OrderingTerm>,
) : WindowFunctionExpression<NumberType> {
    override val functionName: String = NTILE
    override val quantifier: AggregateQuantifier? = null
    override val functionArguments: List<Selectable?> = listOf(numTiles)
    override val fromModifier: FromModifier? = null
    override val nullsModifier: NullsModifier? = null
    override val overDefinition: OverDefinition = OverWindowDefinition(
        WindowDefinition(
            windowPartitionClause = windowPartitionClause,
            windowOrderClause = windowOrderClause,
        ),
    )
}

data class NTileWithReference(
    val numTiles: TypeExpression<NumberType>,
    val windowReference: String,
) : WindowFunctionExpression<NumberType> {
    override val functionName: String = NTILE
    override val quantifier: AggregateQuantifier? = null
    override val functionArguments: List<Selectable?> = listOf(numTiles)
    override val fromModifier: FromModifier? = null
    override val nullsModifier: NullsModifier? = null
    override val overDefinition: OverDefinition = OverWindowReference(windowReference)
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
) = NTileWithReference(numTiles, windowReference)

fun ntile(
    numTiles: Number,
    windowReference: String,
) = ntile(numTiles.toDopeType(), windowReference)
