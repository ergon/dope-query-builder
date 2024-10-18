package ch.ergon.dope.resolvable.expression

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.fromable.Bucket

const val ASTERISK_STRING = "*"

class AsteriskExpression : Expression {
    private val queryString: String

    constructor(bucket: Bucket) {
        queryString = when (bucket) {
            is AliasedBucket -> "`${bucket.alias}`"
            is UnaliasedBucket -> "`${bucket.name}`"
        } + ".$ASTERISK_STRING"
    }
}
