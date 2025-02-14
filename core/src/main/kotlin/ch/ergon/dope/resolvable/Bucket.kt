package ch.ergon.dope.resolvable

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.single.SingleExpression
import ch.ergon.dope.validtype.ObjectType

sealed class Bucket(open val name: String) : Fromable, Joinable, Deletable, Updatable, SingleExpression<ObjectType> {
    override fun toDopeQuery(manager: DopeQueryManager) = DopeQuery(
        queryString = "`$name`",
    )
}

open class UnaliasedBucket(name: String) : Bucket(name) {
    fun alias(alias: String) = AliasedBucket(name, alias)
}

class AliasedBucket(name: String, val alias: String) : Bucket(name) {
    override fun toDopeQuery(manager: DopeQueryManager) = DopeQuery(
        queryString = "`$alias`",
    )

    fun asBucketDefinition() = AliasedBucketDefinition(name, alias)
}

class AliasedBucketDefinition(val name: String, val alias: String) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager) = DopeQuery(
        queryString = "`$name` AS `$alias`",
    )
}

fun Bucket.asterisk() = asterisk(this)
