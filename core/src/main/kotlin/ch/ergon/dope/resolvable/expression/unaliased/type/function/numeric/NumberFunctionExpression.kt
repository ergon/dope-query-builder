package ch.ergon.dope.resolvable.expression.unaliased.type.function.numeric

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.NumberType

sealed class NumberFunctionExpression(
    private val symbol: String,
    private val value: TypeExpression<NumberType>? = null,
    private val additionalValue: TypeExpression<NumberType>? = null,
) : TypeExpression<NumberType>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val valueDopeQuery = value?.toDopeQuery(manager)
        val additionalValueDopeQuery = additionalValue?.toDopeQuery(manager)
        return DopeQuery(
            queryString = toFunctionQueryString(symbol, valueDopeQuery, additionalValueDopeQuery),
            parameters = valueDopeQuery?.parameters.orEmpty() + additionalValueDopeQuery?.parameters.orEmpty(),
        )
    }
}
