package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isMissing
import ch.ergon.dope.toDopeField
import com.schwarz.crystalapi.schema.CMField

@JvmName("isMissingNumber")
fun CMField<out Number>.isMissing() = toDopeField().isMissing()

@JvmName("isMissingString")
fun CMField<String>.isMissing() = toDopeField().isMissing()

@JvmName("isMissingBoolean")
fun CMField<Boolean>.isMissing() = toDopeField().isMissing()
