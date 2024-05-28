package ch.ergon.dope.extension.aggregator

import ch.ergon.dope.asField
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
import com.schwarz.crystalapi.schema.CMField

@JvmName("avgNumber")
fun avg(field: CMField<out Number>, quantifier: AggregateQuantifier? = null): AverageExpression = avg(field.asField(), quantifier)

@JvmName("meanNumber")
fun mean(field: CMField<out Number>, quantifier: AggregateQuantifier? = null): MeanExpression = mean(field.asField(), quantifier)

@JvmName("medianNumber")
fun median(field: CMField<out Number>, quantifier: AggregateQuantifier? = null): MedianExpression = median(field.asField(), quantifier)

@JvmName("sumNumber")
fun sum(field: CMField<out Number>, quantifier: AggregateQuantifier? = null): SumExpression = sum(field.asField(), quantifier)

@JvmName("stddevNumber")
fun stdDev(field: CMField<out Number>, quantifier: AggregateQuantifier? = null): StandardDeviationExpression =
    stdDev(field.asField(), quantifier)

@JvmName("varianceNumber")
fun variance(field: CMField<out Number>, quantifier: AggregateQuantifier? = null): VarianceExpression = variance(field.asField(), quantifier)
