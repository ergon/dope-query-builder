package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.resolvable.clause.ISelectWithClause
import ch.ergon.dope.resolvable.expression.type.DopeVariable
import ch.ergon.dope.validtype.ValidType

data class WithClause(
    val withExpression: DopeVariable<out ValidType>,
    val additionalWithExpressions: List<DopeVariable<out ValidType>> = emptyList(),
) : ISelectWithClause
