package ch.ergon.dope.resolvable.bucket

import ch.ergon.dope.resolvable.Deletable
import ch.ergon.dope.resolvable.Fromable
import ch.ergon.dope.resolvable.Joinable
import ch.ergon.dope.resolvable.Nestable
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.Updatable
import ch.ergon.dope.resolvable.expression.SingleExpression
import ch.ergon.dope.validtype.ObjectType

sealed class Bucket protected constructor(
    open val name: String,
    open val scopeName: String?,
    open val collectionName: String?,
) : Fromable, Joinable, Nestable, Deletable, Updatable, SingleExpression<ObjectType>

interface Unaliased {
    val name: String
    val scopeName: String?
    val collectionName: String?

    fun alias(alias: String): AliasedBucket =
        AliasedBucket(name, scopeName, collectionName, alias)
}

data class UnaliasedBucket(
    override val name: String,
) : Bucket(name = name, scopeName = null, collectionName = null), Unaliased {
    fun useScope(scopeName: String): ScopedBucket = ScopedBucket(name, scopeName)

    fun useScopeAndCollection(scopeName: String, collectionName: String): CollectionBucket =
        CollectionBucket(name, scopeName, collectionName)
}

data class ScopedBucket(
    override val name: String,
    override val scopeName: String,
) : Bucket(name = name, scopeName = scopeName, collectionName = null), Unaliased {

    fun useCollection(collectionName: String): CollectionBucket =
        CollectionBucket(name, scopeName, collectionName)
}

data class CollectionBucket(
    override val name: String,
    override val scopeName: String,
    override val collectionName: String,
) : Bucket(name = name, scopeName = scopeName, collectionName = collectionName), Unaliased

data class AliasedBucket internal constructor(
    override val name: String,
    override val scopeName: String? = null,
    override val collectionName: String? = null,
    val alias: String,
) : Bucket(name = name, scopeName = scopeName, collectionName = collectionName) {
    fun asBucketDefinition(): AliasedBucketDefinition =
        AliasedBucketDefinition(
            bucketName = name,
            scopeName = scopeName,
            collectionName = collectionName,
            alias = alias,
        )
}

data class AliasedBucketDefinition(
    val bucketName: String,
    val scopeName: String? = null,
    val collectionName: String? = null,
    val alias: String,
) : Resolvable
