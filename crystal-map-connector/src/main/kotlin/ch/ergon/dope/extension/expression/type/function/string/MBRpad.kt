package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.string.mbRpad
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun CMJsonField<String>.mbRpad(size: CMJsonField<Number>, prefix: CMJsonField<String>) =
    toDopeType().mbRpad(size.toDopeType(), prefix.toDopeType())

fun CMJsonField<String>.mbRpad(size: CMJsonField<Number>, prefix: TypeExpression<StringType>? = null) =
    toDopeType().mbRpad(size.toDopeType(), prefix)

fun CMJsonField<String>.mbRpad(size: CMJsonField<Number>, prefix: String) =
    toDopeType().mbRpad(size.toDopeType(), prefix.toDopeType())

fun CMJsonField<String>.mbRpad(size: TypeExpression<NumberType>, prefix: CMJsonField<String>) =
    toDopeType().mbRpad(size, prefix.toDopeType())

fun CMJsonField<String>.mbRpad(size: TypeExpression<NumberType>, prefix: TypeExpression<StringType>? = null) =
    toDopeType().mbRpad(size, prefix)

fun CMJsonField<String>.mbRpad(size: TypeExpression<NumberType>, prefix: String) =
    toDopeType().mbRpad(size, prefix.toDopeType())

fun CMJsonField<String>.mbRpad(size: Number, prefix: TypeExpression<StringType>? = null) =
    toDopeType().mbRpad(size.toDopeType(), prefix)

fun CMJsonField<String>.mbRpad(size: Number, prefix: String) =
    toDopeType().mbRpad(size.toDopeType(), prefix.toDopeType())

fun CMJsonField<String>.mbRpad(size: Number, prefix: CMJsonField<String>) =
    toDopeType().mbRpad(size.toDopeType(), prefix.toDopeType())

fun TypeExpression<StringType>.mbRpad(size: CMJsonField<Number>, prefix: CMJsonField<String>) =
    mbRpad(size.toDopeType(), prefix.toDopeType())

fun TypeExpression<StringType>.mbRpad(size: CMJsonField<Number>, prefix: String) =
    mbRpad(size.toDopeType(), prefix.toDopeType())

fun TypeExpression<StringType>.mbRpad(size: CMJsonField<Number>, prefix: TypeExpression<StringType>? = null) =
    mbRpad(size.toDopeType(), prefix)

fun TypeExpression<StringType>.mbRpad(size: TypeExpression<NumberType>, prefix: CMJsonField<String>) =
    mbRpad(size, prefix.toDopeType())

fun String.mbRpad(size: CMJsonField<Number>, prefix: CMJsonField<String>) =
    toDopeType().mbRpad(size.toDopeType(), prefix.toDopeType())

fun String.mbRpad(size: CMJsonField<Number>, prefix: String) =
    toDopeType().mbRpad(size.toDopeType(), prefix.toDopeType())

fun String.mbRpad(size: CMJsonField<Number>, prefix: TypeExpression<StringType>? = null) =
    toDopeType().mbRpad(size.toDopeType(), prefix)

fun String.mbRpad(size: Number, prefix: CMJsonField<String>) =
    toDopeType().mbRpad(size.toDopeType(), prefix.toDopeType())

fun String.mbRpad(size: TypeExpression<NumberType>, prefix: CMJsonField<String>) =
    toDopeType().mbRpad(size, prefix.toDopeType())
