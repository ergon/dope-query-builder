package ch.ergon.dope.extension.type.stringfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.urlEncode
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun urlEncode(string: CMJsonField<String>) = urlEncode(string.toDopeType())
