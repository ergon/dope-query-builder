package ch.ergon.dope.extension.type.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.mbSubstring
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun mbSubstring(
    inStr: CMJsonField<String>,
    startPos: TypeExpression<NumberType>,
    length: TypeExpression<NumberType>? = null,
) = mbSubstring(inStr.toDopeType(), startPos, length)

fun mbSubstring(
    inStr: TypeExpression<StringType>,
    startPos: CMJsonField<Number>,
    length: TypeExpression<NumberType>? = null,
) = mbSubstring(inStr, startPos.toDopeType(), length)

fun mbSubstring(
    inStr: TypeExpression<StringType>,
    startPos: TypeExpression<NumberType>,
    length: CMJsonField<Number>,
) = mbSubstring(inStr, startPos, length.toDopeType())

fun mbSubstring(
    inStr: CMJsonField<String>,
    startPos: CMJsonField<Number>,
    length: TypeExpression<NumberType>? = null,
) = mbSubstring(inStr.toDopeType(), startPos.toDopeType(), length)

fun mbSubstring(inStr: CMJsonField<String>, startPos: TypeExpression<NumberType>, length: CMJsonField<Number>) =
    mbSubstring(inStr.toDopeType(), startPos, length.toDopeType())

fun mbSubstring(inStr: TypeExpression<StringType>, startPos: CMJsonField<Number>, length: CMJsonField<Number>) =
    mbSubstring(inStr, startPos.toDopeType(), length.toDopeType())

fun mbSubstring(inStr: CMJsonField<String>, startPos: CMJsonField<Number>, length: CMJsonField<Number>) =
    mbSubstring(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())

fun mbSubstring(inStr: CMJsonField<String>, startPos: TypeExpression<NumberType>, length: Int) =
    mbSubstring(inStr.toDopeType(), startPos, length.toDopeType())

fun mbSubstring(inStr: TypeExpression<StringType>, startPos: CMJsonField<Number>, length: Int) =
    mbSubstring(inStr, startPos.toDopeType(), length.toDopeType())

fun mbSubstring(inStr: CMJsonField<String>, startPos: CMJsonField<Number>, length: Int) =
    mbSubstring(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())

fun mbSubstring(inStr: TypeExpression<StringType>, startPos: Int, length: CMJsonField<Number>) =
    mbSubstring(inStr, startPos.toDopeType(), length.toDopeType())

fun mbSubstring(inStr: CMJsonField<String>, startPos: Int, length: TypeExpression<NumberType>? = null) =
    mbSubstring(inStr.toDopeType(), startPos.toDopeType(), length)

fun mbSubstring(inStr: CMJsonField<String>, startPos: Int, length: CMJsonField<Number>) =
    mbSubstring(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())

fun mbSubstring(inStr: String, startPos: CMJsonField<Number>, length: TypeExpression<NumberType>? = null) =
    mbSubstring(inStr.toDopeType(), startPos.toDopeType(), length)

fun mbSubstring(inStr: String, startPos: TypeExpression<NumberType>, length: CMJsonField<Number>) =
    mbSubstring(inStr.toDopeType(), startPos, length.toDopeType())

fun mbSubstring(inStr: String, startPos: CMJsonField<Number>, length: CMJsonField<Number>) =
    mbSubstring(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())

fun mbSubstring(inStr: CMJsonField<String>, startPos: Int, length: Int) =
    mbSubstring(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())

fun mbSubstring(inStr: String, startPos: CMJsonField<Number>, length: Int) =
    mbSubstring(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())

fun mbSubstring(inStr: String, startPos: Int, length: CMJsonField<Number>) =
    mbSubstring(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())
