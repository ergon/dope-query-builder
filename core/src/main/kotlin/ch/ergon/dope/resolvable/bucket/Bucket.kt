package ch.ergon.dope.resolvable.bucket

import ch.ergon.dope.resolvable.Deletable
import ch.ergon.dope.resolvable.Fromable
import ch.ergon.dope.resolvable.Joinable
import ch.ergon.dope.resolvable.Nestable
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.Updatable
import ch.ergon.dope.resolvable.expression.SingleExpression
import ch.ergon.dope.validtype.ObjectType

sealed class Bucket(open val name: String) : Fromable, Joinable, Nestable, Deletable, Updatable, SingleExpression<ObjectType>

data class UnaliasedBucket(override val name: String) : Bucket(name) {
    fun alias(alias: String) = AliasedBucket(name, alias)
}

data class AliasedBucket(override val name: String, val alias: String) : Bucket(name) {
    fun asBucketDefinition() = AliasedBucketDefinition(name, alias)
}

data class AliasedBucketDefinition(val name: String, val alias: String) : Resolvable
