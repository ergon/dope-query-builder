package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.ValidType

class MinExpression(
    field: Field<out ValidType>,
    quantifier: AggregateQuantifier?,
) : AggregateExpression(field, quantifier, "MIN")

fun min(field: Field<out ValidType>, quantifier: AggregateQuantifier? = null) = MinExpression(field, quantifier)
