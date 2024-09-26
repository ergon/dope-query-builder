package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.factory

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.TokensExpression

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

fun tokens(inStr: List<String>, opt: CustomTokenOptions = CustomTokenOptions()) = TokensExpression(inStr, opt)
