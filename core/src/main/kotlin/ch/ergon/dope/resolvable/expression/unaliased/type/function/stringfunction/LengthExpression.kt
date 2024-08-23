package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class LengthExpression(
    private val inStr: TypeExpression<StringType>,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val inStrDopeQuery = inStr.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString(symbol = "LENGTH", inStrDopeQuery),
            parameters = inStrDopeQuery.parameters,
        )
    }
}

fun length(inStr: TypeExpression<StringType>) = LengthExpression(inStr)

fun length(inStr: String) = length(inStr.toDopeType())
