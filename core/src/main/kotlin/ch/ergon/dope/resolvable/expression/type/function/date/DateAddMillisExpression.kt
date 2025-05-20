package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class DateAddMillisExpression(date: TypeExpression<NumberType>, increment: TypeExpression<NumberType>, component: DateComponent) :
    FunctionExpression<NumberType>("DATE_ADD_MILLIS", date, increment, component)

@JvmName("millisPlusTypeDateComponent")
fun TypeExpression<NumberType>.plusDateComponent(
    increment: TypeExpression<NumberType>,
    component: DateComponent,
) = DateAddMillisExpression(this, increment, component)

@JvmName("millisPlusNumberDateComponent")
fun TypeExpression<NumberType>.plusDateComponent(
    increment: Number,
    component: DateComponent,
) = plusDateComponent(increment.toDopeType(), component)

fun Number.plusDateComponent(
    increment: TypeExpression<NumberType>,
    component: DateComponent,
) = toDopeType().plusDateComponent(increment, component)

fun Number.plusDateComponent(
    increment: Number,
    component: DateComponent,
) = toDopeType().plusDateComponent(increment.toDopeType(), component)
