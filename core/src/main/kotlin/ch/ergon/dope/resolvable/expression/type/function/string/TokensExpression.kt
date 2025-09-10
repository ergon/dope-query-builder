package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.string.factory.CustomTokenOptions
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.StringType

// Argument {"case":"lower"} is optional. Valid values are lower or upper.
// Default is neither, as in it returns the case of the original data.
// Use this option to specify the case sensitivity.
data class TokensExpression(
    val inStr: List<String>,
    val opt: CustomTokenOptions = CustomTokenOptions(),
) : TypeExpression<ArrayType<StringType>>, FunctionOperator

fun tokens(inStr: List<String>, opt: CustomTokenOptions = CustomTokenOptions()) = TokensExpression(inStr, opt)
