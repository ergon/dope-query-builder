package ch.ergon.dope.extension.expression.type.function.date

import ch.ergon.dope.couchbase.resolvable.expression.type.function.date.toDurationString
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

@JvmName("durationToStrCMNumber")
fun CMJsonField<Number>.toDurationString() = toDopeType().toDurationString()
