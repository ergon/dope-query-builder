package ch.ergon.dope.extension.expression.aggregate

import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AverageExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.MeanExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.MedianExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.StandardDeviationExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.SumExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.VarianceExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.avg
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.mean
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.median
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.stdDev
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.sum
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.variance
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField

@JvmName("avgNumber")
fun avg(field: CMJsonField<out Number>, quantifier: AggregateQuantifier? = null):
    AverageExpression = avg(field.toDopeType(), quantifier)

@JvmName("meanNumber")
fun mean(field: CMJsonField<out Number>, quantifier: AggregateQuantifier? = null):
    MeanExpression = mean(field.toDopeType(), quantifier)

@JvmName("medianNumber")
fun median(field: CMJsonField<out Number>, quantifier: AggregateQuantifier? = null):
    MedianExpression = median(field.toDopeType(), quantifier)

@JvmName("sumNumber")
fun sum(field: CMJsonField<out Number>, quantifier: AggregateQuantifier? = null):
    SumExpression = sum(field.toDopeType(), quantifier)

@JvmName("stdDevNumber")
fun stdDev(field: CMJsonField<out Number>, quantifier: AggregateQuantifier? = null):
    StandardDeviationExpression = stdDev(field.toDopeType(), quantifier)

@JvmName("varianceNumber")
fun variance(field: CMJsonField<out Number>, quantifier: AggregateQuantifier? = null):
    VarianceExpression = variance(field.toDopeType(), quantifier)
