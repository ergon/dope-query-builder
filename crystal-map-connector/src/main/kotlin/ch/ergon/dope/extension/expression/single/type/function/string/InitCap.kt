package ch.ergon.dope.extension.expression.single.type.function.string

import ch.ergon.dope.resolvable.expression.single.type.function.string.initCap
import ch.ergon.dope.resolvable.expression.single.type.function.string.title
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun initCap(inStr: CMJsonField<String>) = initCap(inStr.toDopeType())

fun title(inStr: CMJsonField<String>) = title(inStr.toDopeType())
