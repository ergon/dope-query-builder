package ch.ergon.dope.resolvable.expression.type.function.search

import ch.ergon.dope.resolvable.bucket.Bucket
import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.IField
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

enum class SearchFunctionType {
    SEARCH,
    SEARCH_META,
    SEARCH_SCORE,
}

interface ISearchFunctionExpression : FunctionOperator<BooleanType> {
    val field: IField<out ValidType>?
    val bucket: Bucket?
    val stringSearchExpression: String?
    val objectSearchExpression: Map<String, Any>?
    val options: Map<String, Any>?
}

data class SearchFunctionFieldStringExpression(
    override val field: IField<out ValidType>,
    override val stringSearchExpression: String,
    override val options: Map<String, Any>? = null,
) : ISearchFunctionExpression {
    override val bucket: Bucket? = null
    override val objectSearchExpression: Map<String, Any>? = null
}

data class SearchFunctionBucketStringExpression(
    override val bucket: Bucket,
    override val stringSearchExpression: String,
    override val options: Map<String, Any>? = null,
) : ISearchFunctionExpression {
    override val field: IField<out ValidType>? = null
    override val objectSearchExpression: Map<String, Any>? = null
}

data class SearchFunctionFieldObjectExpression(
    override val field: IField<out ValidType>,
    override val objectSearchExpression: Map<String, Any>,
    override val options: Map<String, Any>? = null,
) : ISearchFunctionExpression {
    override val bucket: Bucket? = null
    override val stringSearchExpression: String? = null
}

data class SearchFunctionBucketObjectExpression(
    override val bucket: Bucket,
    override val objectSearchExpression: Map<String, Any>,
    override val options: Map<String, Any>? = null,
) : ISearchFunctionExpression {
    override val field: IField<out ValidType>? = null
    override val stringSearchExpression: String? = null
}

fun fullTextSearch(field: IField<out ValidType>, stringSearchExpression: String, options: Map<String, Any>? = null) =
    SearchFunctionFieldStringExpression(field, stringSearchExpression, options)

fun fullTextSearch(bucket: Bucket, stringSearchExpression: String, options: Map<String, Any>? = null) =
    SearchFunctionBucketStringExpression(bucket, stringSearchExpression, options)

fun fullTextSearch(
    field: IField<out ValidType>,
    objectSearchExpression: Map<String, Any>,
    options: Map<String, Any>? = null,
) = SearchFunctionFieldObjectExpression(field, objectSearchExpression, options)

fun fullTextSearch(bucket: Bucket, objectSearchExpression: Map<String, Any>, options: Map<String, Any>? = null) =
    SearchFunctionBucketObjectExpression(bucket, objectSearchExpression, options)
