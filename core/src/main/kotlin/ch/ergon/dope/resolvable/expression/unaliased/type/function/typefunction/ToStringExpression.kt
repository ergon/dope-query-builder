package ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ToStringExpression<T : ValidType>(
    private val expression: TypeExpression<T>,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val expressionDopeQuery = when (expression) {
            is ISelectOffsetClause<*> -> expression.asSubQuery().toDopeQuery(manager)
            else -> expression.toDopeQuery(manager)
        }
        return DopeQuery(
            queryString = toFunctionQueryString("TOSTRING", expressionDopeQuery),
            parameters = expressionDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> TypeExpression<T>.toStr() = ToStringExpression(this)

fun Number.toStr() = ToStringExpression(this.toDopeType())

fun Boolean.toStr() = ToStringExpression(this.toDopeType())
