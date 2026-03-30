package ch.ergon.dope.resolvable.expression.type.function.numeric

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

data class ArcTangent2Expression(
    val divisor: TypeExpression<NumberType>,
    val dividend: TypeExpression<NumberType>,
) : FunctionExpression<NumberType>(listOf(divisor, dividend))

fun atan2(divisor: TypeExpression<NumberType>, dividend: TypeExpression<NumberType>) =
    ArcTangent2Expression(divisor, dividend)

fun atan2(divisor: TypeExpression<NumberType>, dividend: Number) = atan2(divisor, dividend.toDopeType())

fun atan2(divisor: Number, dividend: TypeExpression<NumberType>) = atan2(divisor.toDopeType(), dividend)

fun atan2(divisor: Number, dividend: Number) = atan2(divisor.toDopeType(), dividend.toDopeType())
