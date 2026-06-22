package ch.ergon.dope.resolvable.expression.type.function.token

import ch.ergon.dope.resolvable.Resolvable

enum class TokenCase { LOWER, UPPER }

data class TokensOptions(
    val hasName: Boolean? = null,
    val case: TokenCase? = null,
    val includeSpecialCharacters: Boolean? = null,
) : Resolvable

fun tokenOptions(
    hasName: Boolean? = null,
    case: TokenCase? = null,
    includeSpecialCharacters: Boolean? = null,
) = TokensOptions(hasName, case, includeSpecialCharacters)
