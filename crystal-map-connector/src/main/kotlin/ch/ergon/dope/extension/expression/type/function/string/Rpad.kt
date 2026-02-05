package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.string.rpad
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun CMJsonField<String>.rpad(size: CMJsonField<Number>, prefix: CMJsonField<String>) =
    toDopeType().rpad(size.toDopeType(), prefix.toDopeType())

fun CMJsonField<String>.rpad(size: CMJsonField<Number>, prefix: TypeExpression<StringType>? = null) =
    toDopeType().rpad(size.toDopeType(), prefix)

fun CMJsonField<String>.rpad(size: CMJsonField<Number>, prefix: String) =
    toDopeType().rpad(size.toDopeType(), prefix.toDopeType())

fun CMJsonField<String>.rpad(size: TypeExpression<NumberType>, prefix: CMJsonField<String>) =
    toDopeType().rpad(size, prefix.toDopeType())

fun CMJsonField<String>.rpad(size: TypeExpression<NumberType>, prefix: TypeExpression<StringType>? = null) =
    toDopeType().rpad(size, prefix)

fun CMJsonField<String>.rpad(size: TypeExpression<NumberType>, prefix: String) =
    toDopeType().rpad(size, prefix.toDopeType())

fun CMJsonField<String>.rpad(size: Number, prefix: TypeExpression<StringType>? = null) =
    toDopeType().rpad(size.toDopeType(), prefix)

fun CMJsonField<String>.rpad(size: Number, prefix: String) =
    toDopeType().rpad(size.toDopeType(), prefix.toDopeType())

fun CMJsonField<String>.rpad(size: Number, prefix: CMJsonField<String>) =
    toDopeType().rpad(size.toDopeType(), prefix.toDopeType())

fun TypeExpression<StringType>.rpad(size: CMJsonField<Number>, prefix: CMJsonField<String>) =
    rpad(size.toDopeType(), prefix.toDopeType())

fun TypeExpression<StringType>.rpad(size: CMJsonField<Number>, prefix: String) =
    rpad(size.toDopeType(), prefix.toDopeType())

fun TypeExpression<StringType>.rpad(size: CMJsonField<Number>, prefix: TypeExpression<StringType>? = null) =
    rpad(size.toDopeType(), prefix)

fun TypeExpression<StringType>.rpad(size: TypeExpression<NumberType>, prefix: CMJsonField<String>) =
    rpad(size, prefix.toDopeType())

fun TypeExpression<StringType>.rpad(size: TypeExpression<NumberType>, prefix: String) =
    rpad(size, prefix.toDopeType())

fun String.rpad(size: CMJsonField<Number>, prefix: CMJsonField<String>) =
    toDopeType().rpad(size.toDopeType(), prefix.toDopeType())

fun String.rpad(size: CMJsonField<Number>, prefix: String) =
    toDopeType().rpad(size.toDopeType(), prefix.toDopeType())

fun String.rpad(size: CMJsonField<Number>, prefix: TypeExpression<StringType>? = null) =
    toDopeType().rpad(size.toDopeType(), prefix)

fun String.rpad(size: Number, prefix: CMJsonField<String>) =
    toDopeType().rpad(size.toDopeType(), prefix.toDopeType())

fun String.rpad(size: TypeExpression<NumberType>, prefix: CMJsonField<String>) =
    toDopeType().rpad(size, prefix.toDopeType())
