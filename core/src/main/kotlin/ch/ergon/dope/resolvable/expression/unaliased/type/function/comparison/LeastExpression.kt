package ch.ergon.dope.resolvable.expression.unaliased.type.function.comparison

import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.validtype.ComparableType

class LeastExpression<T : ComparableType>(
    firstExpression: UnaliasedExpression<T>,
    secondExpression: UnaliasedExpression<T>,
    vararg additionalExpressions: UnaliasedExpression<T>,
) : FunctionExpression<T>("LEAST", firstExpression, secondExpression, *additionalExpressions)

fun <T : ComparableType> leastOf(
    firstExpression: UnaliasedExpression<T>,
    secondExpression: UnaliasedExpression<T>,
    vararg additionalExpressions: UnaliasedExpression<T>,
) = LeastExpression(firstExpression, secondExpression, *additionalExpressions)
