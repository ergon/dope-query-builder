package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class NowStrExpression(
    private val format: TypeExpression<StringType>? = null,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val formatDopeQuery = format?.toDopeQuery(manager)
        return DopeQuery(
            queryString = toFunctionQueryString("NOW_STR", formatDopeQuery),
            parameters = formatDopeQuery?.parameters.orEmpty(),
        )
    }
}

// todo: DOPE-177
fun nowStr(format: TypeExpression<StringType>? = null) = NowStrExpression(format)

fun nowStr(format: String) = nowStr(format.toDopeType())
