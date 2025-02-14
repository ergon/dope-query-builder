package ch.ergon.dope.resolvable.expression.aggregate

import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.validtype.ValidType

interface AggregateExpression<T : ValidType> : Expression<T>

enum class AggregateQuantifier(val queryString: String) {
    ALL("ALL"),
    DISTINCT("DISTINCT"),
}
