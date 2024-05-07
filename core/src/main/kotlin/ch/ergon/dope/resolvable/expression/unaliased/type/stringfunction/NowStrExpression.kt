package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class NowStrExpression(
    private val format: TypeExpression<StringType>? = null,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toQuery(): DopeQuery {
        val formatDopeQuery = format?.toQuery()
        return DopeQuery(
            queryString = toFunctionQueryString("NOW_STR", extra = formatDopeQuery),
            parameters = formatDopeQuery?.parameters ?: emptyMap(),
        )
    }
}

// todo: DOPE-177
fun nowStr(format: TypeExpression<StringType>? = null): NowStrExpression = NowStrExpression(format)

fun nowStr(format: String): NowStrExpression = nowStr(format.toStringType())
