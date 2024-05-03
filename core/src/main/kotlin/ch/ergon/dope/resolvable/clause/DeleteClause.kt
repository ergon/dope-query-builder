package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.fromable.Bucket

class DeleteClause(private val from: Bucket) : IDeleteClause {
    override fun toQueryString(): String = "DELETE FROM ${from.toQueryString()}"
}
