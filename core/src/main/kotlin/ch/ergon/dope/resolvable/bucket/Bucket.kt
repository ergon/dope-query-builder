package ch.ergon.dope.resolvable.bucket

import ch.ergon.dope.resolvable.Deletable
import ch.ergon.dope.resolvable.Fromable
import ch.ergon.dope.resolvable.Joinable
import ch.ergon.dope.resolvable.Nestable
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.Updatable
import ch.ergon.dope.resolvable.expression.SingleExpression
import ch.ergon.dope.validtype.ObjectType

interface Bucket : Fromable, Joinable, Nestable, Deletable, Updatable, SingleExpression<ObjectType> {
    val name: String
    val scope: BucketScope?
}

interface Definable : Bucket {
    val alias: String

    fun asBucketDefinition(): Resolvable = AliasedBucketDefinition(name, alias, scope)
}

data class BucketScope(val name: String, val collection: ScopeCollection? = null) {
    fun withCollection(collectionName: String) = copy(collection = ScopeCollection(collectionName))
    fun withCollection(collection: ScopeCollection) = copy(collection = collection)
}

data class ScopeCollection(val name: String)

data class UnaliasedBucket(
    override val name: String,
    override val scope: BucketScope? = null,
) : Bucket {
    fun alias(alias: String) = AliasedBucket(name, alias, scope)

    fun withScope(scopeName: String) = copy(scope = BucketScope(scopeName))

    fun withScope(scope: BucketScope) = copy(scope = scope)
    fun withScopeAndCollection(scopeName: String, collectionName: String) =
        copy(scope = BucketScope(scopeName, ScopeCollection(collectionName)))
}

data class AliasedBucket(
    override val name: String,
    override val alias: String,
    override val scope: BucketScope? = null,
) : Definable

data class AliasedBucketDefinition(
    val bucketName: String,
    val alias: String,
    val scope: BucketScope? = null,
) : Resolvable
