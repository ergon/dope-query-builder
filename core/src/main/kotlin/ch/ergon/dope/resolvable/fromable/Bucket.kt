package ch.ergon.dope.resolvable.fromable

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.AsteriskExpression
import ch.ergon.dope.resolvable.expression.Expression

sealed class Bucket(open val name: String) : Fromable, Joinable, Deletable, Updatable, Expression {
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

fun Bucket.asterisk() = AsteriskExpression(this)
