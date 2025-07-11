package ch.ergon.dope.util

import ch.ergon.dope.DopeQuery

fun formatToQueryString(left: String, vararg right: String, separator: String = ", ") =
    "$left ${right.joinToString(separator)}"

fun formatToQueryStringWithSymbol(left: String, symbol: String, vararg right: String) =
    "$left $symbol ${right.joinToString()}"

fun formatToQueryStringWithSeparator(symbol: String, separator: String, vararg argument: String) =
    "$symbol$separator${argument.joinToString(separator = ", ")}"

fun formatToQueryStringWithBrackets(left: String, symbol: String, right: String) = "($left $symbol $right)"

fun formatToQueryStringWithBrackets(symbol: String, vararg argument: String) =
    "$symbol(${argument.joinToString(separator = ", ")})"

fun formatPathToQueryString(name: String, path: String) =
    if (path.isBlank()) {
        "`$name`"
    } else {
        "${path.split(".").joinToString(".") { "`$it`" }}.`$name`"
    }

fun formatStringListToQueryStringWithBrackets(dopeQueries: List<String>, separator: String = ", ", prefix: String = "(", postfix: String = ")") =
    dopeQueries.joinToString(separator, prefix, postfix)

fun formatListToQueryStringWithBrackets(dopeQueries: List<DopeQuery>, separator: String = ", ", prefix: String = "(", postfix: String = ")") =
    dopeQueries.joinToString(separator, prefix, postfix) { it.queryString }

fun formatIndexToQueryString(indexName: String?, indexType: String?) =
    listOfNotNull(indexName?.let { "`$it`" }, indexType).joinToString(separator = " ")

fun formatPartsToQueryStringWithSpace(vararg string: String?) =
    listOfNotNull(*string).joinToString(separator = " ")

fun formatFunctionArgumentsWithAdditionalStrings(
    functionName: String,
    argumentsQueryString: String,
    vararg additionalStrings: String?,
): String {
    val filtered = additionalStrings.filterNotNull()
    val additional = if (filtered.isNotEmpty()) {
        filtered.joinToString(separator = " ", prefix = " ")
    } else {
        ""
    }
    return "$functionName$argumentsQueryString$additional"
}

fun formatQueryStringWithNullableFirst(
    parentDopeQuery: DopeQuery?,
    symbol: String,
    expressionDopeQuery: DopeQuery,
    expressionsDopeQuery: List<DopeQuery> = emptyList(),
): String =
    parentDopeQuery?.let { "${it.queryString} " }.orEmpty() +
        "$symbol " +
        listOf(expressionDopeQuery, *expressionsDopeQuery.toTypedArray()).joinToString { it.queryString }
