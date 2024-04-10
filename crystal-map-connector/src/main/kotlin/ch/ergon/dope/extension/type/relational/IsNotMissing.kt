package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isNotMissing
import com.schwarz.crystalapi.schema.CMField

@JvmName("isNotMissingNumber")
fun CMField<out Number>.isNotMissing() = asField().isNotMissing()

@JvmName("isNotMissingString")
fun CMField<String>.isNotMissing() = asField().isNotMissing()

@JvmName("isNotMissingBoolean")
fun CMField<Boolean>.isNotMissing() = asField().isNotMissing()
