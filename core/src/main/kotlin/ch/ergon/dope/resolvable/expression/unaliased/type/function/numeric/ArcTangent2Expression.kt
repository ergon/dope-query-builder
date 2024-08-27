package ch.ergon.dope.resolvable.expression.unaliased.type.function.numeric

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class ArcTangent2Expression(divisor: TypeExpression<NumberType>, dividend: TypeExpression<NumberType>) :
    NumberFunctionExpression("ATAN2", divisor, dividend)

fun atan2(divisor: TypeExpression<NumberType>, dividend: TypeExpression<NumberType>) =
    ArcTangent2Expression(divisor, dividend)

fun atan2(divisor: TypeExpression<NumberType>, dividend: Number) = atan2(divisor, dividend.toDopeType())

fun atan2(divisor: Number, dividend: TypeExpression<NumberType>) = atan2(divisor.toDopeType(), dividend)

fun atan2(divisor: Number, dividend: Number) = atan2(divisor.toDopeType(), dividend.toDopeType())
