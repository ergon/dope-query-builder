package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.StringType

class UrlEncodeExpression(string: TypeExpression<StringType>) :
    FunctionExpression<StringType>("URL_ENCODE", string)

fun urlEncode(string: TypeExpression<StringType>) = UrlEncodeExpression(string)

fun urlEncode(string: String) = urlEncode(string.toDopeType())
