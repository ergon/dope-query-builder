package ch.ergon.dope.extension.aggregator

import ch.ergon.dope.asArrayField
import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.MinExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.min
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("minNumber")
fun min(field: CMField<Number>): MinExpression = min(field.asField())

@JvmName("minString")
fun min(field: CMField<String>): MinExpression = min(field.asField())

@JvmName("minBoolean")
fun min(field: CMField<Boolean>): MinExpression = min(field.asField())

@JvmName("minNumber")
fun min(field: CMList<Number>): MinExpression = min(field.asArrayField())

@JvmName("minString")
fun min(field: CMList<String>): MinExpression = min(field.asArrayField())

@JvmName("minBoolean")
fun min(field: CMList<Boolean>): MinExpression = min(field.asArrayField())
