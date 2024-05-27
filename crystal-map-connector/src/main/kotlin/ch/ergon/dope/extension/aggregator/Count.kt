package ch.ergon.dope.extension.aggregator

import ch.ergon.dope.asArrayField
import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.CountExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.count
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("countNumber")
fun count(field: CMField<out Number>, quantifier: AggregateQuantifier = ALL): CountExpression = count(field.asField(), quantifier)

@JvmName("countString")
fun count(field: CMField<String>, quantifier: AggregateQuantifier = ALL): CountExpression = count(field.asField(), quantifier)

@JvmName("countBoolean")
fun count(field: CMField<Boolean>, quantifier: AggregateQuantifier = ALL): CountExpression = count(field.asField(), quantifier)

@JvmName("countNumber")
fun count(field: CMList<out Number>, quantifier: AggregateQuantifier = ALL): CountExpression = count(field.asArrayField(), quantifier)

@JvmName("countString")
fun count(field: CMList<String>, quantifier: AggregateQuantifier = ALL): CountExpression = count(field.asArrayField(), quantifier)

@JvmName("countBoolean")
fun count(field: CMList<Boolean>, quantifier: AggregateQuantifier = ALL): CountExpression = count(field.asArrayField(), quantifier)
