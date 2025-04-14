package ch.ergon.dope.resolvable.expression.rowscope.windowfunction

import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OrderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverWindowReference
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.WindowDefinition
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

private const val CUME_DIST = "CUME_DIST"

class CumeDist : WindowFunctionExpression<NumberType> {
    constructor(
        windowOrderClause: List<OrderingTerm>,
        windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    ) : super(
        functionName = CUME_DIST,
        overDefinition = OverWindowDefinition(
            WindowDefinition(
                windowPartitionClause = windowPartitionClause,
                windowOrderClause = windowOrderClause,
            ),
        ),
    )

    constructor(windowReference: String) : super(
        functionName = CUME_DIST,
        overDefinition = OverWindowReference(windowReference),
    )
}

fun cumeDist(windowReference: String) = CumeDist(windowReference)

fun cumeDist(
    windowOrderClause: List<OrderingTerm>,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
) = CumeDist(windowOrderClause, windowPartitionClause)
