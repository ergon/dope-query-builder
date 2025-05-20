package ch.ergon.dope.resolvable.expression.type.function

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.merge
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ValidType

abstract class FunctionExpression<T : ValidType>(
    private val symbol: String,
    private vararg val expressions: Resolvable?,
) : TypeExpression<T>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val expressionsDopeQuery = expressions.mapNotNull { it?.toDopeQuery(manager) }
        return DopeQuery(
            queryString = toFunctionQueryString(symbol, *expressionsDopeQuery.toTypedArray()),
            parameters = expressionsDopeQuery.map { it.parameters }.merge(),
        )
    }
}
