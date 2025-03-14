package ch.ergon.dope.resolvable.expression.rowscope.windowfunction

import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.model.OrderingTerm
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.model.OverClauseWindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.model.OverClauseWindowReference
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.model.WindowDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.model.WindowFrameClause
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.model.WindowFunctionArguments
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

private const val RATIO_TO_REPORT = "RATIO_TO_REPORT"

class RatioToReport : WindowFunctionExpression<NumberType> {
    constructor(
        expression: TypeExpression<out ValidType>,
        windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
        windowOrderClause: List<OrderingTerm>? = null,
        windowFrameClause: WindowFrameClause? = null,
    ) : super(
        functionName = RATIO_TO_REPORT,
        windowFunctionArguments = WindowFunctionArguments(expression),
        overClause = OverClauseWindowDefinition(
            WindowDefinition(
                windowPartitionClause = windowPartitionClause,
                windowOrderClause = windowOrderClause,
                windowFrameClause = windowFrameClause,
            ),
        ),
    )

    constructor(expression: TypeExpression<out ValidType>, nullsModifier: NullsModifier? = null, windowReference: String) : super(
        functionName = RATIO_TO_REPORT,
        windowFunctionArguments = WindowFunctionArguments(expression),
        nullsModifier = nullsModifier,
        overClause = OverClauseWindowReference(windowReference),
    )
}

fun ratioToReport(
    expression: TypeExpression<out ValidType>,
    windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    windowOrderClause: List<OrderingTerm>? = null,
    windowFrameClause: WindowFrameClause? = null,
) = RatioToReport(expression, windowPartitionClause, windowOrderClause, windowFrameClause)

fun ratioToReport(
    expression: TypeExpression<out ValidType>,
    nullsModifier: NullsModifier? = null,
    windowReference: String,
) = RatioToReport(expression, nullsModifier, windowReference)
