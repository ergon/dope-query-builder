package ch.ergon.dope.resolvable.fromable

import ch.ergon.dope.resolvable.clause.select.Fromable
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.unaliased.type.Field
import ch.ergon.dope.validtype.StringType

abstract class Bucket(open val name: String) : Fromable {
    override fun toQueryString(): String = name
}

open class UnaliasedBucket(name: String) : Bucket(name) {
    fun alias(alias: String) = AliasedBucket(name, alias)
}

class AliasedBucket(name: String, val alias: String) : Bucket(name) {
    override fun toQueryString(): String = "$name AS $alias"
}

fun Bucket.all(): Expression = Field<StringType>(
    "*",
    when (this) {
        is AliasedBucket -> alias
        else -> name
    },
)
