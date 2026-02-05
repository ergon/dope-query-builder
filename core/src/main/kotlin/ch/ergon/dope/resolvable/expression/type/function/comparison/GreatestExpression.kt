package ch.ergon.dope.resolvable.expression.type.function.comparison

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.ComparableType

data class GreatestExpression<T : ComparableType>(
    val firstExpression: TypeExpression<T>,
    val secondExpression: TypeExpression<T>,
    val additionalExpressions: List<TypeExpression<T>> = emptyList(),
) : FunctionExpression<T>(listOf(firstExpression, secondExpression, *additionalExpressions.toTypedArray()))

fun <T : ComparableType> greatestOf(
    firstExpression: TypeExpression<T>,
    secondExpression: TypeExpression<T>,
    vararg additionalExpressions: TypeExpression<T>,
) = GreatestExpression(firstExpression, secondExpression, additionalExpressions.toList())
