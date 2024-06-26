package ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.validtype.NumberType

class PiExpression : TypeExpression<NumberType> {
    override fun toDopeQuery() = DopeQuery(
        queryString = "PI()",
        parameters = emptyMap(),
    )
}

fun pi() = PiExpression()
