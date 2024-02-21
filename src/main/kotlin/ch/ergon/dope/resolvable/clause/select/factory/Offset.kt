package ch.ergon.dope.resolvable.clause.select.factory

import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.ClauseBuilder
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.NumberType

open class Offset(clauses: List<Clause>) : Build(clauses) {
    fun offset(numberExpression: TypeExpression<NumberType>): Build = ClauseBuilder(clauses).offset(numberExpression)
}
