package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
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

fun rtrim(inStr: TypeExpression<StringType>, extra: String) = rtrim(inStr, extra.toStringType())

fun rtrim(inStr: String, extra: TypeExpression<StringType>? = null) = rtrim(inStr.toStringType(), extra)

fun rtrim(inStr: String, extra: Char) = rtrim(inStr.toStringType(), extra.toString().toStringType())

fun rtrim(inStr: String, extra: String) = rtrim(inStr.toStringType(), extra.toStringType())

fun rtrim(inStr: TypeExpression<StringType>, extra: Char) = rtrim(inStr, extra.toString().toStringType())
