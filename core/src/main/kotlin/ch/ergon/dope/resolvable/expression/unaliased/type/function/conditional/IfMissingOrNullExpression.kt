package ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.validtype.ValidType

class IfMissingOrNullExpression<T : ValidType>(
    firstExpression: TypeExpression<T>,
    secondExpression: TypeExpression<T>,
    vararg additionalExpressions: TypeExpression<T>,
) : FunctionExpression<T>("IFMISSINGORNULL", firstExpression, secondExpression, *additionalExpressions)

fun <T : ValidType> ifMissingOrNull(
    firstExpression: TypeExpression<T>,
    secondExpression: TypeExpression<T>,
    vararg additionalExpressions: TypeExpression<T>,
) = IfMissingOrNullExpression(firstExpression, secondExpression, *additionalExpressions)

class CoalesceExpression<T : ValidType>(
    firstExpression: TypeExpression<T>,
    secondExpression: TypeExpression<T>,
    vararg additionalExpressions: TypeExpression<T>,
) : FunctionExpression<T>("COALESCE", firstExpression, secondExpression, *additionalExpressions)

fun <T : ValidType> coalesce(
    firstExpression: TypeExpression<T>,
    secondExpression: TypeExpression<T>,
    vararg additionalExpressions: TypeExpression<T>,
) = CoalesceExpression(firstExpression, secondExpression, *additionalExpressions)
