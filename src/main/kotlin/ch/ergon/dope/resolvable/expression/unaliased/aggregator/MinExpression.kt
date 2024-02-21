package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.ValidType

class MinExpression(private val field: Field<out ValidType>) : AggregateExpression, FunctionOperator {
    override fun toQueryString() = toFunctionQueryString("MIN", field)
}

fun min(field: Field<out ValidType>): MinExpression = MinExpression(field)
