package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ArrayPrependExpression<T : ValidType>(
    private val array: TypeExpression<ArrayType<T>>,
    private val value: TypeExpression<T>,
    private vararg val values: TypeExpression<T>,
) : TypeExpression<ArrayType<T>>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val arrayDopeQuery = array.toDopeQuery()
        val valueDopeQuery = value.toDopeQuery()
        val valuesDopeQuery = values.map { it.toDopeQuery() }
        return DopeQuery(
            queryString = toFunctionQueryString("ARRAY_PREPEND", valueDopeQuery, *valuesDopeQuery.toTypedArray(), arrayDopeQuery),
            parameters = arrayDopeQuery.parameters + valueDopeQuery.parameters + valuesDopeQuery.fold(
                emptyMap(),
            ) { argsParameters, field -> argsParameters + field.parameters },
        )
    }
}

fun <T : ValidType> arrayPrepend(array: TypeExpression<ArrayType<T>>, value: TypeExpression<T>, vararg values: TypeExpression<T>) =
    ArrayPrependExpression(array, value, *values)
