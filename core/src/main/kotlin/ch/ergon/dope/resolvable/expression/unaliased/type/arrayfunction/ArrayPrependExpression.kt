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

class ArrayPrependExpression<T : ValidType>(
    private val array: TypeExpression<ArrayType<T>>,
    private val value: TypeExpression<T>,
    private vararg val additionalValues: TypeExpression<T>,
) : TypeExpression<ArrayType<T>>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val arrayDopeQuery = array.toDopeQuery()
        val valueDopeQuery = value.toDopeQuery()
        val additionalValuesDopeQuery = additionalValues.map { it.toDopeQuery() }
        return DopeQuery(
            queryString = toFunctionQueryString(
                "ARRAY_PREPEND",
                valueDopeQuery,
                *additionalValuesDopeQuery.toTypedArray(),
                arrayDopeQuery,
            ),
            parameters = arrayDopeQuery.parameters + valueDopeQuery.parameters + additionalValuesDopeQuery.fold(
                emptyMap(),
            ) { argsParameters, field -> argsParameters + field.parameters },
        )
    }
}

fun <T : ValidType> arrayPrepend(
    array: TypeExpression<ArrayType<T>>,
    value: TypeExpression<T>,
    vararg additionalValues: TypeExpression<T>,
) = ArrayPrependExpression(array, value, *additionalValues)

fun arrayPrepend(
    array: TypeExpression<ArrayType<StringType>>,
    value: String,
    vararg additionalValues: String,
) = arrayPrepend(array, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun arrayPrepend(
    array: TypeExpression<ArrayType<NumberType>>,
    value: Number,
    vararg additionalValues: Number,
) = arrayPrepend(array, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())

fun arrayPrepend(
    array: TypeExpression<ArrayType<BooleanType>>,
    value: Boolean,
    vararg additionalValues: Boolean,
) = arrayPrepend(array, value.toDopeType(), *additionalValues.map { it.toDopeType() }.toTypedArray())
