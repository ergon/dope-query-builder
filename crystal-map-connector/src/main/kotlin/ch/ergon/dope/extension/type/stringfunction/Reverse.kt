package ch.ergon.dope.extension.type.stringfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.reverse
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun reverse(inStr: CMJsonField<String>) = reverse(inStr.toDopeType())
