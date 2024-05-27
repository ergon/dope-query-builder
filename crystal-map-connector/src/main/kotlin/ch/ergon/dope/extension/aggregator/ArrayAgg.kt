package ch.ergon.dope.extension.aggregator

import ch.ergon.dope.asArrayField
import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.ArrayAggExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.arrayAgg
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("arrayAggNumber")
fun arrayAgg(field: CMField<out Number>, quantifier: AggregateQuantifier = ALL): ArrayAggExpression =
    arrayAgg(field.asField(), quantifier)

@JvmName("arrayAggString")
fun arrayAgg(field: CMField<String>, quantifier: AggregateQuantifier = ALL): ArrayAggExpression =
    arrayAgg(field.asField(), quantifier)

@JvmName("arrayAggBoolean")
fun arrayAgg(field: CMField<Boolean>, quantifier: AggregateQuantifier = ALL): ArrayAggExpression =
    arrayAgg(field.asField(), quantifier)

@JvmName("arrayAggNumber")
fun arrayAgg(field: CMList<out Number>, quantifier: AggregateQuantifier = ALL): ArrayAggExpression =
    arrayAgg(field.asArrayField(), quantifier)

@JvmName("arrayAggString")
fun arrayAgg(field: CMList<String>, quantifier: AggregateQuantifier = ALL): ArrayAggExpression =
    arrayAgg(field.asArrayField(), quantifier)

@JvmName("arrayAggBoolean")
fun arrayAgg(field: CMList<Boolean>, quantifier: AggregateQuantifier = ALL): ArrayAggExpression =
    arrayAgg(field.asArrayField(), quantifier)
