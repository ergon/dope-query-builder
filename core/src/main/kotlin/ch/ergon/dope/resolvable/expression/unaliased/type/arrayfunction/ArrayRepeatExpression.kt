package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

class ArrayRepeatExpression<T : ValidType>(
    private val value: TypeExpression<T>,
    private val repetitions: TypeExpression<NumberType>,
) : TypeExpression<ArrayType<T>>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val valueDopeQuery = value.toDopeQuery()
        val repetitionsDopeQuery = repetitions.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString("ARRAY_REPEAT", valueDopeQuery, repetitionsDopeQuery),
            parameters = valueDopeQuery.parameters + repetitionsDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> arrayRepeat(
    value: TypeExpression<T>,
    repetitions: TypeExpression<NumberType>,
) = ArrayRepeatExpression(value, repetitions)

fun <T : ValidType> arrayRepeat(
    value: TypeExpression<T>,
    repetitions: Number,
) = arrayRepeat(value, repetitions.toDopeType())
