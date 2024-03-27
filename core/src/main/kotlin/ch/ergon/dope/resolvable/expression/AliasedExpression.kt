package ch.ergon.dope.resolvable.expression

import ch.ergon.dope.resolvable.expression.unaliased.type.toBooleanType
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.validtype.ValidType

class AliasedExpression<out T : ValidType>(
    private val unaliasedExpression: UnaliasedExpression<out T>,
    private val alias: String,
) : Expression {
    override fun toQueryString(): String =
        "${unaliasedExpression.toQueryString()} AS $alias"
}

fun <T : ValidType> UnaliasedExpression<T>.alias(string: String) = AliasedExpression(this, string)

fun Number.alias(string: String) = this.toNumberType().alias(string)

fun String.alias(string: String) = this.toStringType().alias(string)

fun Boolean.alias(string: String) = this.toBooleanType().alias(string)
