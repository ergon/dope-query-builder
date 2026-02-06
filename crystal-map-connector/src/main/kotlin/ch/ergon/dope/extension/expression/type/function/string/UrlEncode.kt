package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.function.string.urlEncode
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun CMJsonField<String>.urlEncode() = toDopeType().urlEncode()

fun String.urlEncode() = toDopeType().urlEncode()
