package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class DateDiffMillisExpression(date: TypeExpression<NumberType>, other: TypeExpression<NumberType>, component: DateUnit) :
    FunctionExpression<NumberType>("DATE_DIFF_MILLIS", date, other, component)

class DateDiffStrExpression(date: TypeExpression<StringType>, other: TypeExpression<StringType>, component: DateUnit) :
    FunctionExpression<NumberType>("DATE_DIFF_STR", date, other, component)

@JvmName("millisDiffTypeDateComponent")
fun TypeExpression<NumberType>.differenceIn(
    other: TypeExpression<NumberType>,
    component: DateUnit,
) = DateDiffMillisExpression(this, other, component)

@JvmName("millisDiffNumberDateComponent")
fun TypeExpression<NumberType>.differenceIn(
    other: Number,
    component: DateUnit,
) = differenceIn(other.toDopeType(), component)

fun Number.differenceIn(
    other: TypeExpression<NumberType>,
    component: DateUnit,
) = toDopeType().differenceIn(other, component)

fun Number.differenceIn(
    other: Number,
    component: DateUnit,
) = toDopeType().differenceIn(other.toDopeType(), component)

@JvmName("strDiffTypeDateComponent")
fun TypeExpression<StringType>.differenceIn(
    other: TypeExpression<StringType>,
    component: DateUnit,
) = DateDiffStrExpression(this, other, component)

@JvmName("strDiffNumberDateComponent")
fun TypeExpression<StringType>.differenceIn(
    other: String,
    component: DateUnit,
) = differenceIn(other.toDopeType(), component)

fun String.differenceIn(
    other: TypeExpression<StringType>,
    component: DateUnit,
) = toDopeType().differenceIn(other, component)

fun String.differenceIn(
    other: String,
    component: DateUnit,
) = toDopeType().differenceIn(other.toDopeType(), component)
