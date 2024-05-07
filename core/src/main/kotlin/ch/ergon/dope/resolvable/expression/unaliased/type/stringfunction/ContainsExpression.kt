package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.StringType

class ContainsExpression(
    private val inStr: TypeExpression<StringType>,
    private val searchStr: TypeExpression<StringType>,
) : TypeExpression<BooleanType>, FunctionOperator {
    override fun toQuery(): DopeQuery {
        val inStrDopeQuery = inStr.toQuery()
        val searchStrDopeQuery = searchStr.toQuery()
        return DopeQuery(
            queryString = toFunctionQueryString(symbol = "CONTAINS", inStrDopeQuery, searchStrDopeQuery),
            parameters = inStrDopeQuery.parameters + searchStrDopeQuery.parameters,
        )
    }
}

fun contains(inStr: TypeExpression<StringType>, searchStr: TypeExpression<StringType>): ContainsExpression = ContainsExpression(inStr, searchStr)

fun contains(inStr: String, searchStr: String): ContainsExpression = contains(inStr.toStringType(), searchStr.toStringType())

fun contains(inStr: TypeExpression<StringType>, searchStr: String): ContainsExpression = contains(inStr, searchStr.toStringType())

fun contains(inStr: String, searchStr: TypeExpression<StringType>): ContainsExpression = contains(inStr.toStringType(), searchStr)
