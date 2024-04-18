package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.resolvable.expression.AsteriskExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatToQueryStringWithBrackets
import ch.ergon.dope.validtype.ValidType

class CountExpression(
    private val field: Field<out ValidType>,
) : AggregateExpression {
    override fun toQueryString(): String = formatToQueryStringWithBrackets("COUNT", field)
}

class CountAsteriskExpression : AggregateExpression {
    override fun toQueryString(): String = formatToQueryStringWithBrackets("COUNT", AsteriskExpression())
}

fun count(field: Field<out ValidType>) = CountExpression(field)

fun countAsterisk() = CountAsteriskExpression()
