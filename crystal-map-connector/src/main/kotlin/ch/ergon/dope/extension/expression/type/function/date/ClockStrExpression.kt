package ch.ergon.dope.extension.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.function.date.clockString
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun clockString(format: CMJsonField<String>) = clockString(format.toDopeType())
