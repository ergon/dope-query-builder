package ch.ergon.dope.extension.aggregator

import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.CountExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.count
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMJsonList

@JvmName("countNumber")
fun count(field: CMJsonField<out Number>, quantifier: AggregateQuantifier? = null):
    CountExpression = count(field.toDopeType(), quantifier)

@JvmName("countString")
fun count(field: CMJsonField<String>, quantifier: AggregateQuantifier? = null):
    CountExpression = count(field.toDopeType(), quantifier)

@JvmName("countBoolean")
fun count(field: CMJsonField<Boolean>, quantifier: AggregateQuantifier? = null):
    CountExpression = count(field.toDopeType(), quantifier)

@JvmName("countNumber")
fun count(field: CMJsonList<out Number>, quantifier: AggregateQuantifier? = null):
    CountExpression = count(field.toDopeType(), quantifier)

@JvmName("countString")
fun count(field: CMJsonList<String>, quantifier: AggregateQuantifier? = null):
    CountExpression = count(field.toDopeType(), quantifier)

@JvmName("countBoolean")
fun count(field: CMJsonList<Boolean>, quantifier: AggregateQuantifier? = null):
    CountExpression = count(field.toDopeType(), quantifier)
