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

class ArrayPrependExpression<T : ValidType>(
    private val array: TypeExpression<ArrayType<T>>,
    private val value: TypeExpression<T>,
    private vararg val additionalValues: TypeExpression<T>,
) : TypeExpression<ArrayType<T>>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val arrayDopeQuery = when (array) {
            is ISelectOffsetClause<*> -> array.asSubQuery().toDopeQuery(manager)
            else -> array.toDopeQuery(manager)
        }
        val valueDopeQuery = when (value) {
            is ISelectOffsetClause<*> -> value.asSubQuery().toDopeQuery(manager)
            else -> value.toDopeQuery(manager)
        }
        val additionalValuesDopeQuery = additionalValues.map {
            when (it) {
                is ISelectOffsetClause<*> -> it.asSubQuery().toDopeQuery(manager)
                else -> it.toDopeQuery(manager)
            }
        }
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
