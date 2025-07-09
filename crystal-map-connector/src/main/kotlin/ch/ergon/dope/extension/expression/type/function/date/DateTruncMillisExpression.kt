package ch.ergon.dope.extension.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.function.date.DateUnit
import ch.ergon.dope.resolvable.expression.type.function.date.truncateTo
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

@JvmName("millisTruncateToCMNumber")
fun CMJsonField<Number>.truncateTo(dateUnit: DateUnit) =
    toDopeType().truncateTo(dateUnit)

@JvmName("strTruncateToCMString")
fun CMJsonField<String>.truncateTo(dateUnit: DateUnit) =
    toDopeType().truncateTo(dateUnit)
