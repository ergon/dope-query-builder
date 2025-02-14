package ch.ergon.dope.extension.expression.single.type.function.string

import ch.ergon.dope.resolvable.expression.single.type.function.string.urlEncode
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun urlEncode(string: CMJsonField<String>) = urlEncode(string.toDopeType())
