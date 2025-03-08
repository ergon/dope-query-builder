package ch.ergon.dope.resolvable.expression.rowscope.aggregate

import ch.ergon.dope.resolvable.Asterisk
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

class CountExpression(
    field: Field<out ValidType>,
    quantifier: AggregateQuantifier?,
) : AggregateFunctionExpression<NumberType>("COUNT", field, quantifier)

fun count(field: Field<out ValidType>, quantifier: AggregateQuantifier? = null) = CountExpression(field, quantifier)

class CountAsteriskExpression : AggregateFunctionExpression<NumberType>("COUNT", Asterisk(), quantifier = null)

fun countAsterisk() = CountAsteriskExpression()
