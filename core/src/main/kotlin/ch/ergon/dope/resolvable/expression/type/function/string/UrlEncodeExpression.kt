package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

data class UrlEncodeExpression(val string: TypeExpression<StringType>) :
    FunctionExpression<StringType>(listOf(string))

fun TypeExpression<StringType>.urlEncode() = UrlEncodeExpression(this)

fun String.urlEncode() = toDopeType().urlEncode()
