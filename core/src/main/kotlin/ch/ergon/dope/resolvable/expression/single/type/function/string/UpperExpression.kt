package ch.ergon.dope.resolvable.expression.single.type.function.string

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.StringType

class UpperExpression(inStr: TypeExpression<StringType>) : FunctionExpression<StringType>(
    "UPPER",
    inStr,
)

fun upper(inStr: TypeExpression<StringType>) = UpperExpression(inStr)

fun upper(inStr: String) = upper(inStr.toDopeType())
