package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

sealed class ArrayFunctionExpression<T : ValidType>(
    private val symbol: String,
    private val array: TypeExpression<ArrayType<T>>,
    private vararg val arguments: TypeExpression<out ValidType>,
) : TypeExpression<ArrayType<T>>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val arrayDopeQuery = when (array) {
            is ISelectOffsetClause<*> -> array.asSelectWithParentheses().toDopeQuery(manager)
            else -> array.toDopeQuery(manager)
        }
        val argumentsDopeQuery = arguments.map {
            when (it) {
                is ISelectOffsetClause<*> -> it.asSelectWithParentheses().toDopeQuery(manager)
                else -> it.toDopeQuery(manager)
            }
        }
        return DopeQuery(
            queryString = toFunctionQueryString(symbol, arrayDopeQuery, *argumentsDopeQuery.toTypedArray()),
            parameters = arrayDopeQuery.parameters + argumentsDopeQuery.fold(
                emptyMap(),
            ) { argsParameters, field -> argsParameters + field.parameters },
        )
    }
}
