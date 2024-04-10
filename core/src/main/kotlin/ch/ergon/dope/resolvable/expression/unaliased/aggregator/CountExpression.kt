package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatToQueryStringWithBrackets
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class CountExpression(
    private val field: Field<out ValidType>,
) : AggregateExpression {
    override fun toQueryString(): String = formatToQueryStringWithBrackets("COUNT", field)
}

fun count(field: Field<out ValidType>) = CountExpression(field)

fun countAll() = CountExpression(Field<StringType>("*", ""))
