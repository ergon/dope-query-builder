package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ArrayAggregateExpression<T : ValidType>(
    private val field: Field<T>,
    private val quantifier: AggregateQuantifier?,
) : FunctionOperator, UnaliasedExpression<ArrayType<T>> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val fieldDopeQuery = field.toDopeQuery(manager)
        return DopeQuery(
            queryString = toFunctionQueryString("ARRAY_AGG", quantifier, fieldDopeQuery.queryString),
            parameters = fieldDopeQuery.parameters,
            positionalParameters = fieldDopeQuery.positionalParameters,
        )
    }
}

fun arrayAggregate(field: Field<out ValidType>, quantifier: AggregateQuantifier? = null) = ArrayAggregateExpression(field, quantifier)
