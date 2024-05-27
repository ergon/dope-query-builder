package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.NumberType

class SumExpression(
    private val number: Field<out NumberType>,
    private val quantifier: AggregateQuantifier,
) : AggregateExpression, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val numberDopeQuery = number.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString("SUM", quantifier, numberDopeQuery),
            parameters = numberDopeQuery.parameters,
        )
    }
}

fun sum(number: Field<NumberType>, quantifier: AggregateQuantifier = ALL) = SumExpression(number, quantifier)
