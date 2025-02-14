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

class ArrayBinarySearchExpression<T : ValidType>(
    private val array: TypeExpression<ArrayType<T>>,
    private val value: TypeExpression<T>,
) : TypeExpression<NumberType>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val arrayDopeQuery = array.toDopeQuery(manager)
        val valueDopeQuery = value.toDopeQuery(manager)
        return DopeQuery(
            queryString = toFunctionQueryString("ARRAY_BINARY_SEARCH", arrayDopeQuery, valueDopeQuery),
            parameters = arrayDopeQuery.parameters.merge(valueDopeQuery.parameters),
        )
    }
}

fun <T : ValidType> arrayBinarySearch(
    array: TypeExpression<ArrayType<T>>,
    value: TypeExpression<T>,
) = ArrayBinarySearchExpression(array, value)

fun arrayBinarySearch(
    array: TypeExpression<ArrayType<StringType>>,
    value: String,
) = arrayBinarySearch(array, value.toDopeType())

fun arrayBinarySearch(
    array: TypeExpression<ArrayType<NumberType>>,
    value: Number,
) = arrayBinarySearch(array, value.toDopeType())

fun arrayBinarySearch(
    array: TypeExpression<ArrayType<BooleanType>>,
    value: Boolean,
) = arrayBinarySearch(array, value.toDopeType())

fun <T : ValidType> arrayBinarySearch(
    selectClause: ISelectOffsetClause<T>,
    value: TypeExpression<T>,
) = arrayBinarySearch(selectClause.asExpression(), value)

fun arrayBinarySearch(
    selectClause: ISelectOffsetClause<StringType>,
    value: String,
) = arrayBinarySearch(selectClause.asExpression(), value.toDopeType())

fun arrayBinarySearch(
    selectClause: ISelectOffsetClause<NumberType>,
    value: Number,
) = arrayBinarySearch(selectClause.asExpression(), value.toDopeType())

fun arrayBinarySearch(
    selectClause: ISelectOffsetClause<BooleanType>,
    value: Boolean,
) = arrayBinarySearch(selectClause.asExpression(), value.toDopeType())
