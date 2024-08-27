package ch.ergon.dope.resolvable.expression.unaliased.type.function.comparison

import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.validtype.ComparableType

class GreatestExpression<T : ComparableType>(
    firstExpression: UnaliasedExpression<T>,
    secondExpression: UnaliasedExpression<T>,
    vararg additionalExpressions: UnaliasedExpression<T>,
) : FunctionExpression<T>("GREATEST", firstExpression, secondExpression, *additionalExpressions)

fun <T : ComparableType> greatestOf(
    firstExpression: UnaliasedExpression<T>,
    secondExpression: UnaliasedExpression<T>,
    vararg additionalExpressions: UnaliasedExpression<T>,
) = GreatestExpression(firstExpression, secondExpression, *additionalExpressions)
