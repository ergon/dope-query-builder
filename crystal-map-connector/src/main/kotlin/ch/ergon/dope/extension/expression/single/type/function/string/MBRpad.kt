package ch.ergon.dope.extension.expression.single.type.function.string

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.string.mbRpad
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun mbRpad(inStr: CMJsonField<String>, size: CMJsonField<Number>, prefix: CMJsonField<String>) =
    mbRpad(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())

fun mbRpad(inStr: CMJsonField<String>, size: CMJsonField<Number>, prefix: TypeExpression<StringType>? = null) =
    mbRpad(inStr.toDopeType(), size.toDopeType(), prefix)

fun mbRpad(inStr: CMJsonField<String>, size: CMJsonField<Number>, prefix: String) =
    mbRpad(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())

fun mbRpad(inStr: CMJsonField<String>, size: TypeExpression<NumberType>, prefix: CMJsonField<String>) =
    mbRpad(inStr.toDopeType(), size, prefix.toDopeType())

fun mbRpad(inStr: CMJsonField<String>, size: TypeExpression<NumberType>, prefix: TypeExpression<StringType>? = null) =
    mbRpad(inStr.toDopeType(), size, prefix)

fun mbRpad(inStr: CMJsonField<String>, size: TypeExpression<NumberType>, prefix: String) =
    mbRpad(inStr.toDopeType(), size, prefix.toDopeType())

fun mbRpad(inStr: CMJsonField<String>, size: Number, prefix: TypeExpression<StringType>? = null) =
    mbRpad(inStr.toDopeType(), size.toDopeType(), prefix)

fun mbRpad(inStr: CMJsonField<String>, size: Number, prefix: String) =
    mbRpad(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())

fun mbRpad(inStr: CMJsonField<String>, size: Number, prefix: CMJsonField<String>) =
    mbRpad(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())

fun mbRpad(inStr: TypeExpression<StringType>, size: CMJsonField<Number>, prefix: CMJsonField<String>) =
    mbRpad(inStr, size.toDopeType(), prefix.toDopeType())

fun mbRpad(inStr: TypeExpression<StringType>, size: CMJsonField<Number>, prefix: String) =
    mbRpad(inStr, size.toDopeType(), prefix.toDopeType())

fun mbRpad(inStr: TypeExpression<StringType>, size: CMJsonField<Number>, prefix: TypeExpression<StringType>? = null) =
    mbRpad(inStr, size.toDopeType(), prefix)

fun mbRpad(inStr: TypeExpression<StringType>, size: TypeExpression<NumberType>, prefix: CMJsonField<String>) =
    mbRpad(inStr, size, prefix.toDopeType())

fun mbRpad(inStr: TypeExpression<StringType>, size: TypeExpression<NumberType>, prefix: String) =
    mbRpad(inStr, size, prefix.toDopeType())

fun mbRpad(inStr: String, size: CMJsonField<Number>, prefix: CMJsonField<String>) =
    mbRpad(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())

fun mbRpad(inStr: String, size: CMJsonField<Number>, prefix: String) =
    mbRpad(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())

fun mbRpad(inStr: String, size: CMJsonField<Number>, prefix: TypeExpression<StringType>? = null) =
    mbRpad(inStr.toDopeType(), size.toDopeType(), prefix)

fun mbRpad(inStr: String, size: Number, prefix: CMJsonField<String>) =
    mbRpad(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())

fun mbRpad(inStr: String, size: TypeExpression<NumberType>, prefix: CMJsonField<String>) =
    mbRpad(inStr.toDopeType(), size, prefix.toDopeType())
