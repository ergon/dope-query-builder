package ch.ergon.dope.extension.expression.single.type.relational

import ch.ergon.dope.resolvable.expression.single.type.relational.isMissing
import ch.ergon.dope.resolvable.expression.single.type.relational.isNotMissing
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.Schema

@JvmName("isMissingNumber")
fun CMJsonField<out Number>.isMissing() = toDopeType().isMissing()

@JvmName("isMissingString")
fun CMJsonField<String>.isMissing() = toDopeType().isMissing()

@JvmName("isMissingBoolean")
fun CMJsonField<Boolean>.isMissing() = toDopeType().isMissing()

@JvmName("isMissingObject")
fun CMObjectField<Schema>.isMissing() = toDopeType().isMissing()

@JvmName("isNotMissingNumber")
fun CMJsonField<out Number>.isNotMissing() = toDopeType().isNotMissing()

@JvmName("isNotMissingString")
fun CMJsonField<String>.isNotMissing() = toDopeType().isNotMissing()

@JvmName("isNotMissingBoolean")
fun CMJsonField<Boolean>.isNotMissing() = toDopeType().isNotMissing()

@JvmName("isNotMissingObject")
fun CMObjectField<Schema>.isNotMissing() = toDopeType().isNotMissing()
