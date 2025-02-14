package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.function.string.urlDecode
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun urlDecode(encodedString: CMJsonField<String>) = urlDecode(encodedString.toDopeType())
