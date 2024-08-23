package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

class ArraySumExpression<T : ValidType>(
    private val array: TypeExpression<ArrayType<T>>,
) : TypeExpression<NumberType>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val arrayDopeQuery = array.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString("ARRAY_SUM", arrayDopeQuery),
            parameters = arrayDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> arraySum(array: TypeExpression<ArrayType<T>>) = ArraySumExpression(array)
