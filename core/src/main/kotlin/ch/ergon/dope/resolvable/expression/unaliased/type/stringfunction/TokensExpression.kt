package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.factory.CustomTokenOptions
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

// Argument {"case":"lower"} is optional. Valid values are lower or upper.
// Default is neither, as in it returns the case of the original data.
// Use this option to specify the case sensitivity.
class TokensExpression(
    private val inStr: List<String>,
    private val opt: CustomTokenOptions = CustomTokenOptions(),
) : TypeExpression<StringType>, FunctionOperator {
    override fun toQueryString(): String = toFunctionQueryString(
        symbol = "TOKENS",
        inStr.joinToString(prefix = "[\"", postfix = "\"]"),
        opt.toQueryString(),
    )
}
