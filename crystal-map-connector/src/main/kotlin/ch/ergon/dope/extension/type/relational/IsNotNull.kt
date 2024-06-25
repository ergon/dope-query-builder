package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNotNull
import ch.ergon.dope.toDopeField
import com.schwarz.crystalapi.schema.CMField

@JvmName("isNotNullNumber")
fun CMField<out Number>.isNotNull() = toDopeField().isNotNull()

@JvmName("isNotNullString")
fun CMField<String>.isNotNull() = toDopeField().isNotNull()

@JvmName("isNotNullBoolean")
fun CMField<Boolean>.isNotNull() = toDopeField().isNotNull()
