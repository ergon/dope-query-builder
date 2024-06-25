package ch.ergon.dope.extension.aggregator

import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.CountExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.count
import ch.ergon.dope.toDopeArrayField
import ch.ergon.dope.toDopeField
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("countNumber")
fun count(field: CMField<out Number>, quantifier: AggregateQuantifier? = null):
    CountExpression = count(field.toDopeField(), quantifier)

@JvmName("countString")
fun count(field: CMField<String>, quantifier: AggregateQuantifier? = null):
    CountExpression = count(field.toDopeField(), quantifier)

@JvmName("countBoolean")
fun count(field: CMField<Boolean>, quantifier: AggregateQuantifier? = null):
    CountExpression = count(field.toDopeField(), quantifier)

@JvmName("countNumber")
fun count(field: CMList<out Number>, quantifier: AggregateQuantifier? = null):
    CountExpression = count(field.toDopeArrayField(), quantifier)

@JvmName("countString")
fun count(field: CMList<String>, quantifier: AggregateQuantifier? = null):
    CountExpression = count(field.toDopeArrayField(), quantifier)

@JvmName("countBoolean")
fun count(field: CMList<Boolean>, quantifier: AggregateQuantifier? = null):
    CountExpression = count(field.toDopeArrayField(), quantifier)
