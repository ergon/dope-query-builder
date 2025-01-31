package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.NumberType

class MedianExpression(
    number: Field<NumberType>,
    quantifier: AggregateQuantifier?,
) : AggregateFunctionExpression<NumberType>("MEDIAN", number, quantifier)

fun median(number: Field<NumberType>, quantifier: AggregateQuantifier? = null) = MedianExpression(number, quantifier)
