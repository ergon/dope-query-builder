package ch.ergon.dope.resolvable.expression.type.function.token

import ch.ergon.dope.resolvable.Resolvable

data class ContainsTokenOptions(
    val hasNames: Boolean? = null,
    val case: TokenCase? = null,
    val includeSpecialCharacters: Boolean? = null,
    val split: Boolean? = null,
    val trim: Boolean? = null,
) : Resolvable

fun containsTokenOptions(
    hasNames: Boolean? = null,
    case: TokenCase? = null,
    includeSpecialCharacters: Boolean? = null,
    split: Boolean? = null,
    trim: Boolean? = null,
) = ContainsTokenOptions(hasNames, case, includeSpecialCharacters, split, trim)
