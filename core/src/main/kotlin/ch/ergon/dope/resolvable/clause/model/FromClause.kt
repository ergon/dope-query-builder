package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.AliasedSelectClause
import ch.ergon.dope.resolvable.Fromable
import ch.ergon.dope.resolvable.bucket.AliasedBucket
import ch.ergon.dope.resolvable.clause.ISelectClause
import ch.ergon.dope.resolvable.clause.ISelectUnnestClause
import ch.ergon.dope.util.formatToQueryStringWithSymbol
import ch.ergon.dope.validtype.ValidType

class FromClause<T : ValidType>(private val fromable: Fromable, private val parentClause: ISelectClause<T>) : ISelectUnnestClause<T> {

    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery(manager)
        val fromableDopeQuery = when (fromable) {
            is AliasedBucket -> fromable.asBucketDefinition().toDopeQuery(manager)
            is AliasedSelectClause<*> -> fromable.asAliasedSelectClauseDefinition().toDopeQuery(manager)
            else -> fromable.toDopeQuery(manager)
        }
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(parentDopeQuery.queryString, "FROM", fromableDopeQuery.queryString),
            parameters = parentDopeQuery.parameters.merge(fromableDopeQuery.parameters),
        )
    }
}
