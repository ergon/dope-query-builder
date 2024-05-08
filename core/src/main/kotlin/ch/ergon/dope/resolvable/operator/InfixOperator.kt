package ch.ergon.dope.resolvable.operator

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.resolvable.formatToQueryStringWithBrackets
import ch.ergon.dope.validtype.ValidType

open class InfixOperator(
    private val left: TypeExpression<out ValidType>,
    private val symbol: String,
    private val right: TypeExpression<out ValidType>,
) {
    fun toInfixQueryString(useBrackets: Boolean = false) =
        if (useBrackets) formatToQueryStringWithBrackets(left.toQueryString(), symbol, right.toQueryString())
        else formatToQueryString(left.toQueryString(), symbol, right.toQueryString())
}
