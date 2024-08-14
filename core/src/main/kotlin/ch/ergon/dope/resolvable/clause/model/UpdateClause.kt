package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.clause.IUpdateClause
import ch.ergon.dope.resolvable.fromable.IBucket

class UpdateClause(private val bucket: IBucket) : IUpdateClause {
    override fun toDopeQuery(): DopeQuery {
        val bucketDopeQuery = bucket.toDopeQuery()
        return DopeQuery(
            queryString = "UPDATE ${bucketDopeQuery.queryString}",
            parameters = bucketDopeQuery.parameters,
        )
    }
}
