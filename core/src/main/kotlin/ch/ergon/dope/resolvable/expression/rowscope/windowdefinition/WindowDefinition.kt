package ch.ergon.dope.resolvable.expression.rowscope.windowdefinition

import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

data class WindowDefinition(
    val windowReferenceExpression: TypeExpression<StringType>? = null,
    val windowPartitionClause: List<TypeExpression<out ValidType>>? = null,
    val windowOrderClause: List<OrderingTerm>? = null,
    val windowFrameClause: WindowFrameClause? = null,
) : Resolvable
