package ch.ergon.dope.resolvable.expression.rowscope.aggregate

import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.validtype.ValidType

class MinExpression<T : ValidType>(
    field: Field<T>,
    quantifier: AggregateQuantifier?,
) : AggregateFunctionExpression<T>("MIN", field, quantifier)

fun min(field: Field<out ValidType>, quantifier: AggregateQuantifier? = null) = MinExpression(
    field,
    quantifier,
)
