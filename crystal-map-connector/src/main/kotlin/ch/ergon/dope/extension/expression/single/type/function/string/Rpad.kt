package ch.ergon.dope.extension.expression.single.type.function.string

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.string.rpad
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun rpad(inStr: CMJsonField<String>, size: CMJsonField<Number>, prefix: CMJsonField<String>) =
    rpad(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())

fun rpad(inStr: CMJsonField<String>, size: CMJsonField<Number>, prefix: TypeExpression<StringType>? = null) =
    rpad(inStr.toDopeType(), size.toDopeType(), prefix)

fun rpad(inStr: CMJsonField<String>, size: CMJsonField<Number>, prefix: String) =
    rpad(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())

fun rpad(inStr: CMJsonField<String>, size: TypeExpression<NumberType>, prefix: CMJsonField<String>) =
    rpad(inStr.toDopeType(), size, prefix.toDopeType())

fun rpad(inStr: CMJsonField<String>, size: TypeExpression<NumberType>, prefix: TypeExpression<StringType>? = null) =
    rpad(inStr.toDopeType(), size, prefix)

fun rpad(inStr: CMJsonField<String>, size: TypeExpression<NumberType>, prefix: String) =
    rpad(inStr.toDopeType(), size, prefix.toDopeType())

fun rpad(inStr: CMJsonField<String>, size: Number, prefix: TypeExpression<StringType>? = null) =
    rpad(inStr.toDopeType(), size.toDopeType(), prefix)

fun rpad(inStr: CMJsonField<String>, size: Number, prefix: String) =
    rpad(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())

fun rpad(inStr: CMJsonField<String>, size: Number, prefix: CMJsonField<String>) =
    rpad(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())

fun rpad(inStr: TypeExpression<StringType>, size: CMJsonField<Number>, prefix: CMJsonField<String>) =
    rpad(inStr, size.toDopeType(), prefix.toDopeType())

fun rpad(inStr: TypeExpression<StringType>, size: CMJsonField<Number>, prefix: String) =
    rpad(inStr, size.toDopeType(), prefix.toDopeType())

fun rpad(inStr: TypeExpression<StringType>, size: CMJsonField<Number>, prefix: TypeExpression<StringType>? = null) =
    rpad(inStr, size.toDopeType(), prefix)

fun rpad(inStr: TypeExpression<StringType>, size: TypeExpression<NumberType>, prefix: CMJsonField<String>) =
    rpad(inStr, size, prefix.toDopeType())

fun rpad(inStr: TypeExpression<StringType>, size: TypeExpression<NumberType>, prefix: String) =
    rpad(inStr, size, prefix.toDopeType())

fun rpad(inStr: String, size: CMJsonField<Number>, prefix: CMJsonField<String>) =
    rpad(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())

fun rpad(inStr: String, size: CMJsonField<Number>, prefix: String) =
    rpad(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())

fun rpad(inStr: String, size: CMJsonField<Number>, prefix: TypeExpression<StringType>? = null) =
    rpad(inStr.toDopeType(), size.toDopeType(), prefix)

fun rpad(inStr: String, size: Number, prefix: CMJsonField<String>) =
    rpad(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())

fun rpad(inStr: String, size: TypeExpression<NumberType>, prefix: CMJsonField<String>) =
    rpad(inStr.toDopeType(), size, prefix.toDopeType())
