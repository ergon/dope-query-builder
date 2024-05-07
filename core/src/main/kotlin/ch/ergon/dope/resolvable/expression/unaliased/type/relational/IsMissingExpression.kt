package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

class IsMissingExpression(
    private val field: Field<out ValidType>,
) : TypeExpression<BooleanType> {
    override fun toQuery(): DopeQuery {
        val fieldDopeQuery = field.toQuery()
        return DopeQuery(
            queryString = formatToQueryString(fieldDopeQuery.queryString, "IS MISSING"),
            parameters = fieldDopeQuery.parameters,
        )
    }
}

fun Field<out ValidType>.isMissing() = IsMissingExpression(this)
