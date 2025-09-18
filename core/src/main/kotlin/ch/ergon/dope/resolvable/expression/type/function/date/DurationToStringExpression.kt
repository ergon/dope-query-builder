package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class DurationToStringExpression(val duration: TypeExpression<NumberType>) :
    FunctionExpression<StringType>(listOf(duration))

fun TypeExpression<NumberType>.toDurationString() = DurationToStringExpression(this)

fun Number.toDurationString() = toDopeType().toDurationString()
