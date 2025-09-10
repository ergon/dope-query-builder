package ch.ergon.dope.resolvable.clause.model

import ch.ergon.dope.resolvable.Updatable
import ch.ergon.dope.resolvable.clause.IUpdateClause

data class UpdateClause(val updatable: Updatable) : IUpdateClause
