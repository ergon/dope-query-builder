package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isValued
import com.schwarz.crystalapi.schema.CMField

@JvmName("isValuedNumber")
fun CMField<out Number>.isValued() = asField().isValued()

@JvmName("isValuedString")
fun CMField<String>.isValued() = asField().isValued()

@JvmName("isValuedBoolean")
fun CMField<Boolean>.isValued() = asField().isValued()
