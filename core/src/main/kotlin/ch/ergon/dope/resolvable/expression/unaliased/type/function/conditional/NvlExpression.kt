package ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional

import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class NvlExpression<T : ValidType>(
    initialExpression: UnaliasedExpression<T>,
    substituteExpression: UnaliasedExpression<T>,
) : FunctionExpression<T>("NVL", initialExpression, substituteExpression)

fun <T : ValidType> nvl(initialExpression: UnaliasedExpression<T>, substituteExpression: UnaliasedExpression<T>) =
    NvlExpression(initialExpression, substituteExpression)

fun nvl(initialExpression: UnaliasedExpression<NumberType>, substituteExpression: Number) =
    NvlExpression(initialExpression, substituteExpression.toDopeType())

fun nvl(initialExpression: UnaliasedExpression<StringType>, substituteExpression: String) =
    NvlExpression(initialExpression, substituteExpression.toDopeType())

fun nvl(initialExpression: UnaliasedExpression<BooleanType>, substituteExpression: Boolean) =
    NvlExpression(initialExpression, substituteExpression.toDopeType())
