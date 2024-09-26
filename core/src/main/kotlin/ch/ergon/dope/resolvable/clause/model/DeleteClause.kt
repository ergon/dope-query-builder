package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.IDeleteClause
import ch.ergon.dope.resolvable.fromable.AliasedBucket
import ch.ergon.dope.resolvable.fromable.Deletable

class DeleteClause(private val deletable: Deletable) : IDeleteClause {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val bucketDopeQuery = when (deletable) {
            is AliasedBucket -> deletable.asBucketDefinition().toDopeQuery(manager)
            else -> deletable.toDopeQuery(manager)
        }
        return DopeQuery(
            queryString = "DELETE FROM ${bucketDopeQuery.queryString}",
            parameters = bucketDopeQuery.parameters,
            positionalParameters = bucketDopeQuery.positionalParameters,
        )
    }
}
