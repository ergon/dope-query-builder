package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.factory

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.TokensExpression

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

    fun toDopeQuery(manager: DopeQueryManager) = DopeQuery(queryString, emptyMap(), manager)
}

fun tokens(inStr: List<String>, opt: CustomTokenOptions = CustomTokenOptions()) = TokensExpression(inStr, opt)
