package ch.ergon.dope.extension.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.date.ExtractionComponent
import ch.ergon.dope.resolvable.expression.type.function.date.extractDateComponent
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

@JvmName("millisPartCMNumberDateComponent")
fun CMJsonField<Number>.extractDateComponent(component: ExtractionComponent, timeZone: TypeExpression<StringType>? = null) =
    toDopeType().extractDateComponent(component, timeZone)

fun CMJsonField<Number>.extractDateComponent(component: ExtractionComponent, timeZone: CMJsonField<String>) =
    toDopeType().extractDateComponent(component, timeZone.toDopeType())

@JvmName("millisPartTypeCMNumberDateComponent")
fun TypeExpression<NumberType>.extractDateComponent(component: ExtractionComponent, timeZone: CMJsonField<String>) =
    extractDateComponent(component, timeZone.toDopeType())

fun CMJsonField<Number>.extractDateComponent(component: ExtractionComponent, timeZone: String) =
    toDopeType().extractDateComponent(component, timeZone.toDopeType())

fun Number.extractDateComponent(component: ExtractionComponent, timeZone: CMJsonField<String>) =
    toDopeType().extractDateComponent(component, timeZone.toDopeType())

@JvmName("strPartCMStringDateComponent")
fun CMJsonField<String>.extractDateComponent(component: ExtractionComponent) =
    toDopeType().extractDateComponent(component)
