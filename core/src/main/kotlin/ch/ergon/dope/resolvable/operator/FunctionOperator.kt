package ch.ergon.dope.resolvable.operator

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.isBlank
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

interface FunctionOperator {
    fun toFunctionQueryString(symbol: String, vararg arguments: TypeExpression<out ValidType>, extra: TypeExpression<StringType>) =
        if (extra.isBlank()) {
            toFunctionQueryString(symbol = symbol, *arguments)
        } else {
            toFunctionQueryString(symbol = symbol, *arguments, extra)
        }

    fun toFunctionQueryString(symbol: String, vararg arguments: TypeExpression<out ValidType>) = arguments.joinToString(
        ", ",
        prefix = "$symbol(",
        postfix = ")",
    ) {
        it.toQueryString()
    }

    fun toFunctionQueryString(symbol: String, vararg arguments: String) = arguments.joinToString(
        ", ",
        prefix = "$symbol(",
        postfix = ")",
    )
}
