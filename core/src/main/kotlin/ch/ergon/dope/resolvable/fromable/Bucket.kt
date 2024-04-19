package ch.ergon.dope.resolvable.fromable

import ch.ergon.dope.resolvable.clause.Fromable
import ch.ergon.dope.resolvable.expression.AsteriskExpression

sealed class Bucket(open val name: String) : Fromable {
    override fun toQueryString(): String = name
}

open class UnaliasedBucket(name: String) : Bucket(name) {
    fun alias(alias: String) = AliasedBucket(name, alias)
}

class AliasedBucket(name: String, val alias: String) : Bucket(name) {
    override fun toQueryString(): String = "$name AS $alias"
}

fun Bucket.asterisk() = AsteriskExpression(this)
