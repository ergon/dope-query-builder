package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.function.string.initCap
import ch.ergon.dope.resolvable.expression.type.function.string.title
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun CMJsonField<String>.initCap() = toDopeType().initCap()

fun CMJsonField<String>.title() = toDopeType().title()

fun String.initCap() = toDopeType().initCap()

fun String.title() = toDopeType().title()
