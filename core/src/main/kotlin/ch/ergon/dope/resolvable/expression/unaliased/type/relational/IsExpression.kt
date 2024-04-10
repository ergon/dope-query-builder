package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.validtype.ValidType

interface IsExpression {
    fun format(
        left: TypeExpression<out ValidType>,
        symbol: String,
    ) = formatToQueryString(left.toQueryString(), symbol)
}
