package ch.ergon.dope.resolvable.expression.aggregate

import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.validtype.ValidType

class MaxExpression<T : ValidType>(
    field: Field<T>,
    quantifier: AggregateQuantifier?,
) : AggregateFunctionExpression<T>("MAX", field, quantifier)

fun <T : ValidType> max(field: Field<T>, quantifier: AggregateQuantifier? = null) = MaxExpression(
    field,
    quantifier,
)
