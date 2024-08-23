package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class RepeatExpression(
    private val inStr: TypeExpression<StringType>,
    private val repeatAmount: TypeExpression<NumberType>,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val inStrDopeQuery = inStr.toDopeQuery(manager)
        val repeatDopeQuery = repeatAmount.toDopeQuery(manager)
        return DopeQuery(
            queryString = toFunctionQueryString(symbol = "REPEAT", inStrDopeQuery, repeatDopeQuery),
            parameters = inStrDopeQuery.parameters + repeatDopeQuery.parameters,
            manager = manager,
        )
    }
}

fun repeat(inStr: TypeExpression<StringType>, repeatAmount: TypeExpression<NumberType>) = RepeatExpression(inStr, repeatAmount)

fun repeat(inStr: TypeExpression<StringType>, repeatAmount: Number) = repeat(inStr, repeatAmount.toDopeType())

fun repeat(inStr: String, repeatAmount: TypeExpression<NumberType>) = repeat(inStr.toDopeType(), repeatAmount)

fun repeat(inStr: String, repeatAmount: Number) = repeat(inStr.toDopeType(), repeatAmount.toDopeType())
