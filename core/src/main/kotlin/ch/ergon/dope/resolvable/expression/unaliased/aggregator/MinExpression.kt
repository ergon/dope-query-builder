package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.ValidType

class MinExpression<T : ValidType>(
    field: Field<T>,
    quantifier: AggregateQuantifier?,
) : AggregateExpression<T>("MIN", field, quantifier)

fun min(field: Field<out ValidType>, quantifier: AggregateQuantifier? = null) = MinExpression(field, quantifier)
