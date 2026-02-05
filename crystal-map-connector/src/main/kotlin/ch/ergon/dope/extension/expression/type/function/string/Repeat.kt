package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.string.repeat
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun CMJsonField<String>.repeat(repetitions: CMJsonField<Number>) =
    toDopeType().repeat(repetitions.toDopeType())

fun CMJsonField<String>.repeat(repetitions: TypeExpression<NumberType>) =
    toDopeType().repeat(repetitions)

fun CMJsonField<String>.repeat(repetitions: Number) =
    toDopeType().repeat(repetitions.toDopeType())

fun TypeExpression<StringType>.repeat(repetitions: CMJsonField<Number>) =
    repeat(repetitions.toDopeType())

fun String.repeat(repetitions: CMJsonField<Number>) =
    toDopeType().repeat(repetitions.toDopeType())

fun String.repeat(repetitions: TypeExpression<NumberType>) =
    toDopeType().repeat(repetitions)

fun String.repeat(repetitions: Number) =
    toDopeType().repeat(repetitions.toDopeType())
