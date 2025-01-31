package ch.ergon.dope.extension.aggregator

import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.max
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("maxNumber")
fun max(field: CMJsonField<out Number>, quantifier: AggregateQuantifier? = null) = max(field.toDopeType(), quantifier)

@JvmName("maxString")
fun max(field: CMJsonField<String>, quantifier: AggregateQuantifier? = null) = max(field.toDopeType(), quantifier)

@JvmName("maxBoolean")
fun max(field: CMJsonField<Boolean>, quantifier: AggregateQuantifier? = null) = max(field.toDopeType(), quantifier)

@JvmName("maxNumber")
fun max(field: CMJsonList<out Number>, quantifier: AggregateQuantifier? = null) = max(field.toDopeType(), quantifier)

@JvmName("maxString")
fun max(field: CMJsonList<String>, quantifier: AggregateQuantifier? = null) = max(field.toDopeType(), quantifier)

@JvmName("maxBoolean")
fun max(field: CMJsonList<Boolean>, quantifier: AggregateQuantifier? = null) = max(field.toDopeType(), quantifier)
