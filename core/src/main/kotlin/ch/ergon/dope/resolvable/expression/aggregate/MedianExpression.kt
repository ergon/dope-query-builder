package ch.ergon.dope.resolvable.expression.aggregate

import ch.ergon.dope.resolvable.expression.single.type.Field
import ch.ergon.dope.validtype.NumberType

class MedianExpression(
    number: Field<NumberType>,
    quantifier: AggregateQuantifier?,
) : AggregateFunctionExpression<NumberType>("MEDIAN", number, quantifier)

fun median(number: Field<NumberType>, quantifier: AggregateQuantifier? = null) = MedianExpression(
    number,
    quantifier,
)
