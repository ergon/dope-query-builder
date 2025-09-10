package ch.ergon.dope.resolvable.expression.type.function.type

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

data class ToBooleanExpression<T : ValidType>(val expression: TypeExpression<T>) :
    FunctionExpression<BooleanType>("TOBOOLEAN", listOf(expression))

fun <T : ValidType> TypeExpression<T>.toBool() = ToBooleanExpression(this)

fun Number.toBool() = toDopeType().toBool()

fun String.toBool() = toDopeType().toBool()
