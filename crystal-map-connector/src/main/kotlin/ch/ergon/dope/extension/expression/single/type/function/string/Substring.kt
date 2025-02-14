package ch.ergon.dope.extension.expression.single.type.function.string

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.string.substring
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun substring(
    inStr: CMJsonField<String>,
    startPos: TypeExpression<NumberType>,
    length: TypeExpression<NumberType>? = null,
) = substring(inStr.toDopeType(), startPos, length)

fun substring(
    inStr: TypeExpression<StringType>,
    startPos: CMJsonField<Number>,
    length: TypeExpression<NumberType>? = null,
) = substring(inStr, startPos.toDopeType(), length)

fun substring(
    inStr: TypeExpression<StringType>,
    startPos: TypeExpression<NumberType>,
    length: CMJsonField<Number>,
) = substring(inStr, startPos, length.toDopeType())

fun substring(
    inStr: CMJsonField<String>,
    startPos: CMJsonField<Number>,
    length: TypeExpression<NumberType>? = null,
) = substring(inStr.toDopeType(), startPos.toDopeType(), length)

fun substring(inStr: CMJsonField<String>, startPos: TypeExpression<NumberType>, length: CMJsonField<Number>) =
    substring(inStr.toDopeType(), startPos, length.toDopeType())

fun substring(inStr: TypeExpression<StringType>, startPos: CMJsonField<Number>, length: CMJsonField<Number>) =
    substring(inStr, startPos.toDopeType(), length.toDopeType())

fun substring(inStr: CMJsonField<String>, startPos: CMJsonField<Number>, length: CMJsonField<Number>) =
    substring(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())

fun substring(inStr: CMJsonField<String>, startPos: TypeExpression<NumberType>, length: Int) =
    substring(inStr.toDopeType(), startPos, length.toDopeType())

fun substring(inStr: TypeExpression<StringType>, startPos: CMJsonField<Number>, length: Int) =
    substring(inStr, startPos.toDopeType(), length.toDopeType())

fun substring(inStr: CMJsonField<String>, startPos: CMJsonField<Number>, length: Int) =
    substring(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())

fun substring(inStr: TypeExpression<StringType>, startPos: Int, length: CMJsonField<Number>) =
    substring(inStr, startPos.toDopeType(), length.toDopeType())

fun substring(inStr: CMJsonField<String>, startPos: Int, length: TypeExpression<NumberType>? = null) =
    substring(inStr.toDopeType(), startPos.toDopeType(), length)

fun substring(inStr: CMJsonField<String>, startPos: Int, length: CMJsonField<Number>) =
    substring(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())

fun substring(inStr: String, startPos: CMJsonField<Number>, length: TypeExpression<NumberType>? = null) =
    substring(inStr.toDopeType(), startPos.toDopeType(), length)

fun substring(inStr: String, startPos: TypeExpression<NumberType>, length: CMJsonField<Number>) =
    substring(inStr.toDopeType(), startPos, length.toDopeType())

fun substring(inStr: String, startPos: CMJsonField<Number>, length: CMJsonField<Number>) =
    substring(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())

fun substring(inStr: CMJsonField<String>, startPos: Int, length: Int) =
    substring(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())

fun substring(inStr: String, startPos: CMJsonField<Number>, length: Int) =
    substring(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())

fun substring(inStr: String, startPos: Int, length: CMJsonField<Number>) =
    substring(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())
