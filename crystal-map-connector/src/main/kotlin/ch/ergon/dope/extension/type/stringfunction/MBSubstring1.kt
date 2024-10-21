package ch.ergon.dope.extension.type.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.mbSubstring1
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun mbSubstring1(
    inStr: CMJsonField<String>,
    startPos: TypeExpression<NumberType>,
    length: TypeExpression<NumberType>? = null,
) = mbSubstring1(inStr.toDopeType(), startPos, length)

fun mbSubstring1(
    inStr: TypeExpression<StringType>,
    startPos: CMJsonField<Number>,
    length: TypeExpression<NumberType>? = null,
) = mbSubstring1(inStr, startPos.toDopeType(), length)

fun mbSubstring1(
    inStr: TypeExpression<StringType>,
    startPos: TypeExpression<NumberType>,
    length: CMJsonField<Number>,
) = mbSubstring1(inStr, startPos, length.toDopeType())

fun mbSubstring1(
    inStr: CMJsonField<String>,
    startPos: CMJsonField<Number>,
    length: TypeExpression<NumberType>? = null,
) = mbSubstring1(inStr.toDopeType(), startPos.toDopeType(), length)

fun mbSubstring1(inStr: CMJsonField<String>, startPos: TypeExpression<NumberType>, length: CMJsonField<Number>) =
    mbSubstring1(inStr.toDopeType(), startPos, length.toDopeType())

fun mbSubstring1(inStr: TypeExpression<StringType>, startPos: CMJsonField<Number>, length: CMJsonField<Number>) =
    mbSubstring1(inStr, startPos.toDopeType(), length.toDopeType())

fun mbSubstring1(inStr: CMJsonField<String>, startPos: CMJsonField<Number>, length: CMJsonField<Number>) =
    mbSubstring1(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())

fun mbSubstring1(inStr: CMJsonField<String>, startPos: TypeExpression<NumberType>, length: Int) =
    mbSubstring1(inStr.toDopeType(), startPos, length.toDopeType())

fun mbSubstring1(inStr: TypeExpression<StringType>, startPos: CMJsonField<Number>, length: Int) =
    mbSubstring1(inStr, startPos.toDopeType(), length.toDopeType())

fun mbSubstring1(inStr: CMJsonField<String>, startPos: CMJsonField<Number>, length: Int) =
    mbSubstring1(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())

fun mbSubstring1(inStr: TypeExpression<StringType>, startPos: Int, length: CMJsonField<Number>) =
    mbSubstring1(inStr, startPos.toDopeType(), length.toDopeType())

fun mbSubstring1(inStr: CMJsonField<String>, startPos: Int, length: TypeExpression<NumberType>? = null) =
    mbSubstring1(inStr.toDopeType(), startPos.toDopeType(), length)

fun mbSubstring1(inStr: CMJsonField<String>, startPos: Int, length: CMJsonField<Number>) =
    mbSubstring1(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())

fun mbSubstring1(inStr: String, startPos: CMJsonField<Number>, length: TypeExpression<NumberType>? = null) =
    mbSubstring1(inStr.toDopeType(), startPos.toDopeType(), length)

fun mbSubstring1(inStr: String, startPos: TypeExpression<NumberType>, length: CMJsonField<Number>) =
    mbSubstring1(inStr.toDopeType(), startPos, length.toDopeType())

fun mbSubstring1(inStr: String, startPos: CMJsonField<Number>, length: CMJsonField<Number>) =
    mbSubstring1(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())

fun mbSubstring1(inStr: CMJsonField<String>, startPos: Int, length: Int) =
    mbSubstring1(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())

fun mbSubstring1(inStr: String, startPos: CMJsonField<Number>, length: Int) =
    mbSubstring1(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())

fun mbSubstring1(inStr: String, startPos: Int, length: CMJsonField<Number>) =
    mbSubstring1(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())
