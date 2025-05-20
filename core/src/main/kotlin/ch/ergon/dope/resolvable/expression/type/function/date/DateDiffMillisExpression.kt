package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class DateDiffMillisExpression(date: TypeExpression<NumberType>, other: TypeExpression<NumberType>, component: DateComponent) :
    FunctionExpression<NumberType>("DATE_DIFF_MILLIS", date, other, component)

@JvmName("millisDiffTypeDateComponent")
fun TypeExpression<NumberType>.differenceIn(
    other: TypeExpression<NumberType>,
    component: DateComponent,
) = DateDiffMillisExpression(this, other, component)

@JvmName("millisDiffNumberDateComponent")
fun TypeExpression<NumberType>.differenceIn(
    other: Number,
    component: DateComponent,
) = differenceIn(other.toDopeType(), component)

fun Number.differenceIn(
    other: TypeExpression<NumberType>,
    component: DateComponent,
) = toDopeType().differenceIn(other, component)

fun Number.differenceIn(
    other: Number,
    component: DateComponent,
) = toDopeType().differenceIn(other.toDopeType(), component)
