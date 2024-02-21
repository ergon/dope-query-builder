package ch.ergon.dope.resolvable.expression

import ch.ergon.dope.validtype.ValidType

class AliasedExpression<out T : ValidType>(
    private val unaliasedExpression: UnaliasedExpression<out T>,
    private val alias: String,
) : Expression {
    override fun toQueryString(): String =
        "${unaliasedExpression.toQueryString()} AS $alias"
}

fun <T : ValidType> UnaliasedExpression<T>.alias(string: String) = AliasedExpression(this, string)
