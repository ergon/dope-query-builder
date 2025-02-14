package ch.ergon.dope.resolvable.expression.single.type.function.comparison

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.FunctionExpression
import ch.ergon.dope.validtype.ComparableType

class LeastExpression<T : ComparableType>(
    firstExpression: TypeExpression<T>,
    secondExpression: TypeExpression<T>,
    vararg additionalExpressions: TypeExpression<T>,
) : FunctionExpression<T>("LEAST", firstExpression, secondExpression, *additionalExpressions)

fun <T : ComparableType> leastOf(
    firstExpression: TypeExpression<T>,
    secondExpression: TypeExpression<T>,
    vararg additionalExpressions: TypeExpression<T>,
) = LeastExpression(firstExpression, secondExpression, *additionalExpressions)
