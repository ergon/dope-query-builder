package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.string.substring
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun CMJsonField<String>.substring(
    startPos: TypeExpression<NumberType>,
    length: TypeExpression<NumberType>? = null,
) = toDopeType().substring(startPos, length)

fun TypeExpression<StringType>.substring(
    startPos: CMJsonField<Number>,
    length: TypeExpression<NumberType>? = null,
) = substring(startPos.toDopeType(), length)

fun TypeExpression<StringType>.substring(
    startPos: TypeExpression<NumberType>,
    length: CMJsonField<Number>,
) = substring(startPos, length.toDopeType())

fun CMJsonField<String>.substring(
    startPos: CMJsonField<Number>,
    length: TypeExpression<NumberType>? = null,
) = toDopeType().substring(startPos.toDopeType(), length)

fun CMJsonField<String>.substring(startPos: TypeExpression<NumberType>, length: CMJsonField<Number>) =
    toDopeType().substring(startPos, length.toDopeType())

fun TypeExpression<StringType>.substring(startPos: CMJsonField<Number>, length: CMJsonField<Number>) =
    substring(startPos.toDopeType(), length.toDopeType())

fun CMJsonField<String>.substring(startPos: CMJsonField<Number>, length: CMJsonField<Number>) =
    toDopeType().substring(startPos.toDopeType(), length.toDopeType())

fun CMJsonField<String>.substring(startPos: TypeExpression<NumberType>, length: Int) =
    toDopeType().substring(startPos, length.toDopeType())

fun TypeExpression<StringType>.substring(startPos: CMJsonField<Number>, length: Int) =
    substring(startPos.toDopeType(), length.toDopeType())

fun CMJsonField<String>.substring(startPos: CMJsonField<Number>, length: Int) =
    toDopeType().substring(startPos.toDopeType(), length.toDopeType())

fun TypeExpression<StringType>.substring(startPos: Int, length: CMJsonField<Number>) =
    substring(startPos.toDopeType(), length.toDopeType())

fun CMJsonField<String>.substring(startPos: Int, length: TypeExpression<NumberType>? = null) =
    toDopeType().substring(startPos.toDopeType(), length)

fun CMJsonField<String>.substring(startPos: Int, length: CMJsonField<Number>) =
    toDopeType().substring(startPos.toDopeType(), length.toDopeType())

fun String.substring(startPos: CMJsonField<Number>, length: TypeExpression<NumberType>? = null) =
    toDopeType().substring(startPos.toDopeType(), length)

fun String.substring(startPos: TypeExpression<NumberType>, length: CMJsonField<Number>) =
    toDopeType().substring(startPos, length.toDopeType())

fun String.substring(startPos: CMJsonField<Number>, length: CMJsonField<Number>) =
    toDopeType().substring(startPos.toDopeType(), length.toDopeType())

fun CMJsonField<String>.substring(startPos: Int, length: Int) =
    toDopeType().substring(startPos.toDopeType(), length.toDopeType())

fun String.substring(startPos: CMJsonField<Number>, length: Int) =
    toDopeType().substring(startPos.toDopeType(), length.toDopeType())

fun String.substring(startPos: Int, length: CMJsonField<Number>) =
    toDopeType().substring(startPos.toDopeType(), length.toDopeType())
