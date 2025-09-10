package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

data class InitCapExpression(val inStr: TypeExpression<StringType>) :
    FunctionExpression<StringType>("INITCAP", listOf(inStr))

fun initCap(inStr: TypeExpression<StringType>) = InitCapExpression(inStr)

fun initCap(inStr: String) = initCap(inStr.toDopeType())

data class TitleExpression(val inStr: TypeExpression<StringType>) :
    FunctionExpression<StringType>("TITLE", listOf(inStr))

fun title(inStr: TypeExpression<StringType>) = TitleExpression(inStr)

fun title(inStr: String) = title(inStr.toDopeType())
