package ch.ergon.dope.resolvable.keyspace

import ch.ergon.dope.resolvable.Deletable
import ch.ergon.dope.resolvable.Fromable
import ch.ergon.dope.resolvable.Joinable
import ch.ergon.dope.resolvable.Nestable
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.Updatable
import ch.ergon.dope.resolvable.expression.SingleExpression
import ch.ergon.dope.validtype.ObjectType

sealed class Keyspace(
    open val bucket: String,
    open val scope: String? = null,
    open val collection: String? = null,
) : Fromable, Joinable, Nestable, Deletable, Updatable, SingleExpression<ObjectType>

data class UnaliasedKeyspace(
    override val bucket: String,
    override val scope: String? = null,
    override val collection: String? = null,
) : Keyspace(bucket, scope, collection) {
    fun alias(alias: String) = AliasedKeyspace(bucket, scope, collection, alias)

    fun scope(scope: String) = UnaliasedKeyspace(bucket, scope = scope, collection = collection)
    fun collection(collection: String) = UnaliasedKeyspace(bucket, scope = scope, collection = collection)
}

data class AliasedKeyspace(
    override val bucket: String,
    override val scope: String? = null,
    override val collection: String? = null,
    val alias: String,
) : Keyspace(bucket, scope, collection) {
    fun asKeyspaceDefinition() = AliasedKeyspaceDefinition(bucket, scope, collection, alias)
}

data class AliasedKeyspaceDefinition(
    val keyspace: String,
    val scope: String? = null,
    val collection: String? = null,
    val alias: String,
) : Resolvable
