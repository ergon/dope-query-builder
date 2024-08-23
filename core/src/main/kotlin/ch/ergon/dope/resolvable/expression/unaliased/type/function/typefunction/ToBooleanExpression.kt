package ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

class ToBooleanExpression<T : ValidType>(
    private val expression: TypeExpression<T>,
) : TypeExpression<BooleanType>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val expressionDopeQuery = expression.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString("TOBOOLEAN", expressionDopeQuery),
            parameters = expressionDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> TypeExpression<T>.toBool() = ToBooleanExpression(this)

fun Number.toBool() = ToBooleanExpression(this.toDopeType())

fun String.toBool() = ToBooleanExpression(this.toDopeType())
