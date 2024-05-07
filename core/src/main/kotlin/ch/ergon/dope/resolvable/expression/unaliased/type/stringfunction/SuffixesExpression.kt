package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class SuffixesExpression(
    private val inStr: TypeExpression<StringType>,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toQuery(): DopeQuery {
        val inStrDopeQuery = inStr.toQuery()
        return DopeQuery(
            queryString = toFunctionQueryString(symbol = "SUFFIXES", inStrDopeQuery),
            parameters = inStrDopeQuery.parameters,
        )
    }
}

fun suffixes(inStr: TypeExpression<StringType>) = SuffixesExpression(inStr)

fun suffixes(inStr: String): SuffixesExpression = suffixes(inStr.toStringType())
