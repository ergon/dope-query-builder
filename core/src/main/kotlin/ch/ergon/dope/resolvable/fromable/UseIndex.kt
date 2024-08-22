package ch.ergon.dope.resolvable.fromable

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.formatToQueryStringWithSymbol

private const val USE_INDEX = "USE INDEX"

enum class IndexType(val type: String) {
    USING_GSI("USING GSI"),
    USING_FTS("USING FTS"),
}

class IndexReference : Resolvable {
    private val indexName: String?
    private val indexType: IndexType?

    constructor(indexName: String, indexType: IndexType) {
        this.indexName = indexName
        this.indexType = indexType
    }

    constructor(indexName: String) {
        this.indexName = indexName
        this.indexType = null
    }

    constructor(indexType: IndexType) {
        this.indexName = null
        this.indexType = indexType
    }

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
    private val bucket: Bucket,
    private vararg val indexReference: IndexReference,
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

fun Bucket.useIndex(vararg indexReference: IndexReference) = UseIndex(this, *indexReference)
