package ch.ergon.dope.resolvable.expression.unaliased.aggregator

import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.validtype.NumberType

interface AggregateExpression : UnaliasedExpression<NumberType>

enum class AggregateQuantifier {
    ALL,
    DISTINCT,
}
