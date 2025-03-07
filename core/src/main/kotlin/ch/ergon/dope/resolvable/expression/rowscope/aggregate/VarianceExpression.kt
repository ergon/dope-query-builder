package ch.ergon.dope.resolvable.expression.rowscope.aggregate

import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.OverClause
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.validtype.NumberType

class VarianceExpression(
    number: Field<NumberType>,
    quantifier: AggregateQuantifier?,
    overClause: OverClause? = null,
) : AggregateFunctionExpression<NumberType>("VARIANCE", number, quantifier, overClause)

fun variance(number: Field<NumberType>, quantifier: AggregateQuantifier? = null) = VarianceExpression(
    number,
    quantifier,
)
