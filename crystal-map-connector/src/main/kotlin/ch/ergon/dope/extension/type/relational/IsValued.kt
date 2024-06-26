package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isValued
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField

@JvmName("isValuedNumber")
fun CMField<out Number>.isValued() = toDopeType().isValued()

@JvmName("isValuedString")
fun CMField<String>.isValued() = toDopeType().isValued()

@JvmName("isValuedBoolean")
fun CMField<Boolean>.isValued() = toDopeType().isValued()
