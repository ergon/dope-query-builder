package ch.ergon.dope.resolvable

import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.SingleExpression
import ch.ergon.dope.validtype.ValidType

data class AliasedSelectClause<T : ValidType>(
    val alias: String,
    val parentClause: ISelectOffsetClause<T>,
) : Fromable, Joinable, Nestable, SingleExpression<T> {
    fun asAliasedSelectClauseDefinition() = AliasedSelectClauseDefinition(alias, parentClause)
}

data class AliasedSelectClauseDefinition<T : ValidType>(
    val alias: String,
    val parentClause: ISelectOffsetClause<T>,
) : Fromable, Joinable, Nestable, SingleExpression<T>
