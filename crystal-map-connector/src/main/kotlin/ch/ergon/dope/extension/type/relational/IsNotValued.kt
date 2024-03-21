package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNotValued
import com.schwarz.crystalapi.schema.CMField

@JvmName("isNotValuedNumber")
fun CMField<Number>.isNotValued() = asField().isNotValued()

@JvmName("isNotValuedString")
fun CMField<String>.isNotValued() = asField().isNotValued()

@JvmName("isNotValuedBoolean")
fun CMField<Boolean>.isNotValued() = asField().isNotValued()
