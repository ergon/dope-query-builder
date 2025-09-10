package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

data class LowerExpression(val inStr: TypeExpression<StringType>) : FunctionExpression<StringType>("LOWER", listOf(inStr))

fun lower(inStr: TypeExpression<StringType>) = LowerExpression(inStr)

fun lower(inStr: String) = lower(inStr.toDopeType())
