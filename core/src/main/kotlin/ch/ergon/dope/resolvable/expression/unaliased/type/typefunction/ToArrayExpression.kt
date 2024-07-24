package ch.ergon.dope.resolvable.expression.unaliased.type.typefunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.AtomType
import ch.ergon.dope.validtype.ValidType

class ToArrayExpression<T : AtomType>(
    private val expression: TypeExpression<T>,
) : TypeExpression<ArrayType<T>>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val expressionDopeQuery = expression.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString("TOARRAY", expressionDopeQuery),
            parameters = expressionDopeQuery.parameters,
        )
    }
}

@JvmName("atomToArray")
fun <T : AtomType> toArray(expression: TypeExpression<T>) = ToArrayExpression(expression)

class ArrayToArrayExpression<T : ValidType>(
    private val expression: TypeExpression<ArrayType<T>>,
) : TypeExpression<ArrayType<T>>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val expressionDopeQuery = expression.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString("TOARRAY", expressionDopeQuery),
            parameters = expressionDopeQuery.parameters,
        )
    }
}

@JvmName("arrayToArray")
fun <T : ValidType> toArray(expression: TypeExpression<ArrayType<T>>) = ArrayToArrayExpression(expression)
