package ch.ergon.dope.resolvable.expression.type.function.conditional

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.ValidType

data class IfMissingOrNullExpression<T : ValidType>(
    val firstExpression: TypeExpression<T>,
    val secondExpression: TypeExpression<T>,
    val additionalExpressions: List<TypeExpression<T>> = emptyList(),
) : FunctionExpression<T>(
    listOf(firstExpression, secondExpression, *additionalExpressions.toTypedArray()),
)

fun <T : ValidType> ifMissingOrNull(
    firstExpression: TypeExpression<T>,
    secondExpression: TypeExpression<T>,
    vararg additionalExpressions: TypeExpression<T>,
) = IfMissingOrNullExpression(firstExpression, secondExpression, additionalExpressions.toList())

data class CoalesceExpression<T : ValidType>(
    val firstExpression: TypeExpression<T>,
    val secondExpression: TypeExpression<T>,
    val additionalExpressions: List<TypeExpression<T>> = emptyList(),
) : FunctionExpression<T>(
    listOf(firstExpression, secondExpression, *additionalExpressions.toTypedArray()),
)

fun <T : ValidType> coalesce(
    firstExpression: TypeExpression<T>,
    secondExpression: TypeExpression<T>,
    vararg additionalExpressions: TypeExpression<T>,
) = CoalesceExpression(firstExpression, secondExpression, additionalExpressions.toList())
