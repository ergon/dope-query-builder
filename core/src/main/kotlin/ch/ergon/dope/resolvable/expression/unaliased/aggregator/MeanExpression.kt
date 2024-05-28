package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.NumberType

class MeanExpression(
    number: Field<out NumberType>,
    quantifier: AggregateQuantifier?,
) : AggregateExpression(number, quantifier, "MEAN")

fun mean(number: Field<out NumberType>, quantifier: AggregateQuantifier? = null) = MeanExpression(number, quantifier)
