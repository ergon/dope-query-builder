package ch.ergon.dope.resolvable

import ch.ergon.dope.DopeQuery

fun formatToQueryString(left: String, vararg right: String) =
    "$left ${right.joinToString()}"

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
