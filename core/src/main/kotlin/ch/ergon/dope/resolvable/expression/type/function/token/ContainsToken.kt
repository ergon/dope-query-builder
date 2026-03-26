package ch.ergon.dope.resolvable.expression.type.function.token

import ch.ergon.dope.resolvable.Resolvable

data class ContainsTokenOptions(
    val names: Boolean? = null,
    val case: TokenCases? = null,
    val specials: Boolean? = null,
    val split: Boolean? = null,
    val trim: Boolean? = null,
) : Resolvable

fun containsTokenOptions(
    names: Boolean? = null,
    case: TokenCases? = null,
    specials: Boolean? = null,
    split: Boolean? = null,
    trim: Boolean? = null,
) = ContainsTokenOptions(names, case, specials, split, trim)
