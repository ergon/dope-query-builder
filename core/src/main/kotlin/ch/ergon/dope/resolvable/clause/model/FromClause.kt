package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.resolvable.Fromable
import ch.ergon.dope.resolvable.clause.ISelectClause
import ch.ergon.dope.resolvable.clause.ISelectFromClause
import ch.ergon.dope.validtype.ValidType

data class FromClause<T : ValidType>(
    val fromable: Fromable,
    val parentClause: ISelectClause<T>,
) : ISelectFromClause<T>
