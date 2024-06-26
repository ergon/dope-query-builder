package ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.NumberType

sealed class NumberFunctionExpression(
    private val symbol: String,
    private val value: TypeExpression<NumberType>,
    private val extraValue: TypeExpression<NumberType>? = null,
) : TypeExpression<NumberType>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val valueDopeQuery = value.toDopeQuery()
        val extraValueDopeQuery = extraValue?.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString(symbol = symbol, valueDopeQuery, extra = extraValueDopeQuery),
            parameters = valueDopeQuery.parameters + extraValueDopeQuery?.parameters.orEmpty(),
        )
    }
}
