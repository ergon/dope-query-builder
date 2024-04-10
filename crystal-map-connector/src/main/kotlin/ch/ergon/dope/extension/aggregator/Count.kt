package ch.ergon.dope.extension.aggregator

import ch.ergon.dope.asArrayField
import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.CountExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.count
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("countNumber")
fun count(field: CMField<out Number>): CountExpression = count(field.asField())

@JvmName("countString")
fun count(field: CMField<String>): CountExpression = count(field.asField())

@JvmName("countBoolean")
fun count(field: CMField<Boolean>): CountExpression = count(field.asField())

@JvmName("countNumber")
fun count(field: CMList<out Number>): CountExpression = count(field.asArrayField())

@JvmName("countString")
fun count(field: CMList<String>): CountExpression = count(field.asArrayField())

@JvmName("countBoolean")
fun count(field: CMList<Boolean>): CountExpression = count(field.asArrayField())
