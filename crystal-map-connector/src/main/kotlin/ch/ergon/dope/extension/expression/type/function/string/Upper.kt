package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.function.string.upper
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun upper(inStr: CMJsonField<String>) = upper(inStr.toDopeType())
