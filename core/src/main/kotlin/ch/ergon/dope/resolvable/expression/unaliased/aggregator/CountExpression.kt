package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.ASTERISK_STRING
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.formatToQueryStringWithBrackets
import ch.ergon.dope.validtype.ValidType

class CountExpression(
    private val field: Field<out ValidType>,
) : AggregateExpression {
    override fun toDopeQuery(): DopeQuery {
        val fieldDopeQuery = field.toDopeQuery()
        return DopeQuery(
            queryString = formatToQueryStringWithBrackets("COUNT", fieldDopeQuery.queryString),
            parameters = fieldDopeQuery.parameters,
        )
    }
}

class CountAsteriskExpression : AggregateExpression {
    override fun toDopeQuery(): DopeQuery = DopeQuery(
        queryString = "COUNT($ASTERISK_STRING)",
        parameters = emptyMap(),
    )
}

fun count(field: Field<out ValidType>) = CountExpression(field)

fun countAsterisk() = CountAsteriskExpression()
