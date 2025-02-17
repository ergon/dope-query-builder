package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

class UpperExpression(inStr: TypeExpression<StringType>) : FunctionExpression<StringType>(
    "UPPER",
    inStr,
)

fun upper(inStr: TypeExpression<StringType>) = UpperExpression(inStr)

fun upper(inStr: String) = upper(inStr.toDopeType())
