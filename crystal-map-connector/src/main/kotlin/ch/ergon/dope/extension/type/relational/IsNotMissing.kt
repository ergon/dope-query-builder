package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNotMissing
import ch.ergon.dope.toDopeField
import com.schwarz.crystalapi.schema.CMField

@JvmName("isNotMissingNumber")
fun CMField<out Number>.isNotMissing() = toDopeField().isNotMissing()

@JvmName("isNotMissingString")
fun CMField<String>.isNotMissing() = toDopeField().isNotMissing()

@JvmName("isNotMissingBoolean")
fun CMField<Boolean>.isNotMissing() = toDopeField().isNotMissing()
