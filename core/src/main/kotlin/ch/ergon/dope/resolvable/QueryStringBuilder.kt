package ch.ergon.dope.resolvable

fun formatToQueryString(left: String, right: String) = "$left $right"
fun formatToQueryString(left: String, symbol: String, right: String) = "$left $symbol $right"
fun formatToQueryString(symbol: String, vararg argument: Resolvable) =
    "$symbol ${argument.joinToString(separator = ", ") { it.toQueryString() }}"

fun formatToQueryStringWithBrackets(left: String, symbol: String, right: String) = "($left $symbol $right)"
fun formatToQueryStringWithBrackets(symbol: String, vararg argument: Resolvable) =
    "$symbol(${argument.joinToString(separator = ", ") { it.toQueryString() }})"

fun formatPathToQueryString(name: String, path: String) =
    if (path.isBlank()) {
        name
    } else {
        "$path.$name"
    }
