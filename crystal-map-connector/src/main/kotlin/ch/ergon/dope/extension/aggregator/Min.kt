package ch.ergon.dope.extension.aggregator

import ch.ergon.dope.asArrayField
import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.MinExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.min
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("minNumber")
fun min(field: CMField<out Number>, quantifier: AggregateQuantifier = ALL): MinExpression = min(field.asField(), quantifier)

@JvmName("minString")
fun min(field: CMField<String>, quantifier: AggregateQuantifier = ALL): MinExpression = min(field.asField(), quantifier)

@JvmName("minBoolean")
fun min(field: CMField<Boolean>, quantifier: AggregateQuantifier = ALL): MinExpression = min(field.asField(), quantifier)

@JvmName("minNumber")
fun min(field: CMList<out Number>, quantifier: AggregateQuantifier = ALL): MinExpression = min(field.asArrayField(), quantifier)

@JvmName("minString")
fun min(field: CMList<String>, quantifier: AggregateQuantifier = ALL): MinExpression = min(field.asArrayField(), quantifier)

@JvmName("minBoolean")
fun min(field: CMList<Boolean>, quantifier: AggregateQuantifier = ALL): MinExpression = min(field.asArrayField(), quantifier)
