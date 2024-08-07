package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class ArrayIfNullExpression<T : ValidType>(
    private val array: TypeExpression<ArrayType<T>>,
) : TypeExpression<T>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val arrayDopeQuery = array.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString("ARRAY_IFNULL", arrayDopeQuery),
            parameters = arrayDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> arrayIfNull(array: TypeExpression<ArrayType<T>>) = ArrayIfNullExpression(array)
