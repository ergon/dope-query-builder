package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.resolvable.clause.IDeleteClause
import ch.ergon.dope.resolvable.fromable.Bucket

class DeleteClause(private val bucket: Bucket) : IDeleteClause {
    override fun toQueryString(): String = "DELETE FROM ${bucket.toQueryString()}"
}
