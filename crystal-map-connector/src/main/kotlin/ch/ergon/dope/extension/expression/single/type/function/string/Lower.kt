package ch.ergon.dope.extension.expression.single.type.function.string

import ch.ergon.dope.resolvable.expression.single.type.function.string.lower
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun lower(inStr: CMJsonField<String>) = lower(inStr.toDopeType())
