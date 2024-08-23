package ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

class IsAtomExpression<T : ValidType>(
    private val expression: TypeExpression<T>,
) : TypeExpression<BooleanType>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val expressionDopeQuery = expression.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString("ISATOM", expressionDopeQuery),
            parameters = expressionDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> TypeExpression<T>.isAtom() = IsAtomExpression(this)
