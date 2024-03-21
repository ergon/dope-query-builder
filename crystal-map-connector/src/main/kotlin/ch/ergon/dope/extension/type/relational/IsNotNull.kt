package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNotNull
import com.schwarz.crystalapi.schema.CMField

@JvmName("isNotNullNumber")
fun CMField<Number>.isNotNull() = asField().isNotNull()

@JvmName("isNotNullString")
fun CMField<String>.isNotNull() = asField().isNotNull()

@JvmName("isNotNullBoolean")
fun CMField<Boolean>.isNotNull() = asField().isNotNull()
