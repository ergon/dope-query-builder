package ch.ergon.dope.extension.aggregator

import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.arrayAggregate
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("arrayAggNumber")
fun arrayAggregate(field: CMJsonField<out Number>, quantifier: AggregateQuantifier? = null) =
    arrayAggregate(field.toDopeType(), quantifier)

@JvmName("arrayAggString")
fun arrayAggregate(field: CMJsonField<String>, quantifier: AggregateQuantifier? = null) =
    arrayAggregate(field.toDopeType(), quantifier)

@JvmName("arrayAggBoolean")
fun arrayAggregate(field: CMJsonField<Boolean>, quantifier: AggregateQuantifier? = null) =
    arrayAggregate(field.toDopeType(), quantifier)

@JvmName("arrayAggNumberArray")
fun arrayAggregate(field: CMJsonList<out Number>, quantifier: AggregateQuantifier? = null) =
    arrayAggregate(field.toDopeType(), quantifier)

@JvmName("arrayAggStringArray")
fun arrayAggregate(field: CMJsonList<String>, quantifier: AggregateQuantifier? = null) =
    arrayAggregate(field.toDopeType(), quantifier)

@JvmName("arrayAggBooleanArray")
fun arrayAggregate(field: CMJsonList<Boolean>, quantifier: AggregateQuantifier? = null) =
    arrayAggregate(field.toDopeType(), quantifier)
