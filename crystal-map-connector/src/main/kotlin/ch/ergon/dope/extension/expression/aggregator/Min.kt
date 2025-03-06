package ch.ergon.dope.extension.expression.aggregator

import ch.ergon.dope.resolvable.expression.aggregate.AggregateQuantifier
import ch.ergon.dope.resolvable.expression.aggregate.min
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("minNumber")
fun min(field: CMJsonField<out Number>, quantifier: AggregateQuantifier? = null) = min(
    field.toDopeType(),
    quantifier,
)

@JvmName("minString")
fun min(field: CMJsonField<String>, quantifier: AggregateQuantifier? = null) = min(
    field.toDopeType(),
    quantifier,
)

@JvmName("minBoolean")
fun min(field: CMJsonField<Boolean>, quantifier: AggregateQuantifier? = null) = min(
    field.toDopeType(),
    quantifier,
)

@JvmName("minNumber")
fun min(field: CMJsonList<out Number>, quantifier: AggregateQuantifier? = null) = min(
    field.toDopeType(),
    quantifier,
)

@JvmName("minString")
fun min(field: CMJsonList<String>, quantifier: AggregateQuantifier? = null) = min(
    field.toDopeType(),
    quantifier,
)

@JvmName("minBoolean")
fun min(field: CMJsonList<Boolean>, quantifier: AggregateQuantifier? = null) = min(
    field.toDopeType(),
    quantifier,
)
