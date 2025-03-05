package ch.ergon.dope.resolvable.expression.type.function.search

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.bucket.Bucket
import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.search.SearchFunctionType.SEARCH
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

enum class SearchFunctionType(val type: String) {
    SEARCH("SEARCH"),
    SEARCH_META("SEARCH_META"),
    SEARCH_SCORE("SEARCH_SCORE"),
}

class SearchFunctionExpression : TypeExpression<BooleanType>, FunctionOperator {
    private val field: Field<out ValidType>?
    private val bucket: Bucket?
    private val stringSearchExpression: String?
    private val objectSearchExpression: Map<String, Any>?
    private val options: Map<String, Any>?

    constructor(field: Field<out ValidType>, stringSearchExpression: String, options: Map<String, Any>? = null) {
        this.field = field
        this.bucket = null
        this.stringSearchExpression = stringSearchExpression
        this.objectSearchExpression = null
        this.options = options
    }

    constructor(bucket: Bucket, stringSearchExpression: String, options: Map<String, Any>? = null) {
        this.field = null
        this.bucket = bucket
        this.stringSearchExpression = stringSearchExpression
        this.objectSearchExpression = null
        this.options = options
    }

    constructor(field: Field<out ValidType>, objectSearchExpression: Map<String, Any>, options: Map<String, Any>? = null) {
        this.field = field
        this.bucket = null
        this.stringSearchExpression = null
        this.objectSearchExpression = objectSearchExpression
        this.options = options
    }

    constructor(bucket: Bucket, objectSearchExpression: Map<String, Any>, options: Map<String, Any>? = null) {
        this.field = null
        this.bucket = bucket
        this.stringSearchExpression = null
        this.objectSearchExpression = objectSearchExpression
        this.options = options
    }

    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val fieldDopeQuery = field?.toDopeQuery(manager)
        val bucketDopeQuery = bucket?.toDopeQuery(manager)
        val stringSearchExpressionDopeQuery = stringSearchExpression?.toDopeType()?.toDopeQuery(manager)
        val objectSearchExpressionDopeQuery = objectSearchExpression?.toDopeType()?.toDopeQuery(manager)
        val optionsDopeQuery = options?.toDopeType()?.toDopeQuery(manager)
        return DopeQuery(
            queryString = toFunctionQueryString(
                SEARCH.type,
                fieldDopeQuery,
                bucketDopeQuery,
                stringSearchExpressionDopeQuery,
                objectSearchExpressionDopeQuery,
                optionsDopeQuery,
            ),
            parameters = (fieldDopeQuery?.parameters ?: DopeParameters()).merge(
                bucketDopeQuery?.parameters,
                objectSearchExpressionDopeQuery?.parameters,
                optionsDopeQuery?.parameters,
            ),
        )
    }
}

fun fullTextSearch(field: Field<out ValidType>, stringSearchExpression: String, options: Map<String, Any>? = null) =
    SearchFunctionExpression(field, stringSearchExpression, options)

fun fullTextSearch(bucket: Bucket, stringSearchExpression: String, options: Map<String, Any>? = null) =
    SearchFunctionExpression(bucket, stringSearchExpression, options)

fun fullTextSearch(field: Field<out ValidType>, objectSearchExpression: Map<String, Any>, options: Map<String, Any>? = null) =
    SearchFunctionExpression(field, objectSearchExpression, options)

fun fullTextSearch(bucket: Bucket, objectSearchExpression: Map<String, Any>, options: Map<String, Any>? = null) =
    SearchFunctionExpression(bucket, objectSearchExpression, options)
