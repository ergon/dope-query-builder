package ch.ergon.dope.resolvable.expression.unaliased.type.function.arrayfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.NumberType

class ArrayRangeExpression(
    private val start: TypeExpression<NumberType>,
    private val end: TypeExpression<NumberType>,
    private val step: TypeExpression<NumberType>? = null,
) : TypeExpression<ArrayType<NumberType>>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val startDopeQuery = start.toDopeQuery(manager)
        val endDopeQuery = end.toDopeQuery(manager)
        val stepDopeQuery = step?.toDopeQuery(manager)
        return DopeQuery(
            queryString = toFunctionQueryString("ARRAY_RANGE", startDopeQuery, endDopeQuery, stepDopeQuery),
            parameters = startDopeQuery.parameters + endDopeQuery.parameters + stepDopeQuery?.parameters.orEmpty(),
        )
    }
}

fun arrayRange(
    start: TypeExpression<NumberType>,
    end: TypeExpression<NumberType>,
    step: TypeExpression<NumberType>,
) = ArrayRangeExpression(start, end, step)

fun arrayRange(
    start: TypeExpression<NumberType>,
    end: TypeExpression<NumberType>,
    step: Number? = null,
) = ArrayRangeExpression(start, end, step?.toDopeType())

fun arrayRange(
    start: TypeExpression<NumberType>,
    end: Number,
    step: TypeExpression<NumberType>,
) = arrayRange(start, end.toDopeType(), step)

fun arrayRange(
    start: Number,
    end: TypeExpression<NumberType>,
    step: TypeExpression<NumberType>,
) = arrayRange(start.toDopeType(), end, step)

fun arrayRange(
    start: TypeExpression<NumberType>,
    end: Number,
    step: Number? = null,
) = arrayRange(start, end.toDopeType(), step)

fun arrayRange(
    start: Number,
    end: TypeExpression<NumberType>,
    step: Number? = null,
) = arrayRange(start.toDopeType(), end, step)

fun arrayRange(
    start: Number,
    end: Number,
    step: TypeExpression<NumberType>,
) = arrayRange(start.toDopeType(), end.toDopeType(), step)

fun arrayRange(
    start: Number,
    end: Number,
    step: Number? = null,
) = arrayRange(start.toDopeType(), end.toDopeType(), step)
