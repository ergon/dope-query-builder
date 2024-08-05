package ch.ergon.dope.resolvable.fromable

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.AsteriskExpression
import ch.ergon.dope.resolvable.expression.Expression

sealed class Bucket(open val name: String) : Fromable, Expression {
    override fun toDopeQuery() = DopeQuery("`$name`", emptyMap())
}

open class UnaliasedBucket(name: String) : Bucket(name) {
    fun alias(alias: String) = AliasedBucket(name, alias)
}

class AliasedBucket(name: String, val alias: String) : Bucket(name) {
    override fun toDopeQuery() = DopeQuery(
        queryString = "`$name` AS `$alias`",
        parameters = emptyMap(),
    )
}

fun Bucket.asterisk() = AsteriskExpression(this)
