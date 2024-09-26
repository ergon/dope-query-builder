package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class InitCapExpression(
    private val inStr: TypeExpression<StringType>,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val inStrDopeQuery = inStr.toDopeQuery(manager)
        return DopeQuery(
            queryString = toFunctionQueryString(symbol = "INITCAP", inStrDopeQuery),
            parameters = inStrDopeQuery.parameters,
            positionalParameters = inStrDopeQuery.positionalParameters,
        )
    }
}

fun initCap(inStr: TypeExpression<StringType>) = InitCapExpression(inStr)

fun initCap(inStr: String) = initCap(inStr.toDopeType())

class TitleExpression(
    private val inStr: TypeExpression<StringType>,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val inStrDopeQuery = inStr.toDopeQuery(manager)
        return DopeQuery(
            queryString = toFunctionQueryString(symbol = "TITLE", inStrDopeQuery),
            parameters = inStrDopeQuery.parameters,
            positionalParameters = inStrDopeQuery.positionalParameters,
        )
    }
}

fun title(inStr: TypeExpression<StringType>) = TitleExpression(inStr)

fun title(inStr: String) = title(inStr.toDopeType())
