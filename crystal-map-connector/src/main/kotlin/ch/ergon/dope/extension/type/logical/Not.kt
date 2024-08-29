package ch.ergon.dope.extension.type.logical

import ch.ergon.dope.resolvable.expression.unaliased.type.logical.not
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun not(boolean: CMJsonField<Boolean>) = not(boolean.toDopeType())
