package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class DatePartMillisExpression(date: TypeExpression<NumberType>, component: DateComponent, timeZone: TypeExpression<StringType>? = null) :
    FunctionExpression<NumberType>("DATE_PART_MILLIS", date, component, timeZone)

fun TypeExpression<NumberType>.extractDateComponent(component: DateComponent, timeZone: TypeExpression<StringType>? = null) =
    DatePartMillisExpression(this, component, timeZone)

fun TypeExpression<NumberType>.extractDateComponent(component: DateComponent, timeZone: String) =
    extractDateComponent(component, timeZone.toDopeType())

fun Number.extractDateComponent(component: DateComponent, timeZone: TypeExpression<StringType>? = null) =
    toDopeType().extractDateComponent(component, timeZone)

fun Number.extractDateComponent(component: DateComponent, timeZone: String) =
    toDopeType().extractDateComponent(component, timeZone.toDopeType())
