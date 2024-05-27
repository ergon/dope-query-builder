package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.ASTERISK_STRING
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.ValidType

class CountExpression(
    private val field: Field<out ValidType>,
    private val quantifier: AggregateQuantifier,
) : AggregateExpression, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val fieldDopeQuery = field.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString("COUNT", quantifier, fieldDopeQuery),
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

fun count(field: Field<out ValidType>, quantifier: AggregateQuantifier = ALL) = CountExpression(field, quantifier)

fun countAsterisk() = CountAsteriskExpression()
