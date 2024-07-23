package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ArrayBinarySearchExpression<T : ValidType>(
    private val array: TypeExpression<ArrayType<T>>,
    private val value: TypeExpression<T>,
) : TypeExpression<NumberType>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val arrayDopeQuery = array.toDopeQuery()
        val valueDopeQuery = value.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString("ARRAY_BINARY_SEARCH", arrayDopeQuery, valueDopeQuery),
            parameters = arrayDopeQuery.parameters + valueDopeQuery.parameters,
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
