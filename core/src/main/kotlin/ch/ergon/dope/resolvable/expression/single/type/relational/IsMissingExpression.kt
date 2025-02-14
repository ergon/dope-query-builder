package ch.ergon.dope.resolvable.expression.single.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.single.type.Field
import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.util.formatToQueryString
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

class IsMissingExpression(
    private val field: Field<out ValidType>,
) : TypeExpression<BooleanType> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val fieldDopeQuery = field.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryString(fieldDopeQuery.queryString, "IS MISSING"),
            parameters = fieldDopeQuery.parameters,
        )
    }
}

fun Field<out ValidType>.isMissing() = IsMissingExpression(this)

class IsNotMissingExpression(
    private val field: Field<out ValidType>,
) : TypeExpression<BooleanType> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val fieldDopeQuery = field.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryString(fieldDopeQuery.queryString, "IS NOT MISSING"),
            parameters = fieldDopeQuery.parameters,
        )
    }
}

fun Field<out ValidType>.isNotMissing() = IsNotMissingExpression(this)
