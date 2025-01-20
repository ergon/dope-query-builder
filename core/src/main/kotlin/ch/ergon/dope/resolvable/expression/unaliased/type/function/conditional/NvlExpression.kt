package ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class NvlExpression<T : ValidType>(
    initialExpression: TypeExpression<T>,
    substituteExpression: TypeExpression<T>,
) : FunctionExpression<T>("NVL", initialExpression, substituteExpression)

fun <T : ValidType> nvl(initialExpression: TypeExpression<T>, substituteExpression: TypeExpression<T>) =
    NvlExpression(initialExpression, substituteExpression)

fun nvl(initialExpression: TypeExpression<NumberType>, substituteExpression: Number) =
    NvlExpression(initialExpression, substituteExpression.toDopeType())

fun nvl(initialExpression: TypeExpression<StringType>, substituteExpression: String) =
    NvlExpression(initialExpression, substituteExpression.toDopeType())

fun nvl(initialExpression: TypeExpression<BooleanType>, substituteExpression: Boolean) =
    NvlExpression(initialExpression, substituteExpression.toDopeType())
