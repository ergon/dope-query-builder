package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

sealed class ArrayFunctionExpression<T : ValidType>(
    private val symbol: String,
    private val array: TypeExpression<ArrayType<T>>,
    private vararg val arguments: TypeExpression<out ValidType>,
) : TypeExpression<ArrayType<T>>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val arrayDopeQuery = array.toDopeQuery(manager)
        val argumentsDopeQuery = arguments.map { it.toDopeQuery(manager) }
        return DopeQuery(
            queryString = toFunctionQueryString(symbol, arrayDopeQuery, *argumentsDopeQuery.toTypedArray()),
            parameters = arrayDopeQuery.parameters.merge(*argumentsDopeQuery.map { it.parameters }.toTypedArray()),
        )
    }
}
