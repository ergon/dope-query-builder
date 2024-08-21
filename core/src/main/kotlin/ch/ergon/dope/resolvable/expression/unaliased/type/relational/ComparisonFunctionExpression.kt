package ch.ergon.dope.resolvable.expression.unaliased.type.relational

import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.FunctionExpression
import ch.ergon.dope.validtype.ComparableType

sealed class ComparisonFunctionExpression<T : ComparableType>(
    symbol: String,
    firstExpression: UnaliasedExpression<T>,
    secondExpression: UnaliasedExpression<T>,
    vararg additionalExpressions: UnaliasedExpression<T>,
) : FunctionExpression<T>(symbol, firstExpression, secondExpression, *additionalExpressions)

class GreatestExpression<T : ComparableType>(
    firstExpression: UnaliasedExpression<T>,
    secondExpression: UnaliasedExpression<T>,
    vararg additionalExpressions: UnaliasedExpression<T>,
) : ComparisonFunctionExpression<T>("GREATEST", firstExpression, secondExpression, *additionalExpressions)

fun <T : ComparableType> greatestOf(
    firstExpression: UnaliasedExpression<T>,
    secondExpression: UnaliasedExpression<T>,
    vararg additionalExpressions: UnaliasedExpression<T>,
) = GreatestExpression(firstExpression, secondExpression, *additionalExpressions)

class LeastExpression<T : ComparableType>(
    firstExpression: UnaliasedExpression<T>,
    secondExpression: UnaliasedExpression<T>,
    vararg additionalExpressions: UnaliasedExpression<T>,
) : ComparisonFunctionExpression<T>("LEAST", firstExpression, secondExpression, *additionalExpressions)

fun <T : ComparableType> leastOf(
    firstExpression: UnaliasedExpression<T>,
    secondExpression: UnaliasedExpression<T>,
    vararg additionalExpressions: UnaliasedExpression<T>,
) = LeastExpression(firstExpression, secondExpression, *additionalExpressions)
