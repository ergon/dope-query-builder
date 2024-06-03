package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.NumberType

class MeanExpression<T : NumberType>(
    number: Field<T>,
    quantifier: AggregateQuantifier?,
) : AggregateExpression<T>(number, quantifier, "MEAN")

fun mean(number: Field<out NumberType>, quantifier: AggregateQuantifier? = null) = MeanExpression(number, quantifier)
