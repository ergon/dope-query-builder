package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.resolvable.clause.ISelectLetClause
import ch.ergon.dope.resolvable.expression.type.DopeVariable
import ch.ergon.dope.validtype.ValidType

data class LetClause<T : ValidType>(
    val dopeVariable: DopeVariable<out ValidType>,
    val dopeVariables: List<DopeVariable<out ValidType>> = emptyList(),
    val parentClause: ISelectFromClause<T>,
) : ISelectLetClause<T>
