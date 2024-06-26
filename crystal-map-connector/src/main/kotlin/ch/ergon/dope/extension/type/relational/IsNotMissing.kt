package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNotMissing
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField

@JvmName("isNotMissingNumber")
fun CMField<out Number>.isNotMissing() = toDopeType().isNotMissing()

@JvmName("isNotMissingString")
fun CMField<String>.isNotMissing() = toDopeType().isNotMissing()

@JvmName("isNotMissingBoolean")
fun CMField<Boolean>.isNotMissing() = toDopeType().isNotMissing()
