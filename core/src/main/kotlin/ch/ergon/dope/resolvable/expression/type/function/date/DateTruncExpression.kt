package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class DateTruncMillisExpression(date: TypeExpression<NumberType>, component: TruncationComponent) :
    FunctionExpression<NumberType>("DATE_TRUNC_MILLIS", date, component)

class DateTruncStrExpression(date: TypeExpression<StringType>, component: TruncationComponent) :
    FunctionExpression<StringType>("DATE_TRUNC_STR", date, component)

@JvmName("millisTruncTypeDateComponent")
fun TypeExpression<NumberType>.truncateTo(component: TruncationComponent) =
    DateTruncMillisExpression(this, component)

fun Number.truncateTo(component: TruncationComponent) =
    toDopeType().truncateTo(component)

@JvmName("strTruncTypeDateComponent")
fun TypeExpression<StringType>.truncateTo(component: TruncationComponent) =
    DateTruncStrExpression(this, component)

fun String.truncateTo(component: TruncationComponent) =
    toDopeType().truncateTo(component)
