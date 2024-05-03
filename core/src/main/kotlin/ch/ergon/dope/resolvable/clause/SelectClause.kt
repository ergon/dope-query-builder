package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.formatToQueryString

class SelectClause(private val expression: Expression, private vararg val expressions: Expression) : ISelectClause {
    override fun toQueryString(): String = formatToQueryString("SELECT", expression, *expressions)
}

class SelectRawClause(private val expression: Expression) : ISelectClause {
    override fun toQueryString(): String = formatToQueryString("SELECT RAW", expression)
}

class SelectDistinctClause(private val expression: Expression, private vararg val expressions: Expression) : ISelectClause {
    override fun toQueryString(): String = formatToQueryString("SELECT DISTINCT", expression, *expressions)
}
