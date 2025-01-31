package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.NumberType

class MeanExpression(
    number: Field<NumberType>,
    quantifier: AggregateQuantifier?,
) : AggregateFunctionExpression<NumberType>("MEAN", number, quantifier)

fun mean(number: Field<NumberType>, quantifier: AggregateQuantifier? = null) = MeanExpression(number, quantifier)
