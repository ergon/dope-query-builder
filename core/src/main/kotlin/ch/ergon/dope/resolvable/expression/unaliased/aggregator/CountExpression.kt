package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.ASTERISK_STRING
import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ValidType

class CountExpression(
    private val field: Field<out ValidType>,
    private val quantifier: AggregateQuantifier?,
) : FunctionOperator, UnaliasedExpression<NumberType> {
    override fun toDopeQuery(): DopeQuery {
        val fieldDopeQuery = field.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString("COUNT", quantifier, fieldDopeQuery.queryString),
            parameters = fieldDopeQuery.parameters,
        )
    }
}

class CountAsteriskExpression : UnaliasedExpression<ValidType> {
    override fun toDopeQuery(): DopeQuery = DopeQuery(
        queryString = "COUNT($ASTERISK_STRING)",
        parameters = emptyMap(),
    )
}

fun count(field: Field<out ValidType>, quantifier: AggregateQuantifier? = null) = CountExpression(field, quantifier)

fun countAsterisk() = CountAsteriskExpression()
