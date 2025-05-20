package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class DateDiffStrExpression(date: TypeExpression<StringType>, other: TypeExpression<StringType>, component: DateComponent) :
    FunctionExpression<NumberType>("DATE_DIFF_STR", date, other, component)

@JvmName("strDiffTypeDateComponent")
fun TypeExpression<StringType>.differenceIn(
    other: TypeExpression<StringType>,
    component: DateComponent,
) = DateDiffStrExpression(this, other, component)

@JvmName("strDiffNumberDateComponent")
fun TypeExpression<StringType>.differenceIn(
    other: String,
    component: DateComponent,
) = differenceIn(other.toDopeType(), component)

fun String.differenceIn(
    other: TypeExpression<StringType>,
    component: DateComponent,
) = toDopeType().differenceIn(other, component)

fun String.differenceIn(
    other: String,
    component: DateComponent,
) = toDopeType().differenceIn(other.toDopeType(), component)
