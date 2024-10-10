package ch.ergon.dope.extension.type.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.trim
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun trim(inStr: CMJsonField<String>, char: TypeExpression<StringType>? = null) =
    trim(inStr.toDopeType(), char)

fun trim(inStr: TypeExpression<StringType>, char: CMJsonField<String>) =
    trim(inStr, char.toDopeType())

fun trim(inStr: CMJsonField<String>, char: CMJsonField<String>) =
    trim(inStr.toDopeType(), char.toDopeType())

fun trim(inStr: CMJsonField<String>, char: String) =
    trim(inStr.toDopeType(), char.toDopeType())

fun trim(inStr: CMJsonField<String>, char: Char) =
    trim(inStr.toDopeType(), char.toString().toDopeType())

fun trim(inStr: String, char: CMJsonField<String>) =
    trim(inStr.toDopeType(), char.toDopeType())
