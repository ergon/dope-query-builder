package ch.ergon.dope.resolvable.expression.type.function.token

import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.StringType

data class TokensExpression(
    val inString: TypeExpression<ArrayType<StringType>>,
    val tokensOptions: TokensOptions? = null,
) : FunctionOperator<ArrayType<StringType>>

fun TypeExpression<ArrayType<StringType>>.tokenize(tokensOptions: TokensOptions) = TokensExpression(this, tokensOptions)

fun TypeExpression<ArrayType<StringType>>.tokenize(
    hasName: Boolean? = null,
    case: TokenCase? = null,
    includeSpecialCharacters: Boolean? = null,
) = TokensExpression(this, tokenOptions(hasName, case, includeSpecialCharacters))

fun List<String>.tokenize(tokensOptions: TokensOptions) = toDopeType().tokenize(tokensOptions)

fun List<String>.tokenize(
    hasName: Boolean? = null,
    case: TokenCase? = null,
    includeSpecialCharacters: Boolean? = null,
) = toDopeType().tokenize(tokenOptions(hasName, case, includeSpecialCharacters))
