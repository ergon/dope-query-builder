package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.string.lpad
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun CMJsonField<String>.lpad(size: CMJsonField<Number>, prefix: CMJsonField<String>) =
    toDopeType().lpad(size.toDopeType(), prefix.toDopeType())

fun CMJsonField<String>.lpad(size: CMJsonField<Number>, prefix: TypeExpression<StringType>? = null) =
    toDopeType().lpad(size.toDopeType(), prefix)

fun CMJsonField<String>.lpad(size: CMJsonField<Number>, prefix: String) =
    toDopeType().lpad(size.toDopeType(), prefix.toDopeType())

fun CMJsonField<String>.lpad(size: TypeExpression<NumberType>, prefix: CMJsonField<String>) =
    toDopeType().lpad(size, prefix.toDopeType())

fun CMJsonField<String>.lpad(size: TypeExpression<NumberType>, prefix: TypeExpression<StringType>? = null) =
    toDopeType().lpad(size, prefix)

fun CMJsonField<String>.lpad(size: TypeExpression<NumberType>, prefix: String) =
    toDopeType().lpad(size, prefix.toDopeType())

fun CMJsonField<String>.lpad(size: Number, prefix: TypeExpression<StringType>? = null) =
    toDopeType().lpad(size.toDopeType(), prefix)

fun CMJsonField<String>.lpad(size: Number, prefix: String) =
    toDopeType().lpad(size.toDopeType(), prefix.toDopeType())

fun CMJsonField<String>.lpad(size: Number, prefix: CMJsonField<String>) =
    toDopeType().lpad(size.toDopeType(), prefix.toDopeType())

fun TypeExpression<StringType>.lpad(size: CMJsonField<Number>, prefix: CMJsonField<String>) =
    lpad(size.toDopeType(), prefix.toDopeType())

fun TypeExpression<StringType>.lpad(size: CMJsonField<Number>, prefix: String) =
    lpad(size.toDopeType(), prefix.toDopeType())

fun TypeExpression<StringType>.lpad(size: CMJsonField<Number>, prefix: TypeExpression<StringType>? = null) =
    lpad(size.toDopeType(), prefix)

fun TypeExpression<StringType>.lpad(size: TypeExpression<NumberType>, prefix: CMJsonField<String>) =
    lpad(size, prefix.toDopeType())

fun TypeExpression<StringType>.lpad(size: TypeExpression<NumberType>, prefix: String) =
    lpad(size, prefix.toDopeType())

fun String.lpad(size: CMJsonField<Number>, prefix: CMJsonField<String>) =
    toDopeType().lpad(size.toDopeType(), prefix.toDopeType())

fun String.lpad(size: CMJsonField<Number>, prefix: String) =
    toDopeType().lpad(size.toDopeType(), prefix.toDopeType())

fun String.lpad(size: CMJsonField<Number>, prefix: TypeExpression<StringType>? = null) =
    toDopeType().lpad(size.toDopeType(), prefix)

fun String.lpad(size: Number, prefix: CMJsonField<String>) =
    toDopeType().lpad(size.toDopeType(), prefix.toDopeType())

fun String.lpad(size: TypeExpression<NumberType>, prefix: CMJsonField<String>) =
    toDopeType().lpad(size, prefix.toDopeType())
