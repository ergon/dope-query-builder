package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.NumberType

class StandardDeviationExpression(
    number: Field<out NumberType>,
    quantifier: AggregateQuantifier?,
) : AggregateExpression(number, quantifier, "STDDEV")

fun stdDev(number: Field<out NumberType>, quantifier: AggregateQuantifier? = null) = StandardDeviationExpression(number, quantifier)
