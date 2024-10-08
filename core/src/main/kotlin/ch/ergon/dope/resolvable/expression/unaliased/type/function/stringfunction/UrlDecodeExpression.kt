package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.StringType

class UrlDecodeExpression(encodedString: TypeExpression<StringType>) :
    FunctionExpression<StringType>("URL_DECODE", encodedString)

fun urlDecode(encodedString: TypeExpression<StringType>) = UrlDecodeExpression(encodedString)

fun urlDecode(encodedString: String) = urlDecode(encodedString.toDopeType())
