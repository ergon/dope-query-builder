package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.StringType

class MBPosition1Expression(inStr: TypeExpression<StringType>, searchStr: TypeExpression<StringType>) :
    FunctionExpression<StringType>("MB_POSITION1", inStr, searchStr)

fun mbPosition1(inStr: TypeExpression<StringType>, searchStr: TypeExpression<StringType>) =
    MBPosition1Expression(inStr, searchStr)

fun mbPosition1(inStr: TypeExpression<StringType>, searchStr: String) =
    MBPosition1Expression(inStr, searchStr.toDopeType())

fun mbPosition1(inStr: String, searchStr: TypeExpression<StringType>) =
    MBPosition1Expression(inStr.toDopeType(), searchStr)

fun mbPosition1(inStr: String, searchStr: String) =
    MBPosition1Expression(inStr.toDopeType(), searchStr.toDopeType())
