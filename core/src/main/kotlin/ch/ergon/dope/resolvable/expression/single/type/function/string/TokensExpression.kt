package ch.ergon.dope.resolvable.expression.single.type.function.string

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.string.factory.CustomTokenOptions
import ch.ergon.dope.util.formatStringListToQueryStringWithBrackets
import ch.ergon.dope.util.operator.FunctionOperator
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.StringType

// Argument {"case":"lower"} is optional. Valid values are lower or upper.
// Default is neither, as in it returns the case of the original data.
// Use this option to specify the case sensitivity.
class TokensExpression(
    private val inStr: List<String>,
    private val opt: CustomTokenOptions = CustomTokenOptions(),
) : TypeExpression<ArrayType<StringType>>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val optDopeQuery = opt.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString(
                symbol = "TOKENS",
                formatStringListToQueryStringWithBrackets(inStr, prefix = "[\"", postfix = "\"]"),
                optDopeQuery.queryString,
            ),
            parameters = optDopeQuery.parameters,
        )
    }
}

fun tokens(inStr: List<String>, opt: CustomTokenOptions = CustomTokenOptions()) = TokensExpression(inStr, opt)
