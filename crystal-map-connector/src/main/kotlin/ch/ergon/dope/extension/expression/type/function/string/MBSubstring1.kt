package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.string.mbSubstring1
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun CMJsonField<String>.mbSubstring1(
    startPos: TypeExpression<NumberType>,
    length: TypeExpression<NumberType>? = null,
) = toDopeType().mbSubstring1(startPos, length)

fun TypeExpression<StringType>.mbSubstring1(
    startPos: CMJsonField<Number>,
    length: TypeExpression<NumberType>? = null,
) = mbSubstring1(startPos.toDopeType(), length)

fun TypeExpression<StringType>.mbSubstring1(
    startPos: TypeExpression<NumberType>,
    length: CMJsonField<Number>,
) = mbSubstring1(startPos, length.toDopeType())

fun CMJsonField<String>.mbSubstring1(
    startPos: CMJsonField<Number>,
    length: TypeExpression<NumberType>? = null,
) = toDopeType().mbSubstring1(startPos.toDopeType(), length)

fun CMJsonField<String>.mbSubstring1(startPos: TypeExpression<NumberType>, length: CMJsonField<Number>) =
    toDopeType().mbSubstring1(startPos, length.toDopeType())

fun TypeExpression<StringType>.mbSubstring1(startPos: CMJsonField<Number>, length: CMJsonField<Number>) =
    mbSubstring1(startPos.toDopeType(), length.toDopeType())

fun CMJsonField<String>.mbSubstring1(startPos: CMJsonField<Number>, length: CMJsonField<Number>) =
    toDopeType().mbSubstring1(startPos.toDopeType(), length.toDopeType())

fun CMJsonField<String>.mbSubstring1(startPos: TypeExpression<NumberType>, length: Int) =
    toDopeType().mbSubstring1(startPos, length.toDopeType())

fun TypeExpression<StringType>.mbSubstring1(startPos: CMJsonField<Number>, length: Int) =
    mbSubstring1(startPos.toDopeType(), length.toDopeType())

fun CMJsonField<String>.mbSubstring1(startPos: CMJsonField<Number>, length: Int) =
    toDopeType().mbSubstring1(startPos.toDopeType(), length.toDopeType())

fun TypeExpression<StringType>.mbSubstring1(startPos: Int, length: CMJsonField<Number>) =
    mbSubstring1(startPos.toDopeType(), length.toDopeType())

fun CMJsonField<String>.mbSubstring1(startPos: Int, length: TypeExpression<NumberType>? = null) =
    toDopeType().mbSubstring1(startPos.toDopeType(), length)

fun CMJsonField<String>.mbSubstring1(startPos: Int, length: CMJsonField<Number>) =
    toDopeType().mbSubstring1(startPos.toDopeType(), length.toDopeType())

fun String.mbSubstring1(startPos: CMJsonField<Number>, length: TypeExpression<NumberType>? = null) =
    toDopeType().mbSubstring1(startPos.toDopeType(), length)

fun String.mbSubstring1(startPos: TypeExpression<NumberType>, length: CMJsonField<Number>) =
    toDopeType().mbSubstring1(startPos, length.toDopeType())

fun String.mbSubstring1(startPos: CMJsonField<Number>, length: CMJsonField<Number>) =
    toDopeType().mbSubstring1(startPos.toDopeType(), length.toDopeType())

fun CMJsonField<String>.mbSubstring1(startPos: Int, length: Int) =
    toDopeType().mbSubstring1(startPos.toDopeType(), length.toDopeType())

fun String.mbSubstring1(startPos: CMJsonField<Number>, length: Int) =
    toDopeType().mbSubstring1(startPos.toDopeType(), length.toDopeType())

fun String.mbSubstring1(startPos: Int, length: CMJsonField<Number>) =
    toDopeType().mbSubstring1(startPos.toDopeType(), length.toDopeType())
