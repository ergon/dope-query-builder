package ch.ergon.dope.resolvable.expression.rowscope.aggregate

import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.OverClause
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.validtype.NumberType

class MedianExpression(
    number: Field<NumberType>,
    quantifier: AggregateQuantifier?,
    overClause: OverClause? = null,
) : AggregateFunctionExpression<NumberType>("MEDIAN", number, quantifier, overClause)

fun median(number: Field<NumberType>, quantifier: AggregateQuantifier? = null) = MedianExpression(
    number,
    quantifier,
)
