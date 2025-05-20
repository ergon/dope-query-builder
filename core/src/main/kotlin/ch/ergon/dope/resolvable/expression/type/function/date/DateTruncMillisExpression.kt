package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType

class DateTruncMillisExpression(date: TypeExpression<NumberType>, component: DateComponent) : FunctionExpression<NumberType>(
    "DATE_TRUNC_MILLIS",
    date,
    component,
)

@JvmName("millisTruncTypeDateComponent")
fun TypeExpression<NumberType>.truncateTo(component: DateComponent) =
    DateTruncMillisExpression(this, component)

fun Number.truncateTo(component: DateComponent) =
    toDopeType().truncateTo(component)
