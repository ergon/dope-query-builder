package ch.ergon.dope.resolvable.expression

import ch.ergon.dope.resolvable.fromable.AliasedBucket
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.resolvable.fromable.UnaliasedBucket

class AsteriskExpression : Expression {
    private val asterisk = "*"
    private val queryString: String

    constructor(bucket: Bucket) {
        queryString = when (bucket) {
            is AliasedBucket -> bucket.alias
            is UnaliasedBucket -> bucket.name
        } + ".$asterisk"
    }

    constructor() {
        queryString = asterisk
    }

    override fun toQueryString(): String = queryString
}
