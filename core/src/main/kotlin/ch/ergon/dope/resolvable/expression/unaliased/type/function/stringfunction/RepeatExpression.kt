package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class RepeatExpression(inStr: TypeExpression<StringType>, repetitions: TypeExpression<NumberType>) :
    FunctionExpression<StringType>("REPEAT", inStr, repetitions)

fun repeat(inStr: TypeExpression<StringType>, repetitions: TypeExpression<NumberType>) =
    RepeatExpression(inStr, repetitions)

fun repeat(inStr: TypeExpression<StringType>, repetitions: Number) = RepeatExpression(inStr, repetitions.toDopeType())

fun repeat(inStr: String, repetitions: TypeExpression<NumberType>) = RepeatExpression(inStr.toDopeType(), repetitions)

fun repeat(inStr: String, repetitions: Number) = RepeatExpression(inStr.toDopeType(), repetitions.toDopeType())
