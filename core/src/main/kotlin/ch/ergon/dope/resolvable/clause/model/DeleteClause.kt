package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.AliasedBucket
import ch.ergon.dope.resolvable.Deletable
import ch.ergon.dope.resolvable.clause.IDeleteClause

class DeleteClause(private val deletable: Deletable) : IDeleteClause {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val bucketDopeQuery = when (deletable) {
            is AliasedBucket -> deletable.asBucketDefinition().toDopeQuery(manager)
            else -> deletable.toDopeQuery(manager)
        }
        return DopeQuery(
            queryString = "DELETE FROM ${bucketDopeQuery.queryString}",
            parameters = bucketDopeQuery.parameters,
        )
    }
}
