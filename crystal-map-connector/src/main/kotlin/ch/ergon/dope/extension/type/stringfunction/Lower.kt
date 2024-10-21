package ch.ergon.dope.extension.type.stringfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.lower
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun lower(inStr: CMJsonField<String>) = lower(inStr.toDopeType())
