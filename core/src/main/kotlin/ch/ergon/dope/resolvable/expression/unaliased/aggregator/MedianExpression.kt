package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.NumberType

class MedianExpression(
    number: Field<out NumberType>,
    quantifier: AggregateQuantifier?,
) : AggregateExpression(number, quantifier, "MEDIAN")

fun median(number: Field<out NumberType>, quantifier: AggregateQuantifier? = null) = MedianExpression(number, quantifier)
