package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.IDeleteClause
import ch.ergon.dope.resolvable.fromable.AliasedBucket
import ch.ergon.dope.resolvable.fromable.Bucket

class DeleteClause(private val bucket: Bucket) : IDeleteClause {

    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val bucketDopeQuery = when (bucket) {
            is AliasedBucket -> bucket.asBucketDefinition().toDopeQuery(manager)
            else -> bucket.toDopeQuery(manager)
        }
        return DopeQuery(
            queryString = "DELETE FROM ${bucketDopeQuery.queryString}",
            parameters = bucketDopeQuery.parameters,
        )
    }
}
