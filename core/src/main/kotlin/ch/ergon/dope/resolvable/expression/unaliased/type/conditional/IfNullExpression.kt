package ch.ergon.dope.resolvable.expression.unaliased.type.conditional

import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.FunctionExpression
import ch.ergon.dope.validtype.ValidType

class IfNullExpression<T : ValidType>(
    firstExpression: UnaliasedExpression<T>,
    secondExpression: UnaliasedExpression<T>,
    vararg additionalExpressions: UnaliasedExpression<T>,
) : FunctionExpression<T>("IFNULL", firstExpression, secondExpression, *additionalExpressions)

fun <T : ValidType> ifNull(
    firstExpression: UnaliasedExpression<T>,
    secondExpression: UnaliasedExpression<T>,
    vararg additionalExpressions: UnaliasedExpression<T>,
) = IfNullExpression(firstExpression, secondExpression, *additionalExpressions)
