package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.clause.IDeleteClause
import ch.ergon.dope.resolvable.fromable.Bucket

class DeleteClause(private val bucket: Bucket) : IDeleteClause {

    override fun toQuery(): DopeQuery {
        val bucketDopeQuery = bucket.toQuery()
        return DopeQuery(
            queryString = "DELETE FROM ${bucketDopeQuery.queryString}",
            parameters = bucketDopeQuery.parameters,
        )
    }
}
