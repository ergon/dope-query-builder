package ch.ergon.dope.resolvable.expression.rowscope.aggregate

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.ASTERISK_STRING
import ch.ergon.dope.resolvable.expression.rowscope.RowScopeExpression
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

class CountExpression(field: Field<out ValidType>, quantifier: AggregateQuantifier?) :
    AggregateFunctionExpression<NumberType>("COUNT", field, quantifier)

fun count(field: Field<out ValidType>, quantifier: AggregateQuantifier? = null) = CountExpression(field, quantifier)

class CountAsteriskExpression : RowScopeExpression<NumberType> {
    override fun toDopeQuery(manager: DopeQueryManager) = DopeQuery(
        queryString = "COUNT($ASTERISK_STRING)",
    )
}

fun countAsterisk() = CountAsteriskExpression()
