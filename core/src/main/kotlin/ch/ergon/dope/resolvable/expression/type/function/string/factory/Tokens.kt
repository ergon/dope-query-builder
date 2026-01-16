package ch.ergon.dope.resolvable.expression.type.function.string.factory

import ch.ergon.dope.resolvable.Resolvable

enum class TokenCases { LOWER, UPPER }

data class CustomTokenOptions(
    val name: Boolean? = null,
    val case: TokenCases? = null,
    val specials: Boolean? = null,
) : Resolvable

fun customTokenOptions(
    name: Boolean? = null,
    case: TokenCases? = null,
    specials: Boolean? = null,
) = CustomTokenOptions(name, case, specials)
