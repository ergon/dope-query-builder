package ch.ergon.dope.resolvable.expression

import ch.ergon.dope.resolvable.fromable.AliasedBucket
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.resolvable.fromable.UnaliasedBucket

val ASTERISK = AsteriskExpression()

class AsteriskExpression : Expression {
    private val asterisk = "*"
    private val querySting: String

    constructor(bucket: Bucket) {
        querySting = when (bucket) {
            is AliasedBucket -> bucket.alias
            is UnaliasedBucket -> bucket.name
        } + ".$asterisk"
    }

    constructor() {
        querySting = asterisk
    }

    override fun toQueryString(): String = querySting
}
