package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class PositionExpression(
    private val inStr: TypeExpression<StringType>,
    private val searchStr: TypeExpression<StringType>,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val inStrDopeQuery = inStr.toDopeQuery()
        val searchStrDopeQuery = searchStr.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString(symbol = "POSITION", inStrDopeQuery, searchStrDopeQuery),
            parameters = inStrDopeQuery.parameters + searchStrDopeQuery.parameters,
        )
    }
}

fun position(inStr: TypeExpression<StringType>, searchStr: TypeExpression<StringType>) =
    PositionExpression(inStr, searchStr)

fun position(inStr: TypeExpression<StringType>, searchStr: String) = position(inStr, searchStr.toDopeType())

fun position(inStr: String, searchStr: TypeExpression<StringType>) = position(inStr.toDopeType(), searchStr)

fun position(inStr: String, searchStr: String) = position(inStr.toDopeType(), searchStr.toDopeType())
