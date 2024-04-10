package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNull
import com.schwarz.crystalapi.schema.CMField

@JvmName("isNullNumber")
fun CMField<out Number>.isNull() = asField().isNull()

@JvmName("isNullString")
fun CMField<String>.isNull() = asField().isNull()

@JvmName("isNullBoolean")
fun CMField<Boolean>.isNull() = asField().isNull()
