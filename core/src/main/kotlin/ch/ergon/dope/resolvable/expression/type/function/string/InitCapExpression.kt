package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

data class InitCapExpression(val inStr: TypeExpression<StringType>) :
    FunctionExpression<StringType>(listOf(inStr))

data class TitleExpression(val inStr: TypeExpression<StringType>) :
    FunctionExpression<StringType>(listOf(inStr))

fun TypeExpression<StringType>.initCap() = InitCapExpression(this)

fun TypeExpression<StringType>.title() = TitleExpression(this)

fun String.initCap() = toDopeType().initCap()

fun String.title() = toDopeType().title()
