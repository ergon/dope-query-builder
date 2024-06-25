package ch.ergon.dope.extension.aggregator

import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AverageExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.MeanExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.MedianExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.StandardDeviationExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.SumExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.VarianceExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.avg
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.mean
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.median
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.stdDev
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.sum
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.variance
import ch.ergon.dope.toDopeField
import ch.ergon.dope.validtype.NumberType
import com.schwarz.crystalapi.schema.CMField

@JvmName("avgNumber")
fun avg(field: CMField<out Number>, quantifier: AggregateQuantifier? = null):
    AverageExpression<out NumberType> = avg(field.toDopeField(), quantifier)

@JvmName("meanNumber")
fun mean(field: CMField<out Number>, quantifier: AggregateQuantifier? = null):
    MeanExpression<out NumberType> = mean(field.toDopeField(), quantifier)

@JvmName("medianNumber")
fun median(field: CMField<out Number>, quantifier: AggregateQuantifier? = null):
    MedianExpression<out NumberType> = median(field.toDopeField(), quantifier)

@JvmName("sumNumber")
fun sum(field: CMField<out Number>, quantifier: AggregateQuantifier? = null):
    SumExpression<out NumberType> = sum(field.toDopeField(), quantifier)

@JvmName("stdDevNumber")
fun stdDev(field: CMField<out Number>, quantifier: AggregateQuantifier? = null):
    StandardDeviationExpression<out NumberType> = stdDev(field.toDopeField(), quantifier)

@JvmName("varianceNumber")
fun variance(field: CMField<out Number>, quantifier: AggregateQuantifier? = null):
    VarianceExpression<out NumberType> = variance(field.toDopeField(), quantifier)
