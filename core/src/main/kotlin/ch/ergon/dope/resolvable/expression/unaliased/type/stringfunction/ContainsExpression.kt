package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.StringType

class ContainsExpression(
    private val inStr: TypeExpression<StringType>,
    private val searchStr: TypeExpression<StringType>,
) : TypeExpression<BooleanType>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val inStrDopeQuery = inStr.toDopeQuery()
        val searchStrDopeQuery = searchStr.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString(symbol = "CONTAINS", inStrDopeQuery, searchStrDopeQuery),
            parameters = inStrDopeQuery.parameters + searchStrDopeQuery.parameters,
        )
    }
}

fun contains(inStr: TypeExpression<StringType>, searchStr: TypeExpression<StringType>) = ContainsExpression(inStr, searchStr)

fun contains(inStr: String, searchStr: String) = contains(inStr.toDopeType(), searchStr.toDopeType())

fun contains(inStr: TypeExpression<StringType>, searchStr: String) = contains(inStr, searchStr.toDopeType())

fun contains(inStr: String, searchStr: TypeExpression<StringType>) = contains(inStr.toDopeType(), searchStr)
