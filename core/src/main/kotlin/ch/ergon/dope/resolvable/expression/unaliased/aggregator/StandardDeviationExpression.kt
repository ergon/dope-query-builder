package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.NumberType

class StandardDeviationExpression<T : NumberType>(
    number: Field<T>,
    quantifier: AggregateQuantifier?,
) : AggregateExpression<T>("STDDEV", number, quantifier)

fun stdDev(number: Field<out NumberType>, quantifier: AggregateQuantifier? = null) = StandardDeviationExpression(number, quantifier)
