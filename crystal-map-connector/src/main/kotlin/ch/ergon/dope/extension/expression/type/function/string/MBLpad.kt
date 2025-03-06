package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.string.mbLpad
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun mbLpad(inStr: CMJsonField<String>, size: CMJsonField<Number>, char: CMJsonField<String>) =
    mbLpad(inStr.toDopeType(), size.toDopeType(), char.toDopeType())

fun mbLpad(inStr: CMJsonField<String>, size: CMJsonField<Number>, char: TypeExpression<StringType>? = null) =
    mbLpad(inStr.toDopeType(), size.toDopeType(), char)

fun mbLpad(inStr: CMJsonField<String>, size: CMJsonField<Number>, char: String) =
    mbLpad(inStr.toDopeType(), size.toDopeType(), char.toDopeType())

fun mbLpad(inStr: CMJsonField<String>, size: TypeExpression<NumberType>, char: CMJsonField<String>) =
    mbLpad(inStr.toDopeType(), size, char.toDopeType())

fun mbLpad(inStr: CMJsonField<String>, size: TypeExpression<NumberType>, char: TypeExpression<StringType>? = null) =
    mbLpad(inStr.toDopeType(), size, char)

fun mbLpad(inStr: CMJsonField<String>, size: TypeExpression<NumberType>, char: String) =
    mbLpad(inStr.toDopeType(), size, char.toDopeType())

fun mbLpad(inStr: CMJsonField<String>, size: Number, char: TypeExpression<StringType>? = null) =
    mbLpad(inStr.toDopeType(), size.toDopeType(), char)

fun mbLpad(inStr: CMJsonField<String>, size: Number, char: String) =
    mbLpad(inStr.toDopeType(), size.toDopeType(), char.toDopeType())

fun mbLpad(inStr: CMJsonField<String>, size: Number, char: CMJsonField<String>) =
    mbLpad(inStr.toDopeType(), size.toDopeType(), char.toDopeType())

fun mbLpad(inStr: TypeExpression<StringType>, size: CMJsonField<Number>, char: CMJsonField<String>) =
    mbLpad(inStr, size.toDopeType(), char.toDopeType())

fun mbLpad(inStr: TypeExpression<StringType>, size: CMJsonField<Number>, char: String) =
    mbLpad(inStr, size.toDopeType(), char.toDopeType())

fun mbLpad(inStr: TypeExpression<StringType>, size: CMJsonField<Number>, char: TypeExpression<StringType>? = null) =
    mbLpad(inStr, size.toDopeType(), char)

fun mbLpad(inStr: TypeExpression<StringType>, size: TypeExpression<NumberType>, char: CMJsonField<String>) =
    mbLpad(inStr, size, char.toDopeType())

fun mbLpad(inStr: TypeExpression<StringType>, size: TypeExpression<NumberType>, char: String) =
    mbLpad(inStr, size, char.toDopeType())

fun mbLpad(inStr: String, size: CMJsonField<Number>, char: CMJsonField<String>) =
    mbLpad(inStr.toDopeType(), size.toDopeType(), char.toDopeType())

fun mbLpad(inStr: String, size: CMJsonField<Number>, char: String) =
    mbLpad(inStr.toDopeType(), size.toDopeType(), char.toDopeType())

fun mbLpad(inStr: String, size: CMJsonField<Number>, char: TypeExpression<StringType>) =
    mbLpad(inStr.toDopeType(), size.toDopeType(), char)

fun mbLpad(inStr: String, size: Number, char: CMJsonField<String>) =
    mbLpad(inStr.toDopeType(), size.toDopeType(), char.toDopeType())

fun mbLpad(inStr: String, size: TypeExpression<NumberType>, char: CMJsonField<String>) =
    mbLpad(inStr.toDopeType(), size, char.toDopeType())
