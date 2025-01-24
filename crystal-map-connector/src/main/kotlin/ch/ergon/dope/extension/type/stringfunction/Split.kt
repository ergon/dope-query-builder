package ch.ergon.dope.extension.type.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.split
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun split(inStr: CMJsonField<String>, inSubstring: CMJsonField<String>) =
    split(inStr.toDopeType(), inSubstring.toDopeType())

fun split(inStr: CMJsonField<String>, inSubstring: String) =
    split(inStr.toDopeType(), inSubstring.toDopeType())

fun split(inStr: String, inSubstring: CMJsonField<String>) =
    split(inStr.toDopeType(), inSubstring.toDopeType())

fun split(inStr: TypeExpression<StringType>, inSubstring: CMJsonField<String>) =
    split(inStr, inSubstring.toDopeType())

fun split(inStr: CMJsonField<String>, inSubstring: TypeExpression<StringType>? = null) =
    split(inStr.toDopeType(), inSubstring)
