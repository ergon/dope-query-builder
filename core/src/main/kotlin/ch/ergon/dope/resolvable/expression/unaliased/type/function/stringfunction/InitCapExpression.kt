package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class InitCapExpression(
    private val inStr: TypeExpression<StringType>,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val inStrDopeQuery = inStr.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString(symbol = "INITCAP", inStrDopeQuery),
            parameters = inStrDopeQuery.parameters,
        )
    }
}

fun initCap(inStr: TypeExpression<StringType>) = InitCapExpression(inStr)

fun initCap(inStr: String) = initCap(inStr.toDopeType())

class TitleExpression(
    private val inStr: TypeExpression<StringType>,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val inStrDopeQuery = inStr.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString(symbol = "TITLE", inStrDopeQuery),
            parameters = inStrDopeQuery.parameters,
        )
    }
}

fun title(inStr: TypeExpression<StringType>) = TitleExpression(inStr)

fun title(inStr: String) = title(inStr.toDopeType())
