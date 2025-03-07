package ch.ergon.dope.resolvable.expression.rowscope.aggregate

import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.validtype.NumberType

class MedianExpression(
    number: Field<NumberType>,
    quantifier: AggregateQuantifier?,
) : AggregateFunctionExpression<NumberType>("MEDIAN", number, quantifier)

fun median(number: Field<NumberType>, quantifier: AggregateQuantifier? = null) = MedianExpression(
    number,
    quantifier,
)
