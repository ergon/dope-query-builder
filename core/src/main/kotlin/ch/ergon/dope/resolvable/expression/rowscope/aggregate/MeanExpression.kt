package ch.ergon.dope.resolvable.expression.rowscope.aggregate

import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.OverClause
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.validtype.NumberType

class MeanExpression(
    number: Field<NumberType>,
    quantifier: AggregateQuantifier?,
    overClause: OverClause? = null,
) : AggregateFunctionExpression<NumberType>("MEAN", number, quantifier, overClause)

fun mean(number: Field<NumberType>, quantifier: AggregateQuantifier? = null) = MeanExpression(
    number,
    quantifier,
)
