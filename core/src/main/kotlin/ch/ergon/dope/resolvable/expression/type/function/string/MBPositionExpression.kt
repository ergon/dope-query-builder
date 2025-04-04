package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class MBPositionExpression(inStr: TypeExpression<StringType>, searchStr: TypeExpression<StringType>) :
    FunctionExpression<NumberType>(
        "MB_POSITION",
        inStr,
        searchStr,
    )

fun mbPosition(inStr: TypeExpression<StringType>, searchStr: TypeExpression<StringType>) =
    MBPositionExpression(inStr, searchStr)

fun mbPosition(inStr: TypeExpression<StringType>, searchStr: String) = mbPosition(inStr, searchStr.toDopeType())

fun mbPosition(inStr: String, searchStr: TypeExpression<StringType>) = mbPosition(inStr.toDopeType(), searchStr)

fun mbPosition(inStr: String, searchStr: String) = mbPosition(inStr.toDopeType(), searchStr.toDopeType())
