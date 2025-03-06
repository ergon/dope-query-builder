package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.function.string.lower
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun lower(inStr: CMJsonField<String>) = lower(inStr.toDopeType())
