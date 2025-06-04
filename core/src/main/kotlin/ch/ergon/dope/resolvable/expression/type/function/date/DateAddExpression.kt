package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class DateAddMillisExpression(date: TypeExpression<NumberType>, increment: TypeExpression<NumberType>, component: DateUnit) :
    FunctionExpression<NumberType>("DATE_ADD_MILLIS", date, increment, component)

class DateAddStrExpression(date: TypeExpression<StringType>, increment: TypeExpression<NumberType>, component: DateUnit) :
    FunctionExpression<StringType>("DATE_ADD_STR", date, increment, component)

@JvmName("millisPlusTypeDateComponent")
fun TypeExpression<NumberType>.plusDateComponent(
    increment: TypeExpression<NumberType>,
    component: DateUnit,
) = DateAddMillisExpression(this, increment, component)

@JvmName("millisPlusNumberDateComponent")
fun TypeExpression<NumberType>.plusDateComponent(
    increment: Number,
    component: DateUnit,
) = plusDateComponent(increment.toDopeType(), component)

fun Number.plusDateComponent(
    increment: TypeExpression<NumberType>,
    component: DateUnit,
) = toDopeType().plusDateComponent(increment, component)

fun Number.plusDateComponent(
    increment: Number,
    component: DateUnit,
) = toDopeType().plusDateComponent(increment.toDopeType(), component)

@JvmName("strPlusTypeDateComponent")
fun TypeExpression<StringType>.plusDateComponent(
    increment: TypeExpression<NumberType>,
    component: DateUnit,
) = DateAddStrExpression(this, increment, component)

@JvmName("strPlusNumberDateComponent")
fun TypeExpression<StringType>.plusDateComponent(
    increment: Number,
    component: DateUnit,
) = plusDateComponent(increment.toDopeType(), component)

fun String.plusDateComponent(
    increment: TypeExpression<NumberType>,
    component: DateUnit,
) = toDopeType().plusDateComponent(increment, component)

fun String.plusDateComponent(
    increment: Number,
    component: DateUnit,
) = toDopeType().plusDateComponent(increment.toDopeType(), component)
