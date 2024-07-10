package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class LtrimExpression(
    private val inStr: TypeExpression<StringType>,
    private val extra: TypeExpression<StringType>? = null,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val inStrDopeQuery = inStr.toDopeQuery()
        val extraDopeQuery = extra?.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString(symbol = "LTRIM", inStrDopeQuery, extra = extraDopeQuery),
            parameters = inStrDopeQuery.parameters + extraDopeQuery?.parameters.orEmpty(),
        )
    }
}

fun ltrim(inStr: TypeExpression<StringType>, extra: TypeExpression<StringType>? = null) =
    LtrimExpression(inStr, extra)

fun ltrim(inStr: TypeExpression<StringType>, extra: String) = ltrim(inStr, extra.toDopeType())

fun ltrim(inStr: String, extra: TypeExpression<StringType>) = ltrim(inStr.toDopeType(), extra)

fun ltrim(inStr: String, extra: String) = ltrim(inStr.toDopeType(), extra.toDopeType())

fun ltrim(inStr: String) = ltrim(inStr.toDopeType())
