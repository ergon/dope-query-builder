package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class PositionExpression(
    private val inStr: TypeExpression<StringType>,
    private val searchStr: TypeExpression<StringType>,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toQuery(): DopeQuery {
        val inStrDopeQuery = inStr.toQuery()
        val searchStrDopeQuery = searchStr.toQuery()
        return DopeQuery(
            queryString = toFunctionQueryString(symbol = "POSITION", inStrDopeQuery, searchStrDopeQuery),
            parameters = inStrDopeQuery.parameters + searchStrDopeQuery.parameters,
        )
    }
}

fun position(inStr: TypeExpression<StringType>, searchStr: TypeExpression<StringType>): PositionExpression =
    PositionExpression(inStr, searchStr)

fun position(inStr: String, searchStr: String): PositionExpression = position(inStr.toStringType(), searchStr.toStringType())

fun position(inStr: TypeExpression<StringType>, searchStr: String): PositionExpression = position(inStr, searchStr.toStringType())

fun position(inStr: String, searchStr: TypeExpression<StringType>): PositionExpression = position(inStr.toStringType(), searchStr)
