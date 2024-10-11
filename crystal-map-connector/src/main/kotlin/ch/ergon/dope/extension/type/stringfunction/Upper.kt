package ch.ergon.dope.extension.type.stringfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.upper
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun upper(inStr: CMJsonField<String>) = upper(inStr.toDopeType())
