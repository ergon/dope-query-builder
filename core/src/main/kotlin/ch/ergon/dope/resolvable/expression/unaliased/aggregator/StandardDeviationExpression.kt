package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.NumberType

class StandardDeviationExpression(
    number: Field<NumberType>,
    quantifier: AggregateQuantifier?,
) : AggregateFunctionExpression<NumberType>("STDDEV", number, quantifier)

fun stdDev(number: Field<NumberType>, quantifier: AggregateQuantifier? = null) = StandardDeviationExpression(number, quantifier)
