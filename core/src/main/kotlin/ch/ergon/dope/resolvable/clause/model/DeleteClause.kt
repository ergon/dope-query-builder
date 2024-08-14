package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.clause.IDeleteClause
import ch.ergon.dope.resolvable.fromable.IBucket

class DeleteClause(private val bucket: IBucket) : IDeleteClause {
    override fun toDopeQuery(): DopeQuery {
        val bucketDopeQuery = bucket.toDopeQuery()
        return DopeQuery(
            queryString = "DELETE FROM ${bucketDopeQuery.queryString}",
            parameters = bucketDopeQuery.parameters,
        )
    }
}
