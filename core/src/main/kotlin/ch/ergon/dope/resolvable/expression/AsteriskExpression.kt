package ch.ergon.dope.resolvable.expression

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.fromable.AliasedBucket
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.resolvable.fromable.UnaliasedBucket

const val ASTERISK_STRING = "*"

class AsteriskExpression : Expression {
    private val queryString: String

    constructor(bucket: Bucket) {
        queryString = when (bucket) {
            is AliasedBucket -> bucket.alias
            is UnaliasedBucket -> bucket.name
        } + ".$ASTERISK_STRING"
    }

    constructor() {
        queryString = ASTERISK_STRING
    }

    override fun toDopeQuery(): DopeQuery = DopeQuery(queryString, emptyMap())
}
