package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNull
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField

@JvmName("isNullNumber")
fun CMField<out Number>.isNull() = toDopeType().isNull()

@JvmName("isNullString")
fun CMField<String>.isNull() = toDopeType().isNull()

@JvmName("isNullBoolean")
fun CMField<Boolean>.isNull() = toDopeType().isNull()
