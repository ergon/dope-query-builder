package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class RepeatExpression(val inStr: TypeExpression<StringType>, val repetitions: TypeExpression<NumberType>) :
    FunctionExpression<StringType>(listOf(inStr, repetitions))

fun TypeExpression<StringType>.repeat(repetitions: TypeExpression<NumberType>) =
    RepeatExpression(this, repetitions)

fun TypeExpression<StringType>.repeat(repetitions: Number) = repeat(repetitions.toDopeType())

fun String.repeat(repetitions: TypeExpression<NumberType>) = toDopeType().repeat(repetitions)

fun String.repeat(repetitions: Number) = toDopeType().repeat(repetitions.toDopeType())
