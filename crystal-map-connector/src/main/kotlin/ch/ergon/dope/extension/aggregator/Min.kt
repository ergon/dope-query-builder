package ch.ergon.dope.extension.aggregator

import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.MinExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.min
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("minNumber")
fun min(field: CMField<out Number>, quantifier: AggregateQuantifier? = null):
    MinExpression<out ValidType> = min(field.toDopeType(), quantifier)

@JvmName("minString")
fun min(field: CMField<String>, quantifier: AggregateQuantifier? = null):
    MinExpression<out ValidType> = min(field.toDopeType(), quantifier)

@JvmName("minBoolean")
fun min(field: CMField<Boolean>, quantifier: AggregateQuantifier? = null):
    MinExpression<out ValidType> = min(field.toDopeType(), quantifier)

@JvmName("minNumber")
fun min(field: CMList<out Number>, quantifier: AggregateQuantifier? = null):
    MinExpression<out ValidType> = min(field.toDopeType(), quantifier)

@JvmName("minString")
fun min(field: CMList<String>, quantifier: AggregateQuantifier? = null):
    MinExpression<out ValidType> = min(field.toDopeType(), quantifier)

@JvmName("minBoolean")
fun min(field: CMList<Boolean>, quantifier: AggregateQuantifier? = null):
    MinExpression<out ValidType> = min(field.toDopeType(), quantifier)
