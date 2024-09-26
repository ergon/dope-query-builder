package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.StringType

class ContainsExpression(
    private val inStr: TypeExpression<StringType>,
    private val searchStr: TypeExpression<StringType>,
) : TypeExpression<BooleanType>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val inStrDopeQuery = inStr.toDopeQuery(manager)
        val searchStrDopeQuery = searchStr.toDopeQuery(manager)
        return DopeQuery(
            queryString = toFunctionQueryString(symbol = "CONTAINS", inStrDopeQuery, searchStrDopeQuery),
            parameters = inStrDopeQuery.parameters + searchStrDopeQuery.parameters,
            positionalParameters = inStrDopeQuery.positionalParameters + searchStrDopeQuery.positionalParameters,
        )
    }
}

fun contains(inStr: TypeExpression<StringType>, searchStr: TypeExpression<StringType>) = ContainsExpression(inStr, searchStr)

fun contains(inStr: String, searchStr: String) = contains(inStr.toDopeType(), searchStr.toDopeType())

fun contains(inStr: TypeExpression<StringType>, searchStr: String) = contains(inStr, searchStr.toDopeType())

fun contains(inStr: String, searchStr: TypeExpression<StringType>) = contains(inStr.toDopeType(), searchStr)
