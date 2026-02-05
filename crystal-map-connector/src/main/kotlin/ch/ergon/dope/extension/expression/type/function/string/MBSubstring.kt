package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.string.mbSubstring
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun CMJsonField<String>.mbSubstring(
    startPos: TypeExpression<NumberType>,
    length: TypeExpression<NumberType>? = null,
) = toDopeType().mbSubstring(startPos, length)

fun TypeExpression<StringType>.mbSubstring(
    startPos: CMJsonField<Number>,
    length: TypeExpression<NumberType>? = null,
) = mbSubstring(startPos.toDopeType(), length)

fun TypeExpression<StringType>.mbSubstring(
    startPos: TypeExpression<NumberType>,
    length: CMJsonField<Number>,
) = mbSubstring(startPos, length.toDopeType())

fun CMJsonField<String>.mbSubstring(
    startPos: CMJsonField<Number>,
    length: TypeExpression<NumberType>? = null,
) = toDopeType().mbSubstring(startPos.toDopeType(), length)

fun CMJsonField<String>.mbSubstring(startPos: TypeExpression<NumberType>, length: CMJsonField<Number>) =
    toDopeType().mbSubstring(startPos, length.toDopeType())

fun TypeExpression<StringType>.mbSubstring(startPos: CMJsonField<Number>, length: CMJsonField<Number>) =
    mbSubstring(startPos.toDopeType(), length.toDopeType())

fun CMJsonField<String>.mbSubstring(startPos: CMJsonField<Number>, length: CMJsonField<Number>) =
    toDopeType().mbSubstring(startPos.toDopeType(), length.toDopeType())

fun CMJsonField<String>.mbSubstring(startPos: TypeExpression<NumberType>, length: Int) =
    toDopeType().mbSubstring(startPos, length.toDopeType())

fun TypeExpression<StringType>.mbSubstring(startPos: CMJsonField<Number>, length: Int) =
    mbSubstring(startPos.toDopeType(), length.toDopeType())

fun CMJsonField<String>.mbSubstring(startPos: CMJsonField<Number>, length: Int) =
    toDopeType().mbSubstring(startPos.toDopeType(), length.toDopeType())

fun TypeExpression<StringType>.mbSubstring(startPos: Int, length: CMJsonField<Number>) =
    mbSubstring(startPos.toDopeType(), length.toDopeType())

fun CMJsonField<String>.mbSubstring(startPos: Int, length: TypeExpression<NumberType>? = null) =
    toDopeType().mbSubstring(startPos.toDopeType(), length)

fun CMJsonField<String>.mbSubstring(startPos: Int, length: CMJsonField<Number>) =
    toDopeType().mbSubstring(startPos.toDopeType(), length.toDopeType())

fun String.mbSubstring(startPos: CMJsonField<Number>, length: TypeExpression<NumberType>? = null) =
    toDopeType().mbSubstring(startPos.toDopeType(), length)

fun String.mbSubstring(startPos: TypeExpression<NumberType>, length: CMJsonField<Number>) =
    toDopeType().mbSubstring(startPos, length.toDopeType())

fun String.mbSubstring(startPos: CMJsonField<Number>, length: CMJsonField<Number>) =
    toDopeType().mbSubstring(startPos.toDopeType(), length.toDopeType())

fun CMJsonField<String>.mbSubstring(startPos: Int, length: Int) =
    toDopeType().mbSubstring(startPos.toDopeType(), length.toDopeType())

fun String.mbSubstring(startPos: CMJsonField<Number>, length: Int) =
    toDopeType().mbSubstring(startPos.toDopeType(), length.toDopeType())

fun String.mbSubstring(startPos: Int, length: CMJsonField<Number>) =
    toDopeType().mbSubstring(startPos.toDopeType(), length.toDopeType())
