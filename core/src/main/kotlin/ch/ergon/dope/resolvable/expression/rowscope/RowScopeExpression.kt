package ch.ergon.dope.resolvable.expression.rowscope

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.merge
import ch.ergon.dope.resolvable.Selectable
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier
import ch.ergon.dope.resolvable.expression.rowscope.windowdefinition.OverDefinition
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.FromModifier
import ch.ergon.dope.resolvable.expression.rowscope.windowfunction.NullsModifier
import ch.ergon.dope.validtype.ValidType

interface RowScopeExpression<T : ValidType> : Expression<T> {
    val functionName: String
    val quantifier: AggregateQuantifier?
    val functionArguments: List<Selectable?>?
    val fromModifier: FromModifier?
    val nullsModifier: NullsModifier?
    val overDefinition: OverDefinition?

    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val functionArgumentsDopeQuery = functionArguments?.mapNotNull { it?.toDopeQuery(manager) }
        val overClauseDopeQuery = overDefinition?.toDopeQuery(manager)
        val functionArgumentsQueryString = functionArgumentsDopeQuery.orEmpty().joinToString(
            separator = ", ",
            prefix = "(" + quantifier?.let { "${it.queryString} " }.orEmpty(),
            postfix = ")",
        ) { it.queryString }

        return DopeQuery(
            queryString = functionName +
                functionArgumentsQueryString +
                fromModifier?.let { " ${it.queryString}" }.orEmpty() +
                nullsModifier?.let { " ${it.queryString}" }.orEmpty() +
                overClauseDopeQuery?.let { " ${it.queryString}" }.orEmpty(),
            parameters = functionArgumentsDopeQuery?.map { it.parameters }.orEmpty().merge(overClauseDopeQuery?.parameters),
        )
    }
}
