package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.ValidType

class MaxExpression<T : ValidType>(
    field: Field<T>,
    quantifier: AggregateQuantifier?,
) : AggregateExpression<T>(field, quantifier, "MAX")

fun max(field: Field<out ValidType>, quantifier: AggregateQuantifier? = null) = MaxExpression(field, quantifier)
