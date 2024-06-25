package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class ReverseExpression(
    private val inStr: TypeExpression<StringType>,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val inStrDopeQuery = inStr.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString(symbol = "REVERSE", inStrDopeQuery),
            parameters = inStrDopeQuery.parameters,
        )
    }
}

fun reverse(inStr: TypeExpression<StringType>) = ReverseExpression(inStr)

fun reverse(inStr: String): ReverseExpression = reverse(inStr.toDopeType())
