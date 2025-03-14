package ch.ergon.dope.resolvable.expression.rowscope.aggregate

import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ArrayAggregateExpression<T : ValidType>(
    field: Field<T>,
    quantifier: AggregateQuantifier? = null,
) : AggregateFunctionExpression<ArrayType<T>>("ARRAY_AGG", field, quantifier)

fun <T : ValidType> arrayAggregate(field: Field<T>, quantifier: AggregateQuantifier? = null) =
    ArrayAggregateExpression(field, quantifier)
