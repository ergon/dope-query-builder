package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

sealed class ArrayFunctionExpression<T : ValidType>(
    private val symbol: String,
    private val array: TypeExpression<ArrayType<T>>,
    private vararg val args: TypeExpression<out ValidType>,
    private val extra: TypeExpression<out ValidType>? = null,
) : TypeExpression<ArrayType<T>>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val arrayDopeQuery = array.toDopeQuery()
        val argsDopeQuery = args.map { it.toDopeQuery() }
        val extraDopeQuery = extra?.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString(symbol, arrayDopeQuery, *argsDopeQuery.toTypedArray(), extra = extraDopeQuery),
            parameters = arrayDopeQuery.parameters + argsDopeQuery.fold(
                emptyMap(),
            ) { argsParameters, field -> argsParameters + field.parameters } + extraDopeQuery?.parameters.orEmpty(),
        )
    }
}

sealed class ArrayFunctionNumberExpression<T : ValidType>(
    private val symbol: String,
    private val array: TypeExpression<ArrayType<T>>,
    private val value: TypeExpression<out ValidType>? = null,
) : TypeExpression<ValidType>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val arrayDopeQuery = array.toDopeQuery()
        val valueDopeQuery = value?.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString(symbol, arrayDopeQuery, extra = valueDopeQuery),
            parameters = arrayDopeQuery.parameters + valueDopeQuery?.parameters.orEmpty(),
        )
    }
}
