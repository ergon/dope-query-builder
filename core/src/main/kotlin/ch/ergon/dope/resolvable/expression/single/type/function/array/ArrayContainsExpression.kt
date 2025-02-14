package ch.ergon.dope.resolvable.expression.single.type.function.array

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.util.operator.FunctionOperator
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
        val arrayDopeQuery = array.toDopeQuery(manager)
        val valueDopeQuery = value.toDopeQuery(manager)
        return DopeQuery(
            queryString = toFunctionQueryString("ARRAY_CONTAINS", arrayDopeQuery, valueDopeQuery),
            parameters = arrayDopeQuery.parameters.merge(valueDopeQuery.parameters),
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

fun <T : ValidType> arrayContains(selectClause: ISelectOffsetClause<T>, value: TypeExpression<T>) =
    arrayContains(selectClause.asExpression(), value)

fun arrayContains(selectClause: ISelectOffsetClause<StringType>, value: String) =
    arrayContains(selectClause.asExpression(), value.toDopeType())

fun arrayContains(selectClause: ISelectOffsetClause<NumberType>, value: Number) =
    arrayContains(selectClause.asExpression(), value.toDopeType())

fun arrayContains(selectClause: ISelectOffsetClause<BooleanType>, value: Boolean) =
    arrayContains(selectClause.asExpression(), value.toDopeType())
