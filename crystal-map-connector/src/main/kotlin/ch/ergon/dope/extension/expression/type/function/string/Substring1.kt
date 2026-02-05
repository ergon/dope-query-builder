package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.string.substring1
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun CMJsonField<String>.substring1(
    startPos: TypeExpression<NumberType>,
    length: TypeExpression<NumberType>? = null,
) = toDopeType().substring1(startPos, length)

fun TypeExpression<StringType>.substring1(
    startPos: CMJsonField<Number>,
    length: TypeExpression<NumberType>? = null,
) = substring1(startPos.toDopeType(), length)

fun TypeExpression<StringType>.substring1(
    startPos: TypeExpression<NumberType>,
    length: CMJsonField<Number>,
) = substring1(startPos, length.toDopeType())

fun CMJsonField<String>.substring1(
    startPos: CMJsonField<Number>,
    length: TypeExpression<NumberType>? = null,
) = toDopeType().substring1(startPos.toDopeType(), length)

fun CMJsonField<String>.substring1(startPos: TypeExpression<NumberType>, length: CMJsonField<Number>) =
    toDopeType().substring1(startPos, length.toDopeType())

fun TypeExpression<StringType>.substring1(startPos: CMJsonField<Number>, length: CMJsonField<Number>) =
    substring1(startPos.toDopeType(), length.toDopeType())

fun CMJsonField<String>.substring1(startPos: CMJsonField<Number>, length: CMJsonField<Number>) =
    toDopeType().substring1(startPos.toDopeType(), length.toDopeType())

fun CMJsonField<String>.substring1(startPos: TypeExpression<NumberType>, length: Int) =
    toDopeType().substring1(startPos, length.toDopeType())

fun TypeExpression<StringType>.substring1(startPos: CMJsonField<Number>, length: Int) =
    substring1(startPos.toDopeType(), length.toDopeType())

fun CMJsonField<String>.substring1(startPos: CMJsonField<Number>, length: Int) =
    toDopeType().substring1(startPos.toDopeType(), length.toDopeType())

fun TypeExpression<StringType>.substring1(startPos: Int, length: CMJsonField<Number>) =
    substring1(startPos.toDopeType(), length.toDopeType())

fun CMJsonField<String>.substring1(startPos: Int, length: TypeExpression<NumberType>? = null) =
    toDopeType().substring1(startPos.toDopeType(), length)

fun CMJsonField<String>.substring1(startPos: Int, length: CMJsonField<Number>) =
    toDopeType().substring1(startPos.toDopeType(), length.toDopeType())

fun String.substring1(startPos: CMJsonField<Number>, length: TypeExpression<NumberType>? = null) =
    toDopeType().substring1(startPos.toDopeType(), length)

fun String.substring1(startPos: TypeExpression<NumberType>, length: CMJsonField<Number>) =
    toDopeType().substring1(startPos, length.toDopeType())

fun String.substring1(startPos: CMJsonField<Number>, length: CMJsonField<Number>) =
    toDopeType().substring1(startPos.toDopeType(), length.toDopeType())

fun CMJsonField<String>.substring1(startPos: Int, length: Int) =
    toDopeType().substring1(startPos.toDopeType(), length.toDopeType())

fun String.substring1(startPos: CMJsonField<Number>, length: Int) =
    toDopeType().substring1(startPos.toDopeType(), length.toDopeType())

fun String.substring1(startPos: Int, length: CMJsonField<Number>) =
    toDopeType().substring1(startPos.toDopeType(), length.toDopeType())
