package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.IUpdateClause
import ch.ergon.dope.resolvable.fromable.AliasedBucket
import ch.ergon.dope.resolvable.fromable.Updatable

class UpdateClause(private val updatable: Updatable) : IUpdateClause {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val updatableDopeQuery = when (updatable) {
            is AliasedBucket -> updatable.asBucketDefinition().toDopeQuery(manager)
            else -> updatable.toDopeQuery(manager)
        }
        return DopeQuery(
            queryString = "UPDATE ${updatableDopeQuery.queryString}",
            parameters = updatableDopeQuery.parameters,
            positionalParameters = updatableDopeQuery.positionalParameters,
        )
    }
}
