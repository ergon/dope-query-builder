package ch.ergon.dope.resolvable.fromable

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ValidType

class AliasedSelectClause<T : ValidType>(
    private val alias: String,
    private val parentClause: ISelectOffsetClause<T>,
) : Fromable, Joinable, TypeExpression<ArrayType<T>> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery =
        DopeQuery(
            queryString = "`$alias`",
        )

    fun asAliasedSelectClauseDefinition() = AliasedSelectClauseDefinition(alias, parentClause)
}

class AliasedSelectClauseDefinition<T : ValidType>(
    private val alias: String,
    private val parentClause: ISelectOffsetClause<T>,
) : Fromable, Joinable, TypeExpression<T> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentClauseDopeQuery = parentClause.toDopeQuery(manager)
        return DopeQuery(
            queryString = "(${parentClauseDopeQuery.queryString}) AS `$alias`",
            parameters = parentClauseDopeQuery.parameters,
        )
    }
}
