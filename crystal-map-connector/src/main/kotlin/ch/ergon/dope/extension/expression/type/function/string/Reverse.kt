package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.function.string.reverse
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun reverse(inStr: CMJsonField<String>) = reverse(inStr.toDopeType())
