package ch.ergon.dope.resolvable.expression.unaliased.type.arrayfunction

import ch.ergon.dope.DopeQuery
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
    override fun toDopeQuery(): DopeQuery {
        val startDopeQuery = start.toDopeQuery()
        val endDopeQuery = end.toDopeQuery()
        val stepDopeQuery = step?.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString("ARRAY_RANGE", startDopeQuery, endDopeQuery, stepDopeQuery),
            parameters = startDopeQuery.parameters + endDopeQuery.parameters + stepDopeQuery?.parameters.orEmpty(),
        )
    }
}

fun arrayRange(
    start: TypeExpression<NumberType>,
    end: TypeExpression<NumberType>,
    step: TypeExpression<NumberType>? = null,
) = ArrayRangeExpression(start, end, step)

fun arrayRange(
    start: TypeExpression<NumberType>,
    end: Number,
    step: Number? = null,
) = arrayRange(start, end.toDopeType(), step?.toDopeType())

fun arrayRange(
    start: Number,
    end: TypeExpression<NumberType>,
    step: Number? = null,
) = arrayRange(start.toDopeType(), end, step?.toDopeType())

fun arrayRange(
    start: Number,
    end: Number,
    step: Number? = null,
) = arrayRange(start.toDopeType(), end.toDopeType(), step?.toDopeType())
