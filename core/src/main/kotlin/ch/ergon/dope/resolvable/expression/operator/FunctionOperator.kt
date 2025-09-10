package ch.ergon.dope.resolvable.expression.operator

interface FunctionOperator {
    fun toFunctionQueryString(symbol: String, vararg arguments: String?): String =
        arguments.filterNotNull().joinToString(prefix = "$symbol(", postfix = ")")
}
