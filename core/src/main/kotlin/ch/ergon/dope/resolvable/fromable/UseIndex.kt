package ch.ergon.dope.resolvable.fromable

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.formatToQueryStringWithSymbol
import ch.ergon.dope.resolvable.fromable.IndexType.USING_FTS
import ch.ergon.dope.resolvable.fromable.IndexType.USING_GSI

private const val USE_INDEX = "USE INDEX"

enum class IndexType(val type: String) {
    USING_GSI("USING GSI"),
    USING_FTS("USING FTS"),
}

class IndexReference(
    private val indexName: String? = null,
    private val indexType: IndexType? = null,
) : Resolvable {
    override fun toDopeQuery(): DopeQuery {
        val queryString = buildString {
            if (indexName != null) {
                append("`$indexName`")
            }
            if (indexType != null) {
                if (isNotEmpty()) append(" ")
                append(indexType.type)
            }
        }
        return DopeQuery(queryString, emptyMap())
    }
}

class UseIndex(
    val bucket: Bucket,
    vararg val indexReference: IndexReference,
) : IBucket {
    override fun toDopeQuery(): DopeQuery {
        val bucketDopeQuery = bucket.toDopeQuery()
        val indexReferenceDopeQueries = indexReference.map { it.toDopeQuery() }
        val indexReferences = indexReferenceDopeQueries.joinToString(", ", prefix = "(", postfix = ")") { it.queryString }
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(bucketDopeQuery.queryString, USE_INDEX, indexReferences),
            parameters = bucketDopeQuery.parameters + indexReferenceDopeQueries.fold(emptyMap()) { indexReferenceParameters, field ->
                indexReferenceParameters + field.parameters
            },
        )
    }
}

fun UseIndex.useIndex(indexName: String) = UseIndex(bucket, *indexReference, IndexReference(indexName))

fun UseIndex.useGsiIndex(indexName: String? = null) = UseIndex(bucket, *indexReference, IndexReference(indexName, USING_GSI))

fun UseIndex.useFtsIndex(indexName: String? = null) = UseIndex(bucket, *indexReference, IndexReference(indexName, USING_FTS))

fun Bucket.useIndex(indexName: String? = null) = UseIndex(this, IndexReference(indexName))

fun Bucket.useGsiIndex(indexName: String? = null) = UseIndex(this, IndexReference(indexName, USING_GSI))

fun Bucket.useFtsIndex(indexName: String? = null) = UseIndex(this, IndexReference(indexName, USING_FTS))
