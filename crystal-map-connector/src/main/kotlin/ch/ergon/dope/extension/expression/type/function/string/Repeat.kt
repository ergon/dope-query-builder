package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.string.repeat
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun repeat(inStr: CMJsonField<String>, repetitions: CMJsonField<Number>) =
    repeat(inStr.toDopeType(), repetitions.toDopeType())

fun repeat(inStr: CMJsonField<String>, repetitions: TypeExpression<NumberType>) =
    repeat(inStr.toDopeType(), repetitions)

fun repeat(inStr: CMJsonField<String>, repetitions: Number) = repeat(inStr.toDopeType(), repetitions.toDopeType())

fun repeat(inStr: TypeExpression<StringType>, repetitions: CMJsonField<Number>) =
    repeat(inStr, repetitions.toDopeType())

fun repeat(inStr: String, repetitions: CMJsonField<Number>) = repeat(inStr.toDopeType(), repetitions.toDopeType())
