package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.resolvable.clause.ISelectUnnestClause
import ch.ergon.dope.resolvable.expression.AliasedExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class UnnestClause(private val parentClause: ISelectUnnestClause, val field: Field<ArrayType<out ValidType>>) : ISelectUnnestClause {
    override fun toQueryString(): String = "${parentClause.toQueryString()} UNNEST ${field.toQueryString()}"
}

class UnnestClauseWithAliased(private val parentClause: ISelectUnnestClause, val field: AliasedExpression<ArrayType<out ValidType>>) :
    ISelectUnnestClause {
    override fun toQueryString(): String = "${parentClause.toQueryString()} UNNEST ${field.toQueryString()}"
}
