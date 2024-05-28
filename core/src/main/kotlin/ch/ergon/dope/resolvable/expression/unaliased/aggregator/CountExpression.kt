package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.ASTERISK_STRING
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.ValidType

class CountExpression(
    field: Field<out ValidType>,
    quantifier: AggregateQuantifier?,
) : AggregateExpression(field, quantifier, "COUNT")

class CountAsteriskExpression : Expression {
    override fun toDopeQuery(): DopeQuery = DopeQuery(
        queryString = "COUNT($ASTERISK_STRING)",
        parameters = emptyMap(),
    )
}

fun count(field: Field<out ValidType>, quantifier: AggregateQuantifier? = null) = CountExpression(field, quantifier)

fun countAsterisk() = CountAsteriskExpression()
