package ch.ergon.dope.extension.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.date.DateComponent
import ch.ergon.dope.resolvable.expression.type.function.date.extractDateComponent
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

@JvmName("millisPartCMNumberDateComponent")
fun CMJsonField<Number>.extractDateComponent(component: DateComponent, timeZone: TypeExpression<StringType>? = null) =
    toDopeType().extractDateComponent(component, timeZone)

fun CMJsonField<Number>.extractDateComponent(component: DateComponent, timeZone: CMJsonField<String>) =
    toDopeType().extractDateComponent(component, timeZone.toDopeType())

@JvmName("millisPartTypeCMNumberDateComponent")
fun TypeExpression<NumberType>.extractDateComponent(component: DateComponent, timeZone: CMJsonField<String>) =
    extractDateComponent(component, timeZone.toDopeType())

fun CMJsonField<Number>.extractDateComponent(component: DateComponent, timeZone: String) =
    toDopeType().extractDateComponent(component, timeZone.toDopeType())

fun Number.extractDateComponent(component: DateComponent, timeZone: CMJsonField<String>) =
    toDopeType().extractDateComponent(component, timeZone.toDopeType())

@JvmName("strPartCMStringDateComponent")
fun CMJsonField<String>.extractDateComponent(component: DateComponent) =
    toDopeType().extractDateComponent(component)
