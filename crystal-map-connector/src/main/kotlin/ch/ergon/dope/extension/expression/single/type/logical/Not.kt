package ch.ergon.dope.extension.expression.single.type.logical

import ch.ergon.dope.resolvable.expression.single.type.logic.not
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun not(boolean: CMJsonField<Boolean>) = not(boolean.toDopeType())
