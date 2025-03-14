package ch.ergon.dope.resolvable.expression.rowscope.aggregate

import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.validtype.NumberType

class VarianceExpression(
    number: Field<NumberType>,
    quantifier: AggregateQuantifier? = null,
) : AggregateFunctionExpression<NumberType>("VARIANCE", number, quantifier)

fun variance(number: Field<NumberType>, quantifier: AggregateQuantifier? = null) = VarianceExpression(
    number,
    quantifier,
)
