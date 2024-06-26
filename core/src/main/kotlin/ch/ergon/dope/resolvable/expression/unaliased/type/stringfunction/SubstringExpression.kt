package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class SubstringExpression(
    private val inStr: TypeExpression<StringType>,
    private val startPos: Int,
    private val length: Int,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val inStrDopeQuery = inStr.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString(
                symbol = "SUBSTR",
                inStrDopeQuery,
                startPos.toDopeType().toDopeQuery(),
                length.toDopeType().toDopeQuery(),
            ),
            parameters = inStrDopeQuery.parameters,
        )
    }
}

fun substr(inStr: TypeExpression<StringType>, startPos: Int, length: Int = inStr.toDopeQuery().queryString.length) =
    SubstringExpression(inStr, startPos, length)

fun substr(inStr: String, startPos: Int, length: Int = inStr.length): SubstringExpression = substr(inStr.toDopeType(), startPos, length)
