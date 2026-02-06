package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.string.mbLpad
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun CMJsonField<String>.mbLpad(size: CMJsonField<Number>, char: CMJsonField<String>) =
    toDopeType().mbLpad(size.toDopeType(), char.toDopeType())

fun CMJsonField<String>.mbLpad(size: CMJsonField<Number>, char: TypeExpression<StringType>? = null) =
    toDopeType().mbLpad(size.toDopeType(), char)

fun CMJsonField<String>.mbLpad(size: CMJsonField<Number>, char: String) =
    toDopeType().mbLpad(size.toDopeType(), char.toDopeType())

fun CMJsonField<String>.mbLpad(size: TypeExpression<NumberType>, char: CMJsonField<String>) =
    toDopeType().mbLpad(size, char.toDopeType())

fun CMJsonField<String>.mbLpad(size: TypeExpression<NumberType>, char: TypeExpression<StringType>? = null) =
    toDopeType().mbLpad(size, char)

fun CMJsonField<String>.mbLpad(size: TypeExpression<NumberType>, char: String) =
    toDopeType().mbLpad(size, char.toDopeType())

fun CMJsonField<String>.mbLpad(size: Number, char: TypeExpression<StringType>? = null) =
    toDopeType().mbLpad(size.toDopeType(), char)

fun CMJsonField<String>.mbLpad(size: Number, char: String) =
    toDopeType().mbLpad(size.toDopeType(), char.toDopeType())

fun CMJsonField<String>.mbLpad(size: Number, char: CMJsonField<String>) =
    toDopeType().mbLpad(size.toDopeType(), char.toDopeType())

fun TypeExpression<StringType>.mbLpad(size: CMJsonField<Number>, char: CMJsonField<String>) =
    mbLpad(size.toDopeType(), char.toDopeType())

fun TypeExpression<StringType>.mbLpad(size: CMJsonField<Number>, char: String) =
    mbLpad(size.toDopeType(), char.toDopeType())

fun TypeExpression<StringType>.mbLpad(size: CMJsonField<Number>, char: TypeExpression<StringType>? = null) =
    mbLpad(size.toDopeType(), char)

fun TypeExpression<StringType>.mbLpad(size: TypeExpression<NumberType>, char: CMJsonField<String>) =
    mbLpad(size, char.toDopeType())

fun TypeExpression<StringType>.mbLpad(size: TypeExpression<NumberType>, char: String) =
    mbLpad(size, char.toDopeType())

fun String.mbLpad(size: CMJsonField<Number>, char: CMJsonField<String>) =
    toDopeType().mbLpad(size.toDopeType(), char.toDopeType())

fun String.mbLpad(size: CMJsonField<Number>, char: String) =
    toDopeType().mbLpad(size.toDopeType(), char.toDopeType())

fun String.mbLpad(size: CMJsonField<Number>, char: TypeExpression<StringType>) =
    toDopeType().mbLpad(size.toDopeType(), char)

fun String.mbLpad(size: Number, char: CMJsonField<String>) =
    toDopeType().mbLpad(size.toDopeType(), char.toDopeType())

fun String.mbLpad(size: TypeExpression<NumberType>, char: CMJsonField<String>) =
    toDopeType().mbLpad(size, char.toDopeType())
