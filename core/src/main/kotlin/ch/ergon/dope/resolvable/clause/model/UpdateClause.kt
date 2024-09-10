package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.IUpdateClause
import ch.ergon.dope.resolvable.fromable.AliasedBucket
import ch.ergon.dope.resolvable.fromable.Bucket

class UpdateClause(private val bucket: Bucket) : IUpdateClause {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val resolvableDopeQuery = when (bucket) {
            is AliasedBucket -> bucket.asBucketDefinition().toDopeQuery(manager)
            else -> bucket.toDopeQuery(manager)
        }
        return DopeQuery(
            queryString = "UPDATE ${resolvableDopeQuery.queryString}",
            parameters = resolvableDopeQuery.parameters,
        )
    }
}
