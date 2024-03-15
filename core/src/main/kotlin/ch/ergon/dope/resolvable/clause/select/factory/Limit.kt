package ch.ergon.dope.resolvable.clause.select.factory

import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.clause.ClauseBuilder
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.validtype.NumberType

open class Limit(clauses: List<Clause>) : Offset(clauses) {
    fun limit(number: Number): Offset = limit(number.toNumberType())

    fun limit(numberExpression: TypeExpression<NumberType>): Offset = ClauseBuilder(clauses).limit(numberExpression)
}
