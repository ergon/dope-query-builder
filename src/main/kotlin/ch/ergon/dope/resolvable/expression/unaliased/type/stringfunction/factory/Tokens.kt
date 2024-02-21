package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.factory

import ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction.TokensExpression

enum class TOKEN_CASES { LOWER, UPPER }

data class CustomTokenOptions(var name: Boolean = false, private val specials: Boolean = false) {
    private var customToString: () -> String

    init {
        customToString = {
            "{\"name\": $name, \"specials\": $specials}"
        }
    }

    constructor(
        name: Boolean = false,
        case: TOKEN_CASES,
        specials: Boolean = false,
    ) : this(name, specials) {
        customToString = {
            "{\"name\": $name, \"case\": \"$case\", \"specials\": $specials}"
        }
    }

    fun toQueryString(): String = customToString()
}

fun tokens(inStr: List<String>): TokensExpression = TokensExpression(inStr)

fun tokens(inStr: List<String>, opt: CustomTokenOptions): TokensExpression = TokensExpression(inStr, opt)
