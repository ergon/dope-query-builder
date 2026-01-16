package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.function.string.factory.CustomTokenOptions
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.StringType

data class TokensExpression(
    val inStr: List<String>,
    val options: CustomTokenOptions? = null,
) : FunctionOperator<ArrayType<StringType>>

fun tokens(inStr: List<String>, options: CustomTokenOptions? = null) = TokensExpression(inStr, options)
