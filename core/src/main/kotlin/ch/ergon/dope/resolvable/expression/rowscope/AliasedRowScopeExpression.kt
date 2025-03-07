package ch.ergon.dope.resolvable.expression.rowscope

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.util.formatToQueryStringWithSymbol
import ch.ergon.dope.validtype.ValidType

class AliasedRowScopeExpression<T : ValidType>(
    private val rowScopeExpression: RowScopeExpression<T>,
    private val alias: String,
) : Expression<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val rowScopeExpressionDopeQuery = rowScopeExpression.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(
                rowScopeExpressionDopeQuery.queryString,
                symbol = "AS",
                "`$alias`",
            ),
            parameters = rowScopeExpressionDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> RowScopeExpression<T>.alias(alias: String) = AliasedRowScopeExpression(this, alias)
