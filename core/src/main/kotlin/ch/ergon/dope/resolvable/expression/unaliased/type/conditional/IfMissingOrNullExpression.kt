package ch.ergon.dope.resolvable.expression.unaliased.type.conditional

import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.validtype.ValidType

class IfMissingOrNullExpression<T : ValidType>(
    firstExpression: UnaliasedExpression<T>,
    secondExpression: UnaliasedExpression<T>,
    vararg additionalExpressions: UnaliasedExpression<T>,
) : ConditionalExpression<T>("IFMISSINGORNULL", firstExpression, secondExpression, *additionalExpressions)

fun <T : ValidType> ifMissingOrNull(
    firstExpression: UnaliasedExpression<T>,
    secondExpression: UnaliasedExpression<T>,
    vararg additionalExpressions: UnaliasedExpression<T>,
) = IfMissingOrNullExpression(firstExpression, secondExpression, *additionalExpressions)

class CoalesceExpression<T : ValidType>(
    firstExpression: UnaliasedExpression<T>,
    secondExpression: UnaliasedExpression<T>,
    vararg additionalExpressions: UnaliasedExpression<T>,
) : ConditionalExpression<T>("COALESCE", firstExpression, secondExpression, *additionalExpressions)

fun <T : ValidType> coalesce(
    firstExpression: UnaliasedExpression<T>,
    secondExpression: UnaliasedExpression<T>,
    vararg additionalExpressions: UnaliasedExpression<T>,
) = CoalesceExpression(firstExpression, secondExpression, *additionalExpressions)
