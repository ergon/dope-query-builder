package ch.ergon.dope.resolvable.expression.type.function.conditional

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.ValidType

data class IfNullExpression<T : ValidType>(
    val firstExpression: TypeExpression<T>,
    val secondExpression: TypeExpression<T>,
    val additionalExpressions: List<TypeExpression<T>> = emptyList(),
) : FunctionExpression<T>(
    listOf(firstExpression, secondExpression, *additionalExpressions.toTypedArray()),
)

fun <T : ValidType> ifNull(
    firstExpression: TypeExpression<T>,
    secondExpression: TypeExpression<T>,
    vararg additionalExpressions: TypeExpression<T>,
) = IfNullExpression(firstExpression, secondExpression, additionalExpressions.toList())
