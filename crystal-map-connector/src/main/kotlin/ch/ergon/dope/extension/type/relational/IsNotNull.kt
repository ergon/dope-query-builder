package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNotNull
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField

@JvmName("isNotNullNumber")
fun CMField<out Number>.isNotNull() = toDopeType().isNotNull()

@JvmName("isNotNullString")
fun CMField<String>.isNotNull() = toDopeType().isNotNull()

@JvmName("isNotNullBoolean")
fun CMField<Boolean>.isNotNull() = toDopeType().isNotNull()
