package ch.ergon.dope.resolvable.bucket

import ch.ergon.dope.resolvable.Deletable
import ch.ergon.dope.resolvable.Fromable
import ch.ergon.dope.resolvable.Joinable
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.bucket.IndexType.USING_FTS
import ch.ergon.dope.resolvable.bucket.IndexType.USING_GSI

enum class IndexType {
    USING_GSI,
    USING_FTS,
}

data class IndexReference(
    val indexName: String? = null,
    val indexType: IndexType? = null,
) : Resolvable

data class UseIndex(
    val bucket: Bucket,
    val indexReferences: List<IndexReference> = emptyList(),
) : Joinable, Deletable, Fromable

fun UseIndex.useIndex(indexName: String) = UseIndex(bucket, indexReferences + IndexReference(indexName))

fun UseIndex.useGsiIndex(indexName: String? = null) = UseIndex(bucket, indexReferences + IndexReference(indexName, USING_GSI))

fun UseIndex.useFtsIndex(indexName: String? = null) = UseIndex(bucket, indexReferences + IndexReference(indexName, USING_FTS))

fun Bucket.useIndex(indexName: String? = null) = UseIndex(this, listOf(IndexReference(indexName)))

fun Bucket.useGsiIndex(indexName: String? = null) = UseIndex(this, listOf(IndexReference(indexName, USING_GSI)))

fun Bucket.useFtsIndex(indexName: String? = null) = UseIndex(this, listOf(IndexReference(indexName, USING_FTS)))
