package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.NumberType

class VarianceExpression<T : NumberType>(
    number: Field<T>,
    quantifier: AggregateQuantifier?,
) : AggregateExpression<T>(number, quantifier, "VARIANCE")

fun variance(number: Field<out NumberType>, quantifier: AggregateQuantifier? = null) = VarianceExpression(number, quantifier)
