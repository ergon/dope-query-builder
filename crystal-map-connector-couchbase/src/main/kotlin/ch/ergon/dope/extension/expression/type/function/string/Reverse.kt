package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.couchbase.resolvable.expression.type.function.string.reverse
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun CMJsonField<String>.reverse() = toDopeType().reverse()
