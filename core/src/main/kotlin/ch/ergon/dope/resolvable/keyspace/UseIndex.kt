package ch.ergon.dope.resolvable.keyspace

import ch.ergon.dope.resolvable.Deletable
import ch.ergon.dope.resolvable.Fromable
import ch.ergon.dope.resolvable.Joinable
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.keyspace.IndexType.USING_FTS
import ch.ergon.dope.resolvable.keyspace.IndexType.USING_GSI

enum class IndexType {
    USING_GSI,
    USING_FTS,
}

data class IndexReference(
    val indexName: String? = null,
    val indexType: IndexType? = null,
) : Resolvable

data class UseIndex(
    val keyspace: Keyspace,
    val indexReferences: List<IndexReference> = emptyList(),
) : Joinable, Deletable, Fromable

fun UseIndex.useIndex(indexName: String) = UseIndex(keyspace, indexReferences + IndexReference(indexName))

fun UseIndex.useGsiIndex(indexName: String? = null) = UseIndex(keyspace, indexReferences + IndexReference(indexName, USING_GSI))

fun UseIndex.useFtsIndex(indexName: String? = null) = UseIndex(keyspace, indexReferences + IndexReference(indexName, USING_FTS))

fun Keyspace.useIndex(indexName: String? = null) = UseIndex(this, listOf(IndexReference(indexName)))

fun Keyspace.useGsiIndex(indexName: String? = null) = UseIndex(this, listOf(IndexReference(indexName, USING_GSI)))

fun Keyspace.useFtsIndex(indexName: String? = null) = UseIndex(this, listOf(IndexReference(indexName, USING_FTS)))
