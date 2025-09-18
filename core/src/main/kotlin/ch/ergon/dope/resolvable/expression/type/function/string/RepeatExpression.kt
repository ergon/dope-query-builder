package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class RepeatExpression(val inStr: TypeExpression<StringType>, val repetitions: TypeExpression<NumberType>) :
    FunctionExpression<StringType>(listOf(inStr, repetitions))

fun repeat(inStr: TypeExpression<StringType>, repetitions: TypeExpression<NumberType>) =
    RepeatExpression(inStr, repetitions)

fun repeat(inStr: TypeExpression<StringType>, repetitions: Number) = repeat(inStr, repetitions.toDopeType())

fun repeat(inStr: String, repetitions: TypeExpression<NumberType>) = repeat(inStr.toDopeType(), repetitions)

fun repeat(inStr: String, repetitions: Number) = repeat(inStr.toDopeType(), repetitions.toDopeType())
