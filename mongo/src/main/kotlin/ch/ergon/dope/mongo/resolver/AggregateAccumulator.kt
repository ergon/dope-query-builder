package ch.ergon.dope.mongo.resolver

import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateFunctionExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AggregateQuantifier
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.ArrayAggregateExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.AverageExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.CountAsteriskExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.CountExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.MaxExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.MeanExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.MedianExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.MinExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.StandardDeviationExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.SumExpression
import ch.ergon.dope.resolvable.expression.rowscope.aggregate.VarianceExpression
import ch.ergon.dope.resolvable.expression.type.IField

data class AggregateMapping(val accumulator: String, val projectionTemplate: String = "1")

internal const val AGGREGATE_ALIAS_PLACEHOLDER = "%ALIAS%"

internal fun aggregateMapping(expression: AggregateFunctionExpression<*>): AggregateMapping? {
    if (expression.overDefinition != null) return null
    if (expression.quantifier == AggregateQuantifier.DISTINCT) return null

    val fieldName = (expression.selectable as? IField<*>)?.name
    return when (expression) {
        is CountAsteriskExpression -> AggregateMapping("{ \"\$sum\": 1 }")
        is CountExpression -> fieldName?.let {
            AggregateMapping(
                "{ \"\$sum\": { \"\$cond\": [ { \"\$in\": [ { \"\$type\": ${fieldPath(it)} }, " +
                    "[\"null\", \"missing\"] ] }, 0, 1 ] } }",
            )
        }
        is SumExpression -> fieldName?.let { AggregateMapping("{ \"\$sum\": ${fieldPath(it)} }") }
        is AverageExpression -> fieldName?.let { AggregateMapping("{ \"\$avg\": ${fieldPath(it)} }") }
        is MeanExpression -> fieldName?.let { AggregateMapping("{ \"\$avg\": ${fieldPath(it)} }") }
        is MinExpression -> fieldName?.let { AggregateMapping("{ \"\$min\": ${fieldPath(it)} }") }
        is MaxExpression -> fieldName?.let { AggregateMapping("{ \"\$max\": ${fieldPath(it)} }") }
        is MedianExpression -> fieldName?.let {
            AggregateMapping("{ \"\$median\": { \"input\": ${fieldPath(it)}, \"method\": \"approximate\" } }")
        }
        is StandardDeviationExpression -> fieldName?.let { AggregateMapping("{ \"\$stdDevSamp\": ${fieldPath(it)} }") }
        is VarianceExpression -> fieldName?.let {
            AggregateMapping(
                accumulator = "{ \"\$stdDevSamp\": ${fieldPath(it)} }",
                projectionTemplate = "{ \"\$pow\": [ $AGGREGATE_ALIAS_PLACEHOLDER, 2 ] }",
            )
        }
        is ArrayAggregateExpression<*> -> fieldName?.let { AggregateMapping("{ \"\$push\": ${fieldPath(it)} }") }
        else -> null
    }
}
