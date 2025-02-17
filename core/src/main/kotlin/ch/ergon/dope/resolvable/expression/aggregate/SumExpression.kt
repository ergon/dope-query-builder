package ch.ergon.dope.resolvable.expression.aggregate

import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.validtype.NumberType

class SumExpression(
    number: Field<NumberType>,
    quantifier: AggregateQuantifier?,
) : AggregateFunctionExpression<NumberType>("SUM", number, quantifier)

fun sum(number: Field<NumberType>, quantifier: AggregateQuantifier? = null) = SumExpression(
    number,
    quantifier,
)
