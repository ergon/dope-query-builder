package ch.ergon.dope.resolvable.bucket

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Deletable
import ch.ergon.dope.resolvable.Fromable
import ch.ergon.dope.resolvable.Joinable
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.bucket.IndexType.USING_FTS
import ch.ergon.dope.resolvable.bucket.IndexType.USING_GSI
import ch.ergon.dope.util.formatIndexToQueryString
import ch.ergon.dope.util.formatListToQueryStringWithBrackets
import ch.ergon.dope.util.formatToQueryStringWithSymbol

private const val USE_INDEX = "USE INDEX"

enum class IndexType(val queryString: String) {
    USING_GSI("USING GSI"),
    USING_FTS("USING FTS"),
}

class IndexReference(
    private val indexName: String? = null,
    private val indexType: IndexType? = null,
) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager) = DopeQuery(
        queryString = formatIndexToQueryString(indexName, indexType?.queryString),
    )
}

class UseIndex(
    val bucket: Bucket,
    vararg val indexReference: IndexReference,
) : Joinable, Deletable, Fromable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val bucketDopeQuery = bucket.toDopeQuery(manager)
        val indexReferenceDopeQueries = indexReference.map { it.toDopeQuery(manager) }
        return DopeQuery(
            queryString = formatToQueryStringWithSymbol(
                bucketDopeQuery.queryString,
                USE_INDEX,
                formatListToQueryStringWithBrackets(indexReferenceDopeQueries, separator = ", ", prefix = "(", postfix = ")"),
            ),
            parameters = bucketDopeQuery.parameters.merge(*indexReferenceDopeQueries.map { it.parameters }.toTypedArray()),
        )
    }
}

fun UseIndex.useIndex(indexName: String) = UseIndex(bucket, *indexReference, IndexReference(indexName))

fun UseIndex.useGsiIndex(indexName: String? = null) = UseIndex(bucket, *indexReference, IndexReference(indexName, USING_GSI))

fun UseIndex.useFtsIndex(indexName: String? = null) = UseIndex(bucket, *indexReference, IndexReference(indexName, USING_FTS))

fun Bucket.useIndex(indexName: String? = null) = UseIndex(this, IndexReference(indexName))

fun Bucket.useGsiIndex(indexName: String? = null) = UseIndex(this, IndexReference(indexName, USING_GSI))

fun Bucket.useFtsIndex(indexName: String? = null) = UseIndex(this, IndexReference(indexName, USING_FTS))
