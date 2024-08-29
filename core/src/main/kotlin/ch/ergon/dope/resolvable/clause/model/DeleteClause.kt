package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.clause.IDeleteClause
import ch.ergon.dope.resolvable.fromable.AliasedBucket
import ch.ergon.dope.resolvable.fromable.Bucket

class DeleteClause : IDeleteClause {
    private val resolvable: Resolvable

    constructor(bucket: Bucket) {
        this.resolvable = bucket
    }

    constructor(aliasedBucket: AliasedBucket) {
        this.resolvable = aliasedBucket.addBucketReference()
    }

    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val resolvableDopeQuery = resolvable.toDopeQuery(manager)
        return DopeQuery(
            queryString = "DELETE FROM ${resolvableDopeQuery.queryString}",
            parameters = resolvableDopeQuery.parameters,
        )
    }
}
