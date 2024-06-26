package ch.ergon.dope.resolvable.expression.unaliased.type.numberfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.NumberType

class RandomExpression(private val value: TypeExpression<NumberType>? = null) :
    TypeExpression<NumberType>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery =
        value?.let {
            val valueDopeQuery = value.toDopeQuery()
            DopeQuery(
                queryString = toFunctionQueryString("RANDOM", valueDopeQuery.queryString),
                parameters = valueDopeQuery.parameters,
            )
        } ?: DopeQuery(queryString = "RANDOM()", parameters = emptyMap())
}

fun random() = RandomExpression()

fun random(value: TypeExpression<NumberType>) = RandomExpression(value)
fun random(value: Number) = random(value.toDopeType())
