package ch.ergon.dope.resolvable.expression.unaliased.type.function

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.ValidType

abstract class FunctionExpression<T : ValidType>(
    private val symbol: String,
    private vararg val expressions: UnaliasedExpression<T>,
) : TypeExpression<T>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val expressionsDopeQuery = expressions.map {
            when (it) {
                is ISelectOffsetClause<*> -> it.asSelectWithParentheses().toDopeQuery(manager)
                else -> it.toDopeQuery(manager)
            }
        }
        return DopeQuery(
            queryString = toFunctionQueryString(
                symbol,
                *expressionsDopeQuery.toTypedArray(),
            ),
            parameters = expressionsDopeQuery.fold(
                emptyMap(),
            ) { expressionParameters, expression -> expressionParameters + expression.parameters },
        )
    }
}
