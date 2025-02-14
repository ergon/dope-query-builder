package ch.ergon.dope.resolvable.expression.single.type.function.string

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class RepeatExpression(inStr: TypeExpression<StringType>, repetitions: TypeExpression<NumberType>) :
    FunctionExpression<StringType>("REPEAT", inStr, repetitions)

fun repeat(inStr: TypeExpression<StringType>, repetitions: TypeExpression<NumberType>) =
    RepeatExpression(inStr, repetitions)

fun repeat(inStr: TypeExpression<StringType>, repetitions: Number) = repeat(inStr, repetitions.toDopeType())

fun repeat(inStr: String, repetitions: TypeExpression<NumberType>) = repeat(inStr.toDopeType(), repetitions)

fun repeat(inStr: String, repetitions: Number) = repeat(inStr.toDopeType(), repetitions.toDopeType())
