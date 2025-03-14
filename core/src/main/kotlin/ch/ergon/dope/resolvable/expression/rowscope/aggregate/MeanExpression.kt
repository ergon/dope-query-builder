package ch.ergon.dope.resolvable.expression.rowscope.aggregate

import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.validtype.NumberType

class MeanExpression(
    number: Field<NumberType>,
    quantifier: AggregateQuantifier? = null,
) : AggregateFunctionExpression<NumberType>("MEAN", number, quantifier)

fun mean(number: Field<NumberType>, quantifier: AggregateQuantifier? = null) = MeanExpression(
    number,
    quantifier,
)
