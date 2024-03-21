package ch.ergon.dope.resolvable.clause.select.factory

import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.ClauseBuilder
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.BooleanType

open class Where(clauses: List<Clause>) : GroupBy(clauses) {
    fun where(condition: TypeExpression<BooleanType>): GroupBy =
        ClauseBuilder(clauses).where(condition)
}
