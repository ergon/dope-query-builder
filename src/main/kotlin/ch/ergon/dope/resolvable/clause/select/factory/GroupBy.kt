package ch.ergon.dope.resolvable.clause.select.factory

import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.ClauseBuilder
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.ValidType

open class GroupBy(clauses: List<Clause>) : OrderBy(clauses) {
    fun groupBy(field: Field<out ValidType>, vararg fields: Field<out ValidType>): OrderBy = ClauseBuilder(clauses).groupBy(field, *fields)
}
