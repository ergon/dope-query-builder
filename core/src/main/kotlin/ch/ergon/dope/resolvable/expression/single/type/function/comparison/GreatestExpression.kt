package ch.ergon.dope.resolvable.expression.single.type.function.comparison

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.FunctionExpression
import ch.ergon.dope.validtype.ComparableType

class GreatestExpression<T : ComparableType>(
    firstExpression: TypeExpression<T>,
    secondExpression: TypeExpression<T>,
    vararg additionalExpressions: TypeExpression<T>,
) : FunctionExpression<T>("GREATEST", firstExpression, secondExpression, *additionalExpressions)

fun <T : ComparableType> greatestOf(
    firstExpression: TypeExpression<T>,
    secondExpression: TypeExpression<T>,
    vararg additionalExpressions: TypeExpression<T>,
) = GreatestExpression(firstExpression, secondExpression, *additionalExpressions)
