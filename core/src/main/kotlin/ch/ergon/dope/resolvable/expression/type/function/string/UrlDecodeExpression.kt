package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

class UrlDecodeExpression(encodedString: TypeExpression<StringType>) :
    FunctionExpression<StringType>("URL_DECODE", encodedString)

fun urlDecode(encodedString: TypeExpression<StringType>) = UrlDecodeExpression(encodedString)

fun urlDecode(encodedString: String) = urlDecode(encodedString.toDopeType())
