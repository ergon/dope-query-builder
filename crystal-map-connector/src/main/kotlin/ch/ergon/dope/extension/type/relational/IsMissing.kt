package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isMissing
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField

@JvmName("isMissingNumber")
fun CMField<out Number>.isMissing() = toDopeType().isMissing()

@JvmName("isMissingString")
fun CMField<String>.isMissing() = toDopeType().isMissing()

@JvmName("isMissingBoolean")
fun CMField<Boolean>.isMissing() = toDopeType().isMissing()
