package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

data class DatePartMillisExpression(
    val date: TypeExpression<NumberType>,
    val component: DateComponent,
    val timeZone: TypeExpression<StringType>? = null,
) : FunctionExpression<NumberType>(listOf(date, component, timeZone))

data class DatePartStrExpression(val date: TypeExpression<StringType>, val component: DateComponent) :
    FunctionExpression<NumberType>(listOf(date, component))

fun TypeExpression<NumberType>.extractDateComponent(component: DateComponent, timeZone: TypeExpression<StringType>? = null) =
    DatePartMillisExpression(this, component, timeZone)

fun TypeExpression<NumberType>.extractDateComponent(component: DateComponent, timeZone: String) =
    extractDateComponent(component, timeZone.toDopeType())

fun Number.extractDateComponent(component: DateComponent, timeZone: TypeExpression<StringType>? = null) =
    toDopeType().extractDateComponent(component, timeZone)

fun Number.extractDateComponent(component: DateComponent, timeZone: String) =
    toDopeType().extractDateComponent(component, timeZone.toDopeType())

fun TypeExpression<StringType>.extractDateComponent(component: DateComponent) =
    DatePartStrExpression(this, component)

fun String.extractDateComponent(component: DateComponent) =
    toDopeType().extractDateComponent(component)
