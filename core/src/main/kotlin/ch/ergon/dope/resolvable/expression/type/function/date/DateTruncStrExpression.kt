package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

class DateTruncStrExpression(date: TypeExpression<StringType>, component: DateComponent) : FunctionExpression<StringType>(
    "DATE_TRUNC_STR",
    date,
    component,
)

@JvmName("strTruncTypeDateComponent")
fun TypeExpression<StringType>.truncateTo(component: DateComponent) =
    DateTruncStrExpression(this, component)

fun String.truncateTo(component: DateComponent) =
    toDopeType().truncateTo(component)
