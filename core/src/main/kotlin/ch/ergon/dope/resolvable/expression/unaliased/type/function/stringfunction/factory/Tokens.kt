package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.factory

import ch.ergon.dope.DopeQuery

enum class TOKEN_CASES { LOWER, UPPER }

data class CustomTokenOptions(var name: Boolean = false, private val specials: Boolean = false) {
    private var queryString: String

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

    fun toDopeQuery() = DopeQuery(queryString, emptyMap(), emptyList())
}
