package ch.ergon.dope.extension.aggregator

import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.MinExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.min
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("minNumber")
fun min(field: CMJsonField<out Number>, quantifier: AggregateQuantifier? = null):
    MinExpression<out ValidType> = min(field.toDopeType(), quantifier)

@JvmName("minString")
fun min(field: CMJsonField<String>, quantifier: AggregateQuantifier? = null):
    MinExpression<out ValidType> = min(field.toDopeType(), quantifier)

@JvmName("minBoolean")
fun min(field: CMJsonField<Boolean>, quantifier: AggregateQuantifier? = null):
    MinExpression<out ValidType> = min(field.toDopeType(), quantifier)

@JvmName("minNumber")
fun min(field: CMJsonList<out Number>, quantifier: AggregateQuantifier? = null):
    MinExpression<out ValidType> = min(field.toDopeType(), quantifier)

@JvmName("minString")
fun min(field: CMJsonList<String>, quantifier: AggregateQuantifier? = null):
    MinExpression<out ValidType> = min(field.toDopeType(), quantifier)

@JvmName("minBoolean")
fun min(field: CMJsonList<Boolean>, quantifier: AggregateQuantifier? = null):
    MinExpression<out ValidType> = min(field.toDopeType(), quantifier)
