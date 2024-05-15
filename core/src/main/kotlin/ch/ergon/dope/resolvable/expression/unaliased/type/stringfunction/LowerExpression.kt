package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class LowerExpression(
    private val inStr: TypeExpression<StringType>,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val inStrDopeQuery = inStr.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString(symbol = "LOWER", inStrDopeQuery),
            parameters = inStrDopeQuery.parameters,
        )
    }
}

fun lower(inStr: TypeExpression<StringType>): LowerExpression = LowerExpression(inStr)

fun lower(inStr: String): LowerExpression = lower(inStr.toStringType())
