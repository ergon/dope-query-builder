package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.string.substring1
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun substring1(
    inStr: CMJsonField<String>,
    startPos: TypeExpression<NumberType>,
    length: TypeExpression<NumberType>? = null,
) = substring1(inStr.toDopeType(), startPos, length)

fun substring1(
    inStr: TypeExpression<StringType>,
    startPos: CMJsonField<Number>,
    length: TypeExpression<NumberType>? = null,
) = substring1(inStr, startPos.toDopeType(), length)

fun substring1(
    inStr: TypeExpression<StringType>,
    startPos: TypeExpression<NumberType>,
    length: CMJsonField<Number>,
) = substring1(inStr, startPos, length.toDopeType())

fun substring1(
    inStr: CMJsonField<String>,
    startPos: CMJsonField<Number>,
    length: TypeExpression<NumberType>? = null,
) = substring1(inStr.toDopeType(), startPos.toDopeType(), length)

fun substring1(inStr: CMJsonField<String>, startPos: TypeExpression<NumberType>, length: CMJsonField<Number>) =
    substring1(inStr.toDopeType(), startPos, length.toDopeType())

fun substring1(inStr: TypeExpression<StringType>, startPos: CMJsonField<Number>, length: CMJsonField<Number>) =
    substring1(inStr, startPos.toDopeType(), length.toDopeType())

fun substring1(inStr: CMJsonField<String>, startPos: CMJsonField<Number>, length: CMJsonField<Number>) =
    substring1(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())

fun substring1(inStr: CMJsonField<String>, startPos: TypeExpression<NumberType>, length: Int) =
    substring1(inStr.toDopeType(), startPos, length.toDopeType())

fun substring1(inStr: TypeExpression<StringType>, startPos: CMJsonField<Number>, length: Int) =
    substring1(inStr, startPos.toDopeType(), length.toDopeType())

fun substring1(inStr: CMJsonField<String>, startPos: CMJsonField<Number>, length: Int) =
    substring1(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())

fun substring1(inStr: TypeExpression<StringType>, startPos: Int, length: CMJsonField<Number>) =
    substring1(inStr, startPos.toDopeType(), length.toDopeType())

fun substring1(inStr: CMJsonField<String>, startPos: Int, length: TypeExpression<NumberType>? = null) =
    substring1(inStr.toDopeType(), startPos.toDopeType(), length)

fun substring1(inStr: CMJsonField<String>, startPos: Int, length: CMJsonField<Number>) =
    substring1(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())

fun substring1(inStr: String, startPos: CMJsonField<Number>, length: TypeExpression<NumberType>? = null) =
    substring1(inStr.toDopeType(), startPos.toDopeType(), length)

fun substring1(inStr: String, startPos: TypeExpression<NumberType>, length: CMJsonField<Number>) =
    substring1(inStr.toDopeType(), startPos, length.toDopeType())

fun substring1(inStr: String, startPos: CMJsonField<Number>, length: CMJsonField<Number>) =
    substring1(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())

fun substring1(inStr: CMJsonField<String>, startPos: Int, length: Int) =
    substring1(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())

fun substring1(inStr: String, startPos: CMJsonField<Number>, length: Int) =
    substring1(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())

fun substring1(inStr: String, startPos: Int, length: CMJsonField<Number>) =
    substring1(inStr.toDopeType(), startPos.toDopeType(), length.toDopeType())
