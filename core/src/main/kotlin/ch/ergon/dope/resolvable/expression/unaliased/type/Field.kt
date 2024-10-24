package ch.ergon.dope.resolvable.expression.unaliased.type

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.formatPathToQueryString
import ch.ergon.dope.validtype.ValidType

open class Field<T : ValidType>(private val name: String, private val path: String) : TypeExpression<T> {
    override fun toDopeQuery(manager: DopeQueryManager) = DopeQuery(
        queryString = formatPathToQueryString(name, path),
    )
}
