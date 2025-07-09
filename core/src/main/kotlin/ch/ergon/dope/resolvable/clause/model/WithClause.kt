package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectWithClause
import ch.ergon.dope.resolvable.expression.type.DopeVariable
import ch.ergon.dope.validtype.ValidType

const val WITH = "WITH"

class WithClause(
    private val withExpression: DopeVariable<out ValidType>,
    private vararg val additionalWithExpressions: DopeVariable<out ValidType>,
) : ISelectWithClause {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val withDopeQuery = withExpression.toWithDefinitionDopeQuery(manager)
        val withDopeQueries = additionalWithExpressions.map { it.toWithDefinitionDopeQuery(manager) }.toTypedArray()
        return DopeQuery(
            queryString = "$WITH ${listOf(withDopeQuery, *withDopeQueries).joinToString(separator = ", ") { it.queryString }}",
            parameters = withDopeQuery.parameters.merge(*withDopeQueries.map { it.parameters }.toTypedArray()),
        )
    }
}
