package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Updatable
import ch.ergon.dope.resolvable.bucket.AliasedBucket
import ch.ergon.dope.resolvable.clause.IUpdateClause

class UpdateClause(private val updatable: Updatable) : IUpdateClause {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val updatableDopeQuery = when (updatable) {
            is AliasedBucket -> updatable.asBucketDefinition().toDopeQuery(manager)
            else -> updatable.toDopeQuery(manager)
        }
        return DopeQuery(
            queryString = "UPDATE ${updatableDopeQuery.queryString}",
            parameters = updatableDopeQuery.parameters,
        )
    }
}
