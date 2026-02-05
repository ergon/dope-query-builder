package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

data class UrlDecodeExpression(val encodedString: TypeExpression<StringType>) :
    FunctionExpression<StringType>(listOf(encodedString))

fun TypeExpression<StringType>.urlDecode() = UrlDecodeExpression(this)

fun String.urlDecode() = toDopeType().urlDecode()
