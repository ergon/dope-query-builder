package ch.ergon.dope.resolvable.expression.type.function.string.factory

import ch.ergon.dope.resolvable.Resolvable

enum class TOKEN_CASES { LOWER, UPPER }

data class CustomTokenOptions(var name: Boolean = false, val specials: Boolean = false) : Resolvable {
    var queryString: String

    init {
        queryString = "{\"name\": $name, \"specials\": $specials}"
    }

    constructor(
        name: Boolean = false,
        case: TOKEN_CASES,
        specials: Boolean = false,
    ) : this(name, specials) {
        queryString = "{\"name\": $name, \"case\": \"$case\", \"specials\": $specials}"
    }
}
