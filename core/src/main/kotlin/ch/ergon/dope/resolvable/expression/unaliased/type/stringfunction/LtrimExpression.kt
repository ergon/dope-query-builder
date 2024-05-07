package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class LtrimExpression(
    private val inStr: TypeExpression<StringType>,
    private val extra: TypeExpression<StringType>? = null,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toQuery(): DopeQuery {
        val inStrDopeQuery = inStr.toQuery()
        val extraDopeQuery = extra?.toQuery()
        return DopeQuery(
            queryString = toFunctionQueryString(symbol = "LTRIM", inStrDopeQuery, extra = extraDopeQuery),
            parameters = inStrDopeQuery.parameters + (extraDopeQuery?.parameters ?: emptyMap()),
        )
    }
}

fun ltrim(inStr: TypeExpression<StringType>, extra: TypeExpression<StringType>? = null): LtrimExpression =
    LtrimExpression(inStr, extra)

fun ltrim(inStr: TypeExpression<StringType>, extra: String): LtrimExpression = ltrim(inStr, extra.toStringType())

fun ltrim(inStr: String, extra: TypeExpression<StringType>): LtrimExpression = ltrim(inStr.toStringType(), extra)

fun ltrim(inStr: String, extra: String): LtrimExpression = ltrim(inStr.toStringType(), extra.toStringType())

fun ltrim(inStr: String): LtrimExpression = ltrim(inStr.toStringType())
