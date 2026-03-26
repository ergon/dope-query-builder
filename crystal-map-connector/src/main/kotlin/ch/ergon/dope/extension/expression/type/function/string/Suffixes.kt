package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.function.string.suffixes
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun CMJsonField<String>.suffixes() = toDopeType().suffixes()
