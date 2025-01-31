package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.NumberType

class AverageExpression(
    number: Field<NumberType>,
    quantifier: AggregateQuantifier?,
) : AggregateFunctionExpression<NumberType>("AVG", number, quantifier)

fun avg(number: Field<NumberType>, quantifier: AggregateQuantifier? = null) = AverageExpression(number, quantifier)
