package ch.ergon.dope.resolvable.expression.unaliased.type.conditional

import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.FunctionExpression
import ch.ergon.dope.validtype.ValidType

class IfMissingExpression<T : ValidType>(
    firstExpression: UnaliasedExpression<T>,
    secondExpression: UnaliasedExpression<T>,
    vararg additionalExpressions: UnaliasedExpression<T>,
) : FunctionExpression<T>("IFMISSING", firstExpression, secondExpression, *additionalExpressions)

fun <T : ValidType> ifMissing(
    firstExpression: UnaliasedExpression<T>,
    secondExpression: UnaliasedExpression<T>,
    vararg additionalExpressions: UnaliasedExpression<T>,
) = IfMissingExpression(firstExpression, secondExpression, *additionalExpressions)
