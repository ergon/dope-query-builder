package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNotValued
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMField

@JvmName("isNotValuedNumber")
fun CMField<out Number>.isNotValued() = toDopeType().isNotValued()

@JvmName("isNotValuedString")
fun CMField<String>.isNotValued() = toDopeType().isNotValued()

@JvmName("isNotValuedBoolean")
fun CMField<Boolean>.isNotValued() = toDopeType().isNotValued()
