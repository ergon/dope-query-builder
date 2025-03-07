package ch.ergon.dope.resolvable.expression.rowscope.aggregate

import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.OverClause
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.validtype.NumberType

class StandardDeviationExpression(
    number: Field<NumberType>,
    quantifier: AggregateQuantifier?,
    overClause: OverClause? = null,
) : AggregateFunctionExpression<NumberType>("STDDEV", number, quantifier, overClause)

fun stdDev(number: Field<NumberType>, quantifier: AggregateQuantifier? = null) = StandardDeviationExpression(
    number,
    quantifier,
)
