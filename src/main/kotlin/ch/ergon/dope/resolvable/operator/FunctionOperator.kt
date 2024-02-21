package ch.ergon.dope.resolvable.operator

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ValidType

interface FunctionOperator {
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
