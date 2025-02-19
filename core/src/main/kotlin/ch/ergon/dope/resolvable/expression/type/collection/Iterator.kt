package ch.ergon.dope.resolvable.expression.type.collection

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ValidType

class Iterator<T : ValidType>(private val variable: String) : TypeExpression<T> {
    override fun toDopeQuery(manager: DopeQueryManager) = DopeQuery(
        queryString = "`$variable`",
    )
}
