package ch.ergon.dope.extension.expression.type.logical

import ch.ergon.dope.resolvable.expression.type.logic.not
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun not(boolean: CMJsonField<Boolean>) = not(boolean.toDopeType())
