package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class DateAddStrExpression(date: TypeExpression<StringType>, increment: TypeExpression<NumberType>, component: DateComponent) :
    FunctionExpression<StringType>("DATE_ADD_STR", date, increment, component)

@JvmName("strPlusTypeDateComponent")
fun TypeExpression<StringType>.plusDateComponent(
    increment: TypeExpression<NumberType>,
    component: DateComponent,
) = DateAddStrExpression(this, increment, component)

@JvmName("strPlusNumberDateComponent")
fun TypeExpression<StringType>.plusDateComponent(
    increment: Number,
    component: DateComponent,
) = plusDateComponent(increment.toDopeType(), component)

fun String.plusDateComponent(
    increment: TypeExpression<NumberType>,
    component: DateComponent,
) = toDopeType().plusDateComponent(increment, component)

fun String.plusDateComponent(
    increment: Number,
    component: DateComponent,
) = toDopeType().plusDateComponent(increment.toDopeType(), component)
