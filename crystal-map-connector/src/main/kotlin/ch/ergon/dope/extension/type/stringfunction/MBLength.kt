package ch.ergon.dope.extension.type.stringfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.mbLength
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun mbLength(inStr: CMJsonField<String>) = mbLength(inStr.toDopeType())
