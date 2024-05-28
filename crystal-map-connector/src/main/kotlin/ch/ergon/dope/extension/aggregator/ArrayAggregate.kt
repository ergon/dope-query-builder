package ch.ergon.dope.extension.aggregator

import ch.ergon.dope.asArrayField
import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.ArrayAggregateExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.arrayAggregate
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("arrayAggNumber")
fun arrayAggregate(field: CMField<out Number>, quantifier: AggregateQuantifier? = null): ArrayAggregateExpression =
    arrayAggregate(field.asField(), quantifier)

@JvmName("arrayAggString")
fun arrayAggregate(field: CMField<String>, quantifier: AggregateQuantifier? = null): ArrayAggregateExpression =
    arrayAggregate(field.asField(), quantifier)

@JvmName("arrayAggBoolean")
fun arrayAggregate(field: CMField<Boolean>, quantifier: AggregateQuantifier? = null): ArrayAggregateExpression =
    arrayAggregate(field.asField(), quantifier)

@JvmName("arrayAggNumber")
fun arrayAggregate(field: CMList<out Number>, quantifier: AggregateQuantifier? = null): ArrayAggregateExpression =
    arrayAggregate(field.asArrayField(), quantifier)

@JvmName("arrayAggString")
fun arrayAggregate(field: CMList<String>, quantifier: AggregateQuantifier? = null): ArrayAggregateExpression =
    arrayAggregate(field.asArrayField(), quantifier)

@JvmName("arrayAggBoolean")
fun arrayAggregate(field: CMList<Boolean>, quantifier: AggregateQuantifier? = null): ArrayAggregateExpression =
    arrayAggregate(field.asArrayField(), quantifier)
