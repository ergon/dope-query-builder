package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.NumberType

class AverageExpression<T : NumberType>(
    number: Field<T>,
    quantifier: AggregateQuantifier?,
) : AggregateExpression<T>("AVG", number, quantifier)

fun avg(number: Field<out NumberType>, quantifier: AggregateQuantifier? = null) = AverageExpression(number, quantifier)
