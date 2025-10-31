package ch.ergon.dope.resolvable.keyspace

import ch.ergon.dope.resolvable.Deletable
import ch.ergon.dope.resolvable.Fromable
import ch.ergon.dope.resolvable.Joinable
import ch.ergon.dope.resolvable.Nestable
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.Updatable
import ch.ergon.dope.resolvable.expression.SingleExpression
import ch.ergon.dope.validtype.ObjectType

sealed class KeySpace(
    open val bucket: String,
    open val scope: String? = null,
    open val collection: String? = null,
) : Fromable, Joinable, Nestable, Deletable, Updatable, SingleExpression<ObjectType>

data class UnaliasedKeySpace(
    override val bucket: String,
    override val scope: String? = null,
    override val collection: String? = null,
) : KeySpace(bucket, scope, collection) {
    fun alias(alias: String) = AliasedKeySpace(bucket, scope, collection, alias)

    fun scope(scope: String) = UnaliasedKeySpace(bucket, scope = scope, collection = collection)
    fun collection(collection: String) = UnaliasedKeySpace(bucket, scope = scope, collection = collection)
}

data class AliasedKeySpace(
    override val bucket: String,
    override val scope: String? = null,
    override val collection: String? = null,
    val alias: String,
) : KeySpace(bucket, scope, collection) {
    fun asKeySpaceDefinition() = AliasedKeySpaceDefinition(bucket, scope, collection, alias)
}

data class AliasedKeySpaceDefinition(
    val keyspace: String,
    val scope: String? = null,
    val collection: String? = null,
    val alias: String,
) : Resolvable
