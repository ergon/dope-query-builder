package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

class ArrayPositionExpression<T : ValidType>(
    private val array: TypeExpression<ArrayType<T>>,
    private val value: TypeExpression<T>,
) : TypeExpression<NumberType>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val arrayDopeQuery = array.toDopeQuery(manager)
        val valueDopeQuery = value.toDopeQuery(manager)
        return DopeQuery(
            queryString = toFunctionQueryString("ARRAY_POSITION", arrayDopeQuery, valueDopeQuery),
            parameters = arrayDopeQuery.parameters.merge(valueDopeQuery.parameters),
        )
    }
}

fun <T : ValidType> arrayPosition(array: TypeExpression<ArrayType<T>>, value: TypeExpression<T>) =
    ArrayPositionExpression(array, value)

fun <T : ValidType> arrayPosition(array: ISelectOffsetClause<T>, value: TypeExpression<T>) =
    arrayPosition(array.asExpression(), value)
