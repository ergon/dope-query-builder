package ch.ergon.dope.extension.type.stringfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.suffixes
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun suffixes(inStr: CMJsonField<String>) = suffixes(inStr.toDopeType())
