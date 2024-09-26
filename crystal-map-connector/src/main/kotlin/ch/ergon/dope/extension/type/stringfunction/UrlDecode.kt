package ch.ergon.dope.extension.type.stringfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.urlDecode
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun urlDecode(encodedString: CMJsonField<String>) = urlDecode(encodedString.toDopeType())
