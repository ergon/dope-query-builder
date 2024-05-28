package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.ValidType

class MaxExpression(
    field: Field<out ValidType>,
    quantifier: AggregateQuantifier?,
) : AggregateExpression(field, quantifier, "MAX")

fun max(field: Field<out ValidType>, quantifier: AggregateQuantifier? = null) = MaxExpression(field, quantifier)
