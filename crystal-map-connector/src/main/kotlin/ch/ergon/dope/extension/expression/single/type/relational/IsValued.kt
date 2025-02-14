package ch.ergon.dope.extension.expression.single.type.relational

import ch.ergon.dope.resolvable.expression.single.type.relational.isNotValued
import ch.ergon.dope.resolvable.expression.single.type.relational.isValued
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.Schema

@JvmName("isValuedNumber")
fun CMJsonField<out Number>.isValued() = toDopeType().isValued()

@JvmName("isValuedString")
fun CMJsonField<String>.isValued() = toDopeType().isValued()

@JvmName("isValuedBoolean")
fun CMJsonField<Boolean>.isValued() = toDopeType().isValued()

@JvmName("isValuedObject")
fun CMObjectField<Schema>.isValued() = toDopeType().isValued()

@JvmName("isNotValuedNumber")
fun CMJsonField<out Number>.isNotValued() = toDopeType().isNotValued()

@JvmName("isNotValuedString")
fun CMJsonField<String>.isNotValued() = toDopeType().isNotValued()

@JvmName("isNotValuedBoolean")
fun CMJsonField<Boolean>.isNotValued() = toDopeType().isNotValued()

@JvmName("isNotValuedObject")
fun CMObjectField<Schema>.isNotValued() = toDopeType().isNotValued()
