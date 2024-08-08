package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

sealed class ArrayFunctionExpression<T : ValidType>(
    private val symbol: String,
    private val array: TypeExpression<ArrayType<T>>,
    private vararg val arguments: TypeExpression<out ValidType>,
) : TypeExpression<ArrayType<T>>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val arrayDopeQuery = array.toDopeQuery()
        val argumentsDopeQuery = arguments.map { it.toDopeQuery() }
        return DopeQuery(
            queryString = toFunctionQueryString(symbol, arrayDopeQuery, *argumentsDopeQuery.toTypedArray()),
            parameters = arrayDopeQuery.parameters + argumentsDopeQuery.fold(
                emptyMap(),
            ) { argsParameters, field -> argsParameters + field.parameters },
        )
    }
}
