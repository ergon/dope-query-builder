package ch.ergon.dope.resolvable.expression.rowscope.aggregate

import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.OverClause
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.validtype.NumberType

class SumExpression(
    number: Field<NumberType>,
    quantifier: AggregateQuantifier?,
    overClause: OverClause? = null,
) : AggregateFunctionExpression<NumberType>("SUM", number, quantifier, overClause)

fun sum(number: Field<NumberType>, quantifier: AggregateQuantifier? = null) = SumExpression(
    number,
    quantifier,
)
