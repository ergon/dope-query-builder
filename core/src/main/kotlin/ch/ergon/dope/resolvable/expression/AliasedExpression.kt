package ch.ergon.dope.resolvable.expression

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.unaliased.type.toBooleanType
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.validtype.ValidType

class AliasedExpression<T : ValidType>(
    private val unaliasedExpression: UnaliasedExpression<T>,
    private val alias: String,
) : Expression {
    override fun toQuery(): DopeQuery {
        val unaliasedExpressionDopeQuery = unaliasedExpression.toQuery()
        return DopeQuery(
            queryString = formatToQueryString(unaliasedExpressionDopeQuery.queryString, "AS", alias),
            parameters = unaliasedExpressionDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> UnaliasedExpression<T>.alias(string: String): AliasedExpression<T> = AliasedExpression(this, string)

fun Number.alias(string: String) = this.toNumberType().alias(string)

fun String.alias(string: String) = this.toStringType().alias(string)

fun Boolean.alias(string: String) = this.toBooleanType().alias(string)
