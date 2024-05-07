package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class SubstringExpression(
    private val inStr: TypeExpression<StringType>,
    private val startPos: Int,
    private val length: Int,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toQuery(): DopeQuery {
        val inStrDopeQuery = inStr.toQuery()
        return DopeQuery(
            queryString = toFunctionQueryString(
                symbol = "SUBSTR",
                inStrDopeQuery,
                startPos.toNumberType().toQuery(),
                length.toNumberType().toQuery(),
            ),
            parameters = inStrDopeQuery.parameters,
        )
    }
}

fun substr(inStr: TypeExpression<StringType>, startPos: Int, length: Int = inStr.toQuery().queryString.length) =
    SubstringExpression(inStr, startPos, length)

fun substr(inStr: String, startPos: Int, length: Int = inStr.length): SubstringExpression = substr(inStr.toStringType(), startPos, length)
