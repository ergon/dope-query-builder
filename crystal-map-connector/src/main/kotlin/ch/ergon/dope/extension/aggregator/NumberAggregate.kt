package ch.ergon.dope.extension.aggregator

import ch.ergon.dope.asField
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AggregateQuantifier.ALL
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.AvgExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.MeanExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.MedianExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.StdDevExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.SumExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.VarianceExpression
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.avg
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.mean
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.median
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.stddev
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.sum
import ch.ergon.dope.resolvable.expression.unaliased.aggregator.variance
import com.schwarz.crystalapi.schema.CMField

@JvmName("avgNumber")
fun avg(field: CMField<out Number>, quantifier: AggregateQuantifier = ALL): AvgExpression = avg(field.asField(), quantifier)

@JvmName("meanNumber")
fun mean(field: CMField<out Number>, quantifier: AggregateQuantifier = ALL): MeanExpression = mean(field.asField(), quantifier)

@JvmName("medianNumber")
fun median(field: CMField<out Number>, quantifier: AggregateQuantifier = ALL): MedianExpression = median(field.asField(), quantifier)

@JvmName("sumNumber")
fun sum(field: CMField<out Number>, quantifier: AggregateQuantifier = ALL): SumExpression = sum(field.asField(), quantifier)

@JvmName("stddevNumber")
fun stddev(field: CMField<out Number>, quantifier: AggregateQuantifier = ALL): StdDevExpression = stddev(field.asField(), quantifier)

@JvmName("varianceNumber")
fun variance(field: CMField<out Number>, quantifier: AggregateQuantifier = ALL): VarianceExpression = variance(field.asField(), quantifier)
