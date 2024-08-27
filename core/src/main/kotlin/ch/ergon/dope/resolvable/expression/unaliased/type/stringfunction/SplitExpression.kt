package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class SplitExpression(
    private val inStr: TypeExpression<StringType>,
    private val inSubstring: TypeExpression<StringType>? = null,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val inStrDopeQuery = inStr.toDopeQuery(manager)
        val inSubstringDopeQuery = inSubstring?.toDopeQuery(manager)
        return DopeQuery(
            queryString = toFunctionQueryString(symbol = "SPLIT", inStrDopeQuery, inSubstringDopeQuery),
            parameters = inStrDopeQuery.parameters + inSubstringDopeQuery?.parameters.orEmpty(),
        )
    }
}

fun split(inStr: TypeExpression<StringType>, inSubstring: TypeExpression<StringType>? = null) = SplitExpression(inStr, inSubstring)

fun split(inStr: TypeExpression<StringType>, inSubstring: String) = split(inStr, inSubstring.toDopeType())

fun split(inStr: String, inSubstring: TypeExpression<StringType>? = null) = split(inStr.toDopeType(), inSubstring)

fun split(inStr: String, inSubstring: String) = split(inStr.toDopeType(), inSubstring.toDopeType())
