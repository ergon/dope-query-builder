package ch.ergon.dope.resolvable.expression.type.function.type

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

data class IsBooleanExpression<T : ValidType>(val expression: TypeExpression<T>) :
    FunctionExpression<BooleanType>("ISBOOLEAN", listOf(expression))

fun <T : ValidType> TypeExpression<T>.isBoolean() = IsBooleanExpression(this)
