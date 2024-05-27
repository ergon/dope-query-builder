package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.ValidType

class MaxExpression(
    private val field: Field<out ValidType>,
    private val quantifier: AggregateQuantifier,
) : AggregateExpression, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val fieldDopeQuery = field.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString("MAX", quantifier, fieldDopeQuery),
            parameters = fieldDopeQuery.parameters,
        )
    }
}

fun max(field: Field<out ValidType>, quantifier: AggregateQuantifier = ALL) = MaxExpression(field, quantifier)
