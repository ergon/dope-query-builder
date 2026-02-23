package ch.ergon.dope.resolvable.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.validtype.NumberType

class EulerExpression : FunctionExpression<NumberType>(emptyList())

fun e() = EulerExpression()
