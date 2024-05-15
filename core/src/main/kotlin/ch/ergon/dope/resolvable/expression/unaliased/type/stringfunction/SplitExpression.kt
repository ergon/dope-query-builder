package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class SplitExpression(
    private val inStr: TypeExpression<StringType>,
    private val inSubstring: TypeExpression<StringType>? = null,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val inStrDopeQuery = inStr.toDopeQuery()
        val inSubstringDopeQuery = inSubstring?.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString(symbol = "SPLIT", inStrDopeQuery, extra = inSubstringDopeQuery),
            parameters = inStrDopeQuery.parameters + (inSubstringDopeQuery?.parameters ?: emptyMap()),
        )
    }
}

fun split(inStr: TypeExpression<StringType>, inSubstring: TypeExpression<StringType>? = null) = SplitExpression(inStr, inSubstring)

fun split(inStr: String): SplitExpression = split(inStr.toStringType())

fun split(inStr: TypeExpression<StringType>): SplitExpression = SplitExpression(inStr)

fun split(inStr: String, inSubstring: String? = null): SplitExpression = split(inStr.toStringType(), inSubstring?.toStringType())

fun split(inStr: TypeExpression<StringType>, inSubstring: String? = null): SplitExpression = split(inStr, inSubstring?.toStringType())

fun split(inStr: String, inSubstring: TypeExpression<StringType>? = null) = split(inStr.toStringType(), inSubstring)
