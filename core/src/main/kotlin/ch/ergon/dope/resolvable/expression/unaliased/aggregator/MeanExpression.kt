package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.NumberType

class MeanExpression(
    private val number: Field<out NumberType>,
    private val quantifier: AggregateQuantifier,
) : AggregateExpression, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val numberDopeQuery = number.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString("MEAN", quantifier, numberDopeQuery),
            parameters = numberDopeQuery.parameters,
        )
    }
}

fun mean(number: Field<out NumberType>, quantifier: AggregateQuantifier = ALL) = MeanExpression(number, quantifier)
