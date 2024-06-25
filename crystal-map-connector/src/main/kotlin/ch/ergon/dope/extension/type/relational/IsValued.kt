package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isValued
import ch.ergon.dope.toDopeField
import com.schwarz.crystalapi.schema.CMField

@JvmName("isValuedNumber")
fun CMField<out Number>.isValued() = toDopeField().isValued()

@JvmName("isValuedString")
fun CMField<String>.isValued() = toDopeField().isValued()

@JvmName("isValuedBoolean")
fun CMField<Boolean>.isValued() = toDopeField().isValued()
