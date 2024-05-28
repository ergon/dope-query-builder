package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.NumberType

class AverageExpression(
    number: Field<out NumberType>,
    quantifier: AggregateQuantifier?,
) : AggregateExpression(number, quantifier, "AVG")

fun avg(number: Field<out NumberType>, quantifier: AggregateQuantifier? = null) = AverageExpression(number, quantifier)
