package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.factory.CustomTokenOptions
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

// Argument {"case":"lower"} is optional. Valid values are lower or upper.
// Default is neither, as in it returns the case of the original data.
// Use this option to specify the case sensitivity.
class TokensExpression(
    private val inStr: List<String>,
    private val opt: CustomTokenOptions = CustomTokenOptions(),
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val optDopeQuery = opt.toDopeQuery(manager)
        return DopeQuery(
            queryString = toFunctionQueryString(
                symbol = "TOKENS",
                inStr.joinToString(prefix = "[\"", postfix = "\"]"),
                optDopeQuery.queryString,
            ),
            parameters = optDopeQuery.parameters,
        )
    }
}
