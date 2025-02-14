package ch.ergon.dope.extension.expression.single.type.function.string

import ch.ergon.dope.resolvable.expression.single.type.function.string.length
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun length(inStr: CMJsonField<String>) = length(inStr.toDopeType())
