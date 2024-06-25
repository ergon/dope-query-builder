package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNotValued
import ch.ergon.dope.toDopeField
import com.schwarz.crystalapi.schema.CMField

@JvmName("isNotValuedNumber")
fun CMField<out Number>.isNotValued() = toDopeField().isNotValued()

@JvmName("isNotValuedString")
fun CMField<String>.isNotValued() = toDopeField().isNotValued()

@JvmName("isNotValuedBoolean")
fun CMField<Boolean>.isNotValued() = toDopeField().isNotValued()
