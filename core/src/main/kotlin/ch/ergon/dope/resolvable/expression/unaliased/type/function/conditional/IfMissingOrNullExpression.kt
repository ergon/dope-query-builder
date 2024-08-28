package ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional

import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.validtype.ValidType

class IfMissingOrNullExpression<T : ValidType>(
    firstExpression: UnaliasedExpression<T>,
    secondExpression: UnaliasedExpression<T>,
    vararg additionalExpressions: UnaliasedExpression<T>,
) : FunctionExpression<T>("IFMISSINGORNULL", firstExpression, secondExpression, *additionalExpressions)

fun <T : ValidType> ifMissingOrNull(
    firstExpression: UnaliasedExpression<T>,
    secondExpression: UnaliasedExpression<T>,
    vararg additionalExpressions: UnaliasedExpression<T>,
) = IfMissingOrNullExpression(firstExpression, secondExpression, *additionalExpressions)

class CoalesceExpression<T : ValidType>(
    firstExpression: UnaliasedExpression<T>,
    secondExpression: UnaliasedExpression<T>,
    vararg additionalExpressions: UnaliasedExpression<T>,
) : FunctionExpression<T>("COALESCE", firstExpression, secondExpression, *additionalExpressions)

fun <T : ValidType> coalesce(
    firstExpression: UnaliasedExpression<T>,
    secondExpression: UnaliasedExpression<T>,
    vararg additionalExpressions: UnaliasedExpression<T>,
) = CoalesceExpression(firstExpression, secondExpression, *additionalExpressions)
