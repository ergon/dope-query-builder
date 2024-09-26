package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.StringType

class InitCapExpression(inStr: TypeExpression<StringType>) : FunctionExpression<StringType>("INITCAP", inStr)

fun initCap(inStr: TypeExpression<StringType>) = InitCapExpression(inStr)

fun initCap(inStr: String) = InitCapExpression(inStr.toDopeType())

class TitleExpression(inStr: TypeExpression<StringType>) : FunctionExpression<StringType>("TITLE", inStr)

fun title(inStr: TypeExpression<StringType>) = TitleExpression(inStr)

fun title(inStr: String) = TitleExpression(inStr.toDopeType())
