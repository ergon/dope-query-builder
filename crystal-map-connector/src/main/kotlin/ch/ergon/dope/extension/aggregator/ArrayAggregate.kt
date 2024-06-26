package ch.ergon.dope.extension.aggregator

import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.ArrayAggregateExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.arrayAggregate
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("arrayAggNumber")
fun arrayAggregate(field: CMField<out Number>, quantifier: AggregateQuantifier? = null):
    ArrayAggregateExpression<out ValidType> = arrayAggregate(field.toDopeType(), quantifier)

@JvmName("arrayAggString")
fun arrayAggregate(field: CMField<String>, quantifier: AggregateQuantifier? = null):
    ArrayAggregateExpression<out ValidType> = arrayAggregate(field.toDopeType(), quantifier)

@JvmName("arrayAggBoolean")
fun arrayAggregate(field: CMField<Boolean>, quantifier: AggregateQuantifier? = null):
    ArrayAggregateExpression<out ValidType> = arrayAggregate(field.toDopeType(), quantifier)

@JvmName("arrayAggNumberArray")
fun arrayAggregate(field: CMList<out Number>, quantifier: AggregateQuantifier? = null):
    ArrayAggregateExpression<out ValidType> = arrayAggregate(field.toDopeType(), quantifier)

@JvmName("arrayAggStringArray")
fun arrayAggregate(field: CMList<String>, quantifier: AggregateQuantifier? = null):
    ArrayAggregateExpression<out ValidType> = arrayAggregate(field.toDopeType(), quantifier)

@JvmName("arrayAggBooleanArray")
fun arrayAggregate(field: CMList<Boolean>, quantifier: AggregateQuantifier? = null):
    ArrayAggregateExpression<out ValidType> = arrayAggregate(field.toDopeType(), quantifier)
