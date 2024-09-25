package ch.ergon.dope.resolvable.expression

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.formatToQueryStringWithSymbol
import ch.ergon.dope.validtype.ValidType

class AliasedExpression<T : ValidType>(
    private val unaliasedExpression: UnaliasedExpression<T>,
    private val alias: String,
) : SingleExpression {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val unaliasedExpressionDopeQuery = unaliasedExpression.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(unaliasedExpressionDopeQuery.queryString, "AS", "`$alias`"),
            parameters = unaliasedExpressionDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> UnaliasedExpression<T>.alias(string: String): AliasedExpression<T> = AliasedExpression(this, string)

fun Number.alias(string: String) = toDopeType().alias(string)

fun String.alias(string: String) = toDopeType().alias(string)

fun Boolean.alias(string: String) = toDopeType().alias(string)

fun <K, V> Map<K, V>.alias(string: String) = toDopeType().alias(string)
