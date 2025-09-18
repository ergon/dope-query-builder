package ch.ergon.dope.resolvable.expression.type.function.conditional

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.ValidType

data class IfMissingExpression<T : ValidType>(
    val firstExpression: TypeExpression<T>,
    val secondExpression: TypeExpression<T>,
    val additionalExpressions: List<TypeExpression<T>> = emptyList(),
) : FunctionExpression<T>(listOf(firstExpression, secondExpression, *additionalExpressions.toTypedArray()))

fun <T : ValidType> ifMissing(
    firstExpression: TypeExpression<T>,
    secondExpression: TypeExpression<T>,
    vararg additionalExpressions: TypeExpression<T>,
) = IfMissingExpression(firstExpression, secondExpression, additionalExpressions.toList())
