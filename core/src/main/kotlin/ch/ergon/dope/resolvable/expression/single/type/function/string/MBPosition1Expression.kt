package ch.ergon.dope.resolvable.expression.single.type.function.string

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class MBPosition1Expression(inStr: TypeExpression<StringType>, searchStr: TypeExpression<StringType>) :
    FunctionExpression<NumberType>(
        "MB_POSITION1",
        inStr,
        searchStr,
    )

fun mbPosition1(inStr: TypeExpression<StringType>, searchStr: TypeExpression<StringType>) =
    MBPosition1Expression(inStr, searchStr)

fun mbPosition1(inStr: TypeExpression<StringType>, searchStr: String) =
    mbPosition1(inStr, searchStr.toDopeType())

fun mbPosition1(inStr: String, searchStr: TypeExpression<StringType>) =
    mbPosition1(inStr.toDopeType(), searchStr)

fun mbPosition1(inStr: String, searchStr: String) =
    mbPosition1(inStr.toDopeType(), searchStr.toDopeType())
