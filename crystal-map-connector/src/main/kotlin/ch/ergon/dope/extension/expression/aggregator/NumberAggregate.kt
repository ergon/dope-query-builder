package ch.ergon.dope.extension.expression.aggregator

import ch.ergon.dope.resolvable.expression.aggregate.AggregateQuantifier
import ch.ergon.dope.resolvable.expression.aggregate.AverageExpression
import ch.ergon.dope.resolvable.expression.aggregate.MeanExpression
import ch.ergon.dope.resolvable.expression.aggregate.MedianExpression
import ch.ergon.dope.resolvable.expression.aggregate.StandardDeviationExpression
import ch.ergon.dope.resolvable.expression.aggregate.SumExpression
import ch.ergon.dope.resolvable.expression.aggregate.VarianceExpression
import ch.ergon.dope.resolvable.expression.aggregate.avg
import ch.ergon.dope.resolvable.expression.aggregate.mean
import ch.ergon.dope.resolvable.expression.aggregate.median
import ch.ergon.dope.resolvable.expression.aggregate.stdDev
import ch.ergon.dope.resolvable.expression.aggregate.sum
import ch.ergon.dope.resolvable.expression.aggregate.variance
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
