package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.ValidType

class ArrayAggregateExpression(
    field: Field<out ValidType>,
    quantifier: AggregateQuantifier?,
) : AggregateExpression(field, quantifier, "ARRAY_AGG")

fun arrayAggregate(field: Field<out ValidType>, quantifier: AggregateQuantifier? = null) = ArrayAggregateExpression(field, quantifier)
