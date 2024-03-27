package ch.ergon.dope.extension.type.relational

import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isMissing
import com.schwarz.crystalapi.schema.CMField

@JvmName("isMissingNumber")
fun CMField<out Number>.isMissing() = asField().isMissing()

@JvmName("isMissingString")
fun CMField<String>.isMissing() = asField().isMissing()

@JvmName("isMissingBoolean")
fun CMField<Boolean>.isMissing() = asField().isMissing()
