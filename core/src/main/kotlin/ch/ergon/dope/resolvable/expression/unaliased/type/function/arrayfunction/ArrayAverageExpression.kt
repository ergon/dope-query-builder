package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.NumberType

class ArrayAverageExpression<T : NumberType>(
    private val array: TypeExpression<ArrayType<T>>,
) : TypeExpression<T>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val arrayDopeQuery = when (array) {
            is ISelectOffsetClause<*> -> array.asSelectWithParentheses().toDopeQuery(manager)
            else -> array.toDopeQuery(manager)
        }
        return DopeQuery(
            queryString = toFunctionQueryString("ARRAY_AVG", arrayDopeQuery),
            parameters = arrayDopeQuery.parameters,
        )
    }
}

fun <T : NumberType> arrayAverage(array: TypeExpression<ArrayType<T>>) = ArrayAverageExpression(array)
