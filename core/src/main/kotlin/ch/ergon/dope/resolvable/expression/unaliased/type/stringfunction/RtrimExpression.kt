package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class RtrimExpression(
    private val inStr: TypeExpression<StringType>,
    private val extra: TypeExpression<StringType>? = null,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val inStrDopeQuery = inStr.toDopeQuery()
        val extraDopeQuery = extra?.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString(symbol = "RTRIM", inStrDopeQuery, extra = extraDopeQuery),
            parameters = inStrDopeQuery.parameters + extraDopeQuery?.parameters.orEmpty(),
        )
    }
}

fun rtrim(inStr: TypeExpression<StringType>, extra: TypeExpression<StringType>? = null) = RtrimExpression(inStr, extra)

fun rtrim(inStr: TypeExpression<StringType>, extra: String) = rtrim(inStr, extra.toDopeType())

fun rtrim(inStr: String, extra: TypeExpression<StringType>? = null) = rtrim(inStr.toDopeType(), extra)

fun rtrim(inStr: String, extra: Char) = rtrim(inStr.toDopeType(), extra.toString().toDopeType())

fun rtrim(inStr: String, extra: String) = rtrim(inStr.toDopeType(), extra.toDopeType())

fun rtrim(inStr: TypeExpression<StringType>, extra: Char) = rtrim(inStr, extra.toString().toDopeType())
