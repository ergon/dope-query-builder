package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class StringToDurationExpression(val duration: TypeExpression<StringType>) :
    FunctionExpression<NumberType>("STR_TO_DURATION", listOf(duration))

fun TypeExpression<StringType>.toDurationNanos() = StringToDurationExpression(this)

fun String.toDurationNanos() = toDopeType().toDurationNanos()
