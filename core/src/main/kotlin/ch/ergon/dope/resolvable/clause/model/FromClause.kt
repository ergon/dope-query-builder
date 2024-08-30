package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.clause.ISelectClause
import ch.ergon.dope.resolvable.clause.ISelectUnnestClause
import ch.ergon.dope.resolvable.formatToQueryStringWithSymbol
import ch.ergon.dope.resolvable.fromable.AliasedBucket
import ch.ergon.dope.resolvable.fromable.Fromable

class FromClause : ISelectUnnestClause {
    private val resolvable: Resolvable
    private val parentClause: ISelectClause

    constructor(fromable: Fromable, parentClause: ISelectClause) {
        this.resolvable = fromable
        this.parentClause = parentClause
    }

    constructor(aliasedBucket: AliasedBucket, parentClause: ISelectClause) {
        this.resolvable = aliasedBucket.addBucketReference()
        this.parentClause = parentClause
    }

    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val parentDopeQuery = parentClause.toDopeQuery(manager)
        val resolvableDopeQuery = resolvable.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(parentDopeQuery.queryString, "FROM", resolvableDopeQuery.queryString),
            parameters = resolvableDopeQuery.parameters + parentDopeQuery.parameters,
        )
    }
}
