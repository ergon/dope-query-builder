package ch.ergon.dope.resolvable.fromable

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.formatToQueryStringWithSymbol
import ch.ergon.dope.validtype.StringType

private const val USE_INDEX = "USE INDEX"

enum class IndexType(val type: String) {
    USING_GSI("USING GSI"),
    USING_FTS("USING FTS"),
}

class IndexReference : Resolvable {
    private val indexName: TypeExpression<StringType>?
    private val indexType: IndexType?

    constructor(indexName: TypeExpression<StringType>, indexType: IndexType) {
        this.indexName = indexName
        this.indexType = indexType
    }

    constructor(indexName: TypeExpression<StringType>) {
        this.indexName = indexName
        this.indexType = null
    }

    constructor(indexName: String) {
        this.indexName = indexName.toDopeType()
        this.indexType = null
    }

    constructor(indexType: IndexType) {
        this.indexName = null
        this.indexType = indexType
    }

    override fun toDopeQuery(): DopeQuery {
        val fromableDopeQuery = indexName?.toDopeQuery()
        val queryString = buildString {
            if (fromableDopeQuery != null) {
                append(fromableDopeQuery.queryString)
            }
            if (indexType != null) {
                if (isNotEmpty()) append(" ")
                append(indexType.type)
            }
        }
        return DopeQuery(queryString, fromableDopeQuery?.parameters.orEmpty())
    }
}

class UseIndex(
    private vararg val indexReference: IndexReference,
    private val bucket: Bucket,
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

fun Bucket.useIndex(vararg indexReference: IndexReference) = UseIndex(*indexReference, bucket = this)
