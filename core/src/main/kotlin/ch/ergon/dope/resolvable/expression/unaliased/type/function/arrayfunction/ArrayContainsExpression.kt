package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ArrayContainsExpression<T : ValidType>(
    private val array: TypeExpression<ArrayType<T>>,
    private val value: TypeExpression<T>,
) : TypeExpression<BooleanType>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val arrayDopeQuery = when (array) {
            is ISelectOffsetClause<*> -> array.asSubQuery().toDopeQuery(manager)
            else -> array.toDopeQuery(manager)
        }
        val valueDopeQuery = when (value) {
            is ISelectOffsetClause<*> -> value.asSubQuery().toDopeQuery(manager)
            else -> value.toDopeQuery(manager)
        }
        return DopeQuery(
            queryString = toFunctionQueryString("ARRAY_CONTAINS", arrayDopeQuery, valueDopeQuery),
            parameters = arrayDopeQuery.parameters + valueDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> arrayContains(array: TypeExpression<ArrayType<T>>, value: TypeExpression<T>) =
    ArrayContainsExpression(array, value)

fun arrayContains(array: TypeExpression<ArrayType<StringType>>, value: String) =
    arrayContains(array, value.toDopeType())

fun arrayContains(array: TypeExpression<ArrayType<NumberType>>, value: Number) =
    arrayContains(array, value.toDopeType())

fun arrayContains(array: TypeExpression<ArrayType<BooleanType>>, value: Boolean) =
    arrayContains(array, value.toDopeType())
