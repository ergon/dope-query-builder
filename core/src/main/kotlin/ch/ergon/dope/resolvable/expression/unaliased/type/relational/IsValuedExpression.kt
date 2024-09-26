package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatToQueryString
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

class IsValuedExpression(
    private val field: Field<out ValidType>,
) : TypeExpression<BooleanType> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val fieldDopeQuery = field.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryString(fieldDopeQuery.queryString, "IS VALUED"),
            parameters = fieldDopeQuery.parameters,
            positionalParameters = fieldDopeQuery.positionalParameters,
        )
    }
}

fun Field<out ValidType>.isValued() = IsValuedExpression(this)

class IsNotValuedExpression(
    private val field: Field<out ValidType>,
) : TypeExpression<BooleanType> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val fieldDopeQuery = field.toDopeQuery(manager)
        return DopeQuery(
            queryString = formatToQueryString(fieldDopeQuery.queryString, "IS NOT VALUED"),
            parameters = fieldDopeQuery.parameters,
            positionalParameters = fieldDopeQuery.positionalParameters,
        )
    }
}

fun Field<out ValidType>.isNotValued() = IsNotValuedExpression(this)
