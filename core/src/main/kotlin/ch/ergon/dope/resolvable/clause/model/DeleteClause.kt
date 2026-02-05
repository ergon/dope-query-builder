package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.resolvable.Deletable
import ch.ergon.dope.resolvable.clause.IDeleteClause

data class DeleteClause(val deletable: Deletable) : IDeleteClause
