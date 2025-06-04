package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class DatePartMillisExpression(
    date: TypeExpression<NumberType>,
    component: ExtractionComponent,
    timeZone: TypeExpression<StringType>? = null,
) :
    FunctionExpression<NumberType>("DATE_PART_MILLIS", date, component, timeZone)

class DatePartStrExpression(date: TypeExpression<StringType>, component: ExtractionComponent) :
    FunctionExpression<NumberType>("DATE_PART_STR", date, component)

fun TypeExpression<NumberType>.extractDateComponent(component: ExtractionComponent, timeZone: TypeExpression<StringType>? = null) =
    DatePartMillisExpression(this, component, timeZone)

fun TypeExpression<NumberType>.extractDateComponent(component: ExtractionComponent, timeZone: String) =
    extractDateComponent(component, timeZone.toDopeType())

fun Number.extractDateComponent(component: ExtractionComponent, timeZone: TypeExpression<StringType>? = null) =
    toDopeType().extractDateComponent(component, timeZone)

fun Number.extractDateComponent(component: ExtractionComponent, timeZone: String) =
    toDopeType().extractDateComponent(component, timeZone.toDopeType())

fun TypeExpression<StringType>.extractDateComponent(component: ExtractionComponent) =
    DatePartStrExpression(this, component)

fun String.extractDateComponent(component: ExtractionComponent) =
    toDopeType().extractDateComponent(component)
