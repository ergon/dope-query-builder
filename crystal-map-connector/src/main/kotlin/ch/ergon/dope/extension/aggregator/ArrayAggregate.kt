package ch.ergon.dope.extension.aggregator

import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.ArrayAggregateExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.arrayAggregate
import ch.ergon.dope.toDopeArrayField
import ch.ergon.dope.toDopeField
import ch.ergon.dope.validtype.ValidType
import com.schwarz.crystalapi.schema.CMField
import com.schwarz.crystalapi.schema.CMList

@JvmName("arrayAggNumber")
fun arrayAggregate(field: CMField<out Number>, quantifier: AggregateQuantifier? = null):
    ArrayAggregateExpression<out ValidType> = arrayAggregate(field.toDopeField(), quantifier)

@JvmName("arrayAggString")
fun arrayAggregate(field: CMField<String>, quantifier: AggregateQuantifier? = null):
    ArrayAggregateExpression<out ValidType> = arrayAggregate(field.toDopeField(), quantifier)

@JvmName("arrayAggBoolean")
fun arrayAggregate(field: CMField<Boolean>, quantifier: AggregateQuantifier? = null):
    ArrayAggregateExpression<out ValidType> = arrayAggregate(field.toDopeField(), quantifier)

@JvmName("arrayAggNumberArray")
fun arrayAggregate(field: CMList<out Number>, quantifier: AggregateQuantifier? = null):
    ArrayAggregateExpression<out ValidType> = arrayAggregate(field.toDopeArrayField(), quantifier)

@JvmName("arrayAggStringArray")
fun arrayAggregate(field: CMList<String>, quantifier: AggregateQuantifier? = null):
    ArrayAggregateExpression<out ValidType> = arrayAggregate(field.toDopeArrayField(), quantifier)

@JvmName("arrayAggBooleanArray")
fun arrayAggregate(field: CMList<Boolean>, quantifier: AggregateQuantifier? = null):
    ArrayAggregateExpression<out ValidType> = arrayAggregate(field.toDopeArrayField(), quantifier)
