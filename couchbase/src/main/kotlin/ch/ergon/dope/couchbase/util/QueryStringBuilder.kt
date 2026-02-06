package ch.ergon.dope.couchbase.util

import ch.ergon.dope.couchbase.CouchbaseDopeQuery

internal fun formatToQueryString(left: String, vararg right: String, separator: String = ", ") =
    "$left ${right.joinToString(separator)}"

internal fun formatToQueryStringWithSymbol(left: String, symbol: String, vararg right: String) =
    "$left $symbol ${right.joinToString()}"

internal fun formatToQueryStringWithSeparator(symbol: String, separator: String, vararg argument: String) =
    "$symbol$separator${argument.joinToString(separator = ", ")}"

internal fun formatToQueryStringWithBrackets(left: String, symbol: String, right: String) = "($left $symbol $right)"

internal fun formatToQueryStringWithBrackets(symbol: String, vararg argument: String) =
    "$symbol(${argument.joinToString(separator = ", ")})"

internal fun formatPathToQueryString(name: String, path: String) =
    if (path.isBlank()) {
        "`$name`"
    } else {
        "${path.split(".").joinToString(".") { "`$it`" }}.`$name`"
    }

internal fun formatKeyspace(name: String): String =
    name.split('.')
        .filter { it.isNotBlank() }
        .joinToString(".") { "`$it`" }

internal fun formatKeyspace(keyspace: String, scope: String? = null, collection: String? = null): String =
    listOfNotNull(keyspace, scope, collection)
        .filter { it.isNotBlank() }
        .joinToString(".") { "`$it`" }

internal fun formatStringListToQueryStringWithBrackets(
    dopeQueries: List<String>,
    separator: String = ", ",
    prefix: String = "(",
    postfix: String = ")",
) = dopeQueries.joinToString(separator, prefix, postfix)

internal fun formatPartsToQueryStringWithSpace(vararg string: String?) =
    listOfNotNull(*string).joinToString(separator = " ")

internal fun formatListToQueryStringWithBrackets(
    dopeQueries: List<CouchbaseDopeQuery>,
    separator: String = ", ",
    prefix: String = "(",
    postfix: String = ")",
) = dopeQueries.joinToString(separator, prefix, postfix) { it.queryString }

internal fun formatQueryStringWithNullableFirst(
    parentDopeQuery: CouchbaseDopeQuery?,
    symbol: String,
    expressionDopeQuery: CouchbaseDopeQuery,
    expressionsDopeQuery: List<CouchbaseDopeQuery> = emptyList(),
): String =
    parentDopeQuery?.let { "${it.queryString} " }.orEmpty() +
        "$symbol " +
        listOf(expressionDopeQuery, *expressionsDopeQuery.toTypedArray()).joinToString { it.queryString }

internal fun formatFunctionQueryString(symbol: String, vararg arguments: String?): String =
    arguments.filterNotNull().joinToString(prefix = "$symbol(", postfix = ")")
