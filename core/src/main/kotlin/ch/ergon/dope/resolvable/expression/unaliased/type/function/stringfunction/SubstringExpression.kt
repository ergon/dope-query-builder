package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class SubstringExpression(
    private val inStr: TypeExpression<StringType>,
    private val startPos: Int,
    private val length: Int? = null,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val inStrDopeQuery = inStr.toDopeQuery(manager)
        return DopeQuery(
            queryString = toFunctionQueryString(
                symbol = "SUBSTR",
                inStrDopeQuery,
                startPos.toDopeType().toDopeQuery(manager),
                length?.toDopeType()?.toDopeQuery(manager),
            ),
            parameters = inStrDopeQuery.parameters,
            positionalParameters = inStrDopeQuery.positionalParameters,
        )
    }
}

fun substr(inStr: TypeExpression<StringType>, startPos: Int, length: Int? = null) = SubstringExpression(inStr, startPos, length)

fun substr(inStr: String, startPos: Int, length: Int? = null) = substr(inStr.toDopeType(), startPos, length)
