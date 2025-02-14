package ch.ergon.dope.resolvable.expression.single.type.function.string

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.StringType

class InitCapExpression(inStr: TypeExpression<StringType>) : FunctionExpression<StringType>(
    "INITCAP",
    inStr,
)

fun initCap(inStr: TypeExpression<StringType>) = InitCapExpression(inStr)

fun initCap(inStr: String) = initCap(inStr.toDopeType())

class TitleExpression(inStr: TypeExpression<StringType>) : FunctionExpression<StringType>(
    "TITLE",
    inStr,
)

fun title(inStr: TypeExpression<StringType>) = TitleExpression(inStr)

fun title(inStr: String) = title(inStr.toDopeType())
