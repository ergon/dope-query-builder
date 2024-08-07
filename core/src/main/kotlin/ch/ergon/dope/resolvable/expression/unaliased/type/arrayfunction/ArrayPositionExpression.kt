package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

class ArrayPositionExpression<T : ValidType>(
    private val array: TypeExpression<ArrayType<T>>,
    private val value: TypeExpression<T>,
) : TypeExpression<NumberType>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val arrayDopeQuery = array.toDopeQuery()
        val valueDopeQuery = value.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString("ARRAY_POSITION", arrayDopeQuery, valueDopeQuery),
            parameters = arrayDopeQuery.parameters + valueDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> arrayPosition(array: TypeExpression<ArrayType<T>>, value: TypeExpression<T>) =
    ArrayPositionExpression(array, value)
