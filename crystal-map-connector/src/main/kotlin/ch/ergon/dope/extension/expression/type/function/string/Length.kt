package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.function.string.length
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun length(inStr: CMJsonField<String>) = length(inStr.toDopeType())
