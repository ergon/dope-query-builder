package ch.ergon.dope.extension.type.stringfunction

import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.initCap
import ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction.title
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun initCap(inStr: CMJsonField<String>) = initCap(inStr.toDopeType())

fun title(inStr: CMJsonField<String>) = title(inStr.toDopeType())
