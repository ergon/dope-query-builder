package ch.ergon.dope.resolvable.expression.single.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.single.type.Field
import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.util.formatToQueryString
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

class IsNullExpression(
    private val field: Field<out ValidType>,
) : TypeExpression<BooleanType> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val fieldDopeQuery = field.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryString(fieldDopeQuery.queryString, "IS NULL"),
            parameters = fieldDopeQuery.parameters,
        )
    }
}

fun Field<out ValidType>.isNull() = IsNullExpression(this)

class IsNotNullExpression(
    private val field: Field<out ValidType>,
) : TypeExpression<BooleanType> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val fieldDopeQuery = field.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryString(fieldDopeQuery.queryString, "IS NOT NULL"),
            parameters = fieldDopeQuery.parameters,
        )
    }
}

fun Field<out ValidType>.isNotNull() = IsNotNullExpression(this)
