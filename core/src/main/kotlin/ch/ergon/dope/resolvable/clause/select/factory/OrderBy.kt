package ch.ergon.dope.resolvable.clause.select.factory

import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.ClauseBuilder
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.StringType

open class OrderBy(clauses: List<Clause>) : Limit(clauses) {
    fun orderBy(stringField: Field<StringType>): Limit = ClauseBuilder(clauses).orderBy(stringField)
}
