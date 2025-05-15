package ch.ergon.dope.resolvable.expression.rowscope.aggregate

enum class AggregateQuantifier(val queryString: String) {
    ALL("ALL"),
    DISTINCT("DISTINCT"),
}
