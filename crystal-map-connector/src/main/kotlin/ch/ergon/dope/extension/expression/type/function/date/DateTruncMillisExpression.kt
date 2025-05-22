package ch.ergon.dope.extension.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.function.date.DateComponent
import ch.ergon.dope.resolvable.expression.type.function.date.truncateTo
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

@JvmName("millisTruncateToCMNumber")
fun CMJsonField<Number>.truncateTo(component: DateComponent) =
    toDopeType().truncateTo(component)

@JvmName("strTruncateToCMString")
fun CMJsonField<String>.truncateTo(component: DateComponent) =
    toDopeType().truncateTo(component)
