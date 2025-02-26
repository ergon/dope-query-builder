package ch.ergon.dope.resolvable.expression.type.function.search

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.bucket.Bucket
import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.search.SearchFunctionType.SEARCH
import ch.ergon.dope.resolvable.expression.type.function.search.SearchFunctionType.SEARCH_META
import ch.ergon.dope.resolvable.expression.type.function.search.SearchFunctionType.SEARCH_SCORE
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.ValidType

enum class SearchFunctionType(val type: String) {
    SEARCH("SEARCH"),
    SEARCH_META("SEARCH_META"),
    SEARCH_SCORE("SEARCH_SCORE"),
}

sealed class SearchFunctionExpression(
    private val identifier: Field<out ValidType>? = null,
    private val keyspaceIdentifier: Bucket? = null,
    private val stringSearchQuery: String? = null,
    private val objectSearchQuery: Map<String, Any>? = null,
    private val options: Map<String, Any>? = null,
) : TypeExpression<BooleanType>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val identifierDopeQuery = identifier?.toDopeQuery(manager)
        val keyspaceIdentifierDopeQuery = keyspaceIdentifier?.toDopeQuery(manager)
        val stringSearchQueryDopeQuery = stringSearchQuery?.toDopeType()?.toDopeQuery(manager)
        val objectSearchQuery = objectSearchQuery?.toDopeType()?.toDopeQuery(manager)
        val optionsDopeQuery = options?.toDopeType()?.toDopeQuery(manager)
        return DopeQuery(
            queryString = toFunctionQueryString(
                SEARCH.type,
                identifierDopeQuery,
                keyspaceIdentifierDopeQuery,
                stringSearchQueryDopeQuery,
                objectSearchQuery,
                optionsDopeQuery,
            ),
            parameters = (identifierDopeQuery?.parameters ?: DopeParameters()).merge(
                keyspaceIdentifierDopeQuery?.parameters,
                objectSearchQuery?.parameters,
                optionsDopeQuery?.parameters,
            ),
        )
    }
}

class SearchFieldStringFunctionExpression(
    identifier: Field<out ValidType>,
    searchQuery: String,
    options: Map<String, Any>? = null,
) : SearchFunctionExpression(
    identifier = identifier,
    stringSearchQuery = searchQuery,
    options = options,
)

fun fullTextSearch(identifier: Field<out ValidType>, searchQuery: String, options: Map<String, Any>? = null) =
    SearchFieldStringFunctionExpression(identifier, searchQuery, options)

class SearchBucketStringFunctionExpression(
    identifier: Bucket,
    searchQuery: String,
    options: Map<String, Any>? = null,
) : SearchFunctionExpression(
    keyspaceIdentifier = identifier,
    stringSearchQuery = searchQuery,
    options = options,
)

fun fullTextSearch(identifier: Bucket, searchQuery: String, options: Map<String, Any>? = null) =
    SearchBucketStringFunctionExpression(identifier, searchQuery, options)

class SearchFieldObjectFunctionExpression(
    identifier: Field<out ValidType>,
    searchQuery: Map<String, Any>,
    options: Map<String, Any>? = null,
) : SearchFunctionExpression(
    identifier = identifier,
    objectSearchQuery = searchQuery,
    options = options,
)

fun fullTextSearch(identifier: Field<out ValidType>, searchQuery: Map<String, Any>, options: Map<String, Any>? = null) =
    SearchFieldObjectFunctionExpression(identifier, searchQuery, options)

class SearchBucketObjectFunctionExpression(
    identifier: Bucket,
    searchQuery: Map<String, Any>,
    options: Map<String, Any>? = null,
) : SearchFunctionExpression(
    keyspaceIdentifier = identifier,
    objectSearchQuery = searchQuery,
    options = options,
)

fun fullTextSearch(identifier: Bucket, searchQuery: Map<String, Any>, options: Map<String, Any>? = null) =
    SearchBucketObjectFunctionExpression(identifier, searchQuery, options)

sealed class SearchDependencyFunctionExpression<T : ValidType>(
    private val searchFunctionType: SearchFunctionType,
    private val outName: String? = null,
) : TypeExpression<T>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        return DopeQuery(
            queryString = toFunctionQueryString(
                searchFunctionType.type,
                outName?.let { "`$it`" },
            ),
        )
    }
}

class SearchMetaFunctionExpression(outName: String? = null) :
    SearchDependencyFunctionExpression<ObjectType>(SEARCH_META, outName)

fun fullTextSearchMeta(outName: String? = null) = SearchMetaFunctionExpression(outName)

class SearchScoreFunctionExpression(outName: String? = null) :
    SearchDependencyFunctionExpression<NumberType>(SEARCH_SCORE, outName)

fun fullTextSearchScore(outName: String? = null) = SearchScoreFunctionExpression(outName)
