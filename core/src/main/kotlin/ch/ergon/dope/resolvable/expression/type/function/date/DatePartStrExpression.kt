package ch.ergon.dope.resolvable.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class DatePartStrExpression(date: TypeExpression<StringType>, component: DateComponent) :
    FunctionExpression<NumberType>("DATE_PART_STR", date, component)

fun TypeExpression<StringType>.extractDateComponent(component: DateComponent) =
    DatePartStrExpression(this, component)

fun String.extractDateComponent(component: DateComponent) =
    toDopeType().extractDateComponent(component)
