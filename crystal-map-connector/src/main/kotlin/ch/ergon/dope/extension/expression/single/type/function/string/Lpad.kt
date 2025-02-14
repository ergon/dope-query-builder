package ch.ergon.dope.extension.expression.single.type.function.string

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.string.lpad
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun lpad(inStr: CMJsonField<String>, size: CMJsonField<Number>, prefix: CMJsonField<String>) =
    lpad(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())

fun lpad(inStr: CMJsonField<String>, size: CMJsonField<Number>, prefix: TypeExpression<StringType>? = null) =
    lpad(inStr.toDopeType(), size.toDopeType(), prefix)

fun lpad(inStr: CMJsonField<String>, size: CMJsonField<Number>, prefix: String) =
    lpad(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())

fun lpad(inStr: CMJsonField<String>, size: TypeExpression<NumberType>, prefix: CMJsonField<String>) =
    lpad(inStr.toDopeType(), size, prefix.toDopeType())

fun lpad(inStr: CMJsonField<String>, size: TypeExpression<NumberType>, prefix: TypeExpression<StringType>? = null) =
    lpad(inStr.toDopeType(), size, prefix)

fun lpad(inStr: CMJsonField<String>, size: TypeExpression<NumberType>, prefix: String) =
    lpad(inStr.toDopeType(), size, prefix.toDopeType())

fun lpad(inStr: CMJsonField<String>, size: Number, prefix: TypeExpression<StringType>? = null) =
    lpad(inStr.toDopeType(), size.toDopeType(), prefix)

fun lpad(inStr: CMJsonField<String>, size: Number, prefix: String) =
    lpad(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())

fun lpad(inStr: CMJsonField<String>, size: Number, prefix: CMJsonField<String>) =
    lpad(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())

fun lpad(inStr: TypeExpression<StringType>, size: CMJsonField<Number>, prefix: CMJsonField<String>) =
    lpad(inStr, size.toDopeType(), prefix.toDopeType())

fun lpad(inStr: TypeExpression<StringType>, size: CMJsonField<Number>, prefix: String) =
    lpad(inStr, size.toDopeType(), prefix.toDopeType())

fun lpad(inStr: TypeExpression<StringType>, size: CMJsonField<Number>, prefix: TypeExpression<StringType>? = null) =
    lpad(inStr, size.toDopeType(), prefix)

fun lpad(inStr: TypeExpression<StringType>, size: TypeExpression<NumberType>, prefix: CMJsonField<String>) =
    lpad(inStr, size, prefix.toDopeType())

fun lpad(inStr: TypeExpression<StringType>, size: TypeExpression<NumberType>, prefix: String) =
    lpad(inStr, size, prefix.toDopeType())

fun lpad(inStr: String, size: CMJsonField<Number>, prefix: CMJsonField<String>) =
    lpad(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())

fun lpad(inStr: String, size: CMJsonField<Number>, prefix: String) =
    lpad(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())

fun lpad(inStr: String, size: CMJsonField<Number>, prefix: TypeExpression<StringType>? = null) =
    lpad(inStr.toDopeType(), size.toDopeType(), prefix)

fun lpad(inStr: String, size: Number, prefix: CMJsonField<String>) =
    lpad(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())

fun lpad(inStr: String, size: TypeExpression<NumberType>, prefix: CMJsonField<String>) =
    lpad(inStr.toDopeType(), size, prefix.toDopeType())
