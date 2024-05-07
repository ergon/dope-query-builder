package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.resolvable.clause.ISelectUnnestClause
import ch.ergon.dope.resolvable.expression.AliasedExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

private const val UNNEST = "UNNEST"

class UnnestClause(private val parentClause: ISelectUnnestClause, val arrayTypeField: Field<ArrayType<out ValidType>>) : ISelectUnnestClause {
    override fun toQueryString(): String = formatToQueryString(parentClause, UNNEST, arrayTypeField)
}

class AliasedUnnestClause(
    private val parentClause: ISelectUnnestClause,
    private val aliasedExpression: AliasedExpression<ArrayType<out ValidType>>
) :
    ISelectUnnestClause {
    override fun toQueryString(): String = formatToQueryString(parentClause, UNNEST, aliasedExpression)
}
