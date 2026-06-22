package ch.ergon.dope.extension.expression.type.function.date

import ch.ergon.dope.couchbase.resolvable.expression.type.function.date.toDurationNanos
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

fun CMJsonField<String>.toDurationMillis() = toDopeType().toDurationNanos()
