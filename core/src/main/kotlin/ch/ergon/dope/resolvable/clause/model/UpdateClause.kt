package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.clause.IUpdateClause
import ch.ergon.dope.resolvable.fromable.AliasedBucket
import ch.ergon.dope.resolvable.fromable.Bucket

class UpdateClause : IUpdateClause {
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
            queryString = "UPDATE ${resolvableDopeQuery.queryString}",
            parameters = resolvableDopeQuery.parameters,
        )
    }
}
