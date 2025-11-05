package ch.ergon.dope.resolvable.expression.type.function.string.factory

import ch.ergon.dope.resolvable.Resolvable

enum class TokenCases { LOWER, UPPER }

data class CustomTokenOptions(
    var name: Boolean = false,
    val case: TokenCases? = null,
    val specials: Boolean = false,
) : Resolvable
