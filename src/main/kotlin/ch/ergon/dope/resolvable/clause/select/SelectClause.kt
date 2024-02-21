package ch.ergon.dope.resolvable.clause.select

import ch.ergon.dope.resolvable.clause.Clause
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.formatToQueryString

class SelectClause(private val expression: Expression, private vararg val expressions: Expression) : Clause {
    override fun toQueryString(): String = formatToQueryString("SELECT", expression, *expressions)
}
