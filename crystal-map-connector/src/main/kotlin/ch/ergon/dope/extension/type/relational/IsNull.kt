package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNull
import ch.ergon.dope.toDopeField
import com.schwarz.crystalapi.schema.CMField

@JvmName("isNullNumber")
fun CMField<out Number>.isNull() = toDopeField().isNull()

@JvmName("isNullString")
fun CMField<String>.isNull() = toDopeField().isNull()

@JvmName("isNullBoolean")
fun CMField<Boolean>.isNull() = toDopeField().isNull()
