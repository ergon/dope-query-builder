package ch.ergon.dope.extension.type.stringfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.length
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun length(inStr: CMJsonField<String>) = length(inStr.toDopeType())
