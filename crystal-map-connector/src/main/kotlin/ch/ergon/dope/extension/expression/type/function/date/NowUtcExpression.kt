package ch.ergon.dope.extension.expression.type.function.date

import ch.ergon.dope.resolvable.expression.type.function.date.utcNowString
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun utcNowString(format: CMJsonField<String>) = utcNowString(format.toDopeType())
