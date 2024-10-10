package ch.ergon.dope.extension.type.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.repeat
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
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
