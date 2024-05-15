package ch.ergon.dope.resolvable

fun formatToQueryString(left: String, vararg right: String) =
    "$left ${right.joinToString()}"

fun formatMinimumTwoToQueryString(left: String, symbol: String, vararg right: String) =
    "$left $symbol ${right.joinToString()}"

fun formatToQueryStringWithSeparator(symbol: String, separator: String, vararg argument: String) =
    "$symbol$separator${argument.joinToString(separator = ", ")}"

fun formatToQueryStringWithBrackets(left: String, symbol: String, right: String) = "($left $symbol $right)"
fun formatToQueryStringWithBrackets(symbol: String, vararg argument: String) =
    "$symbol(${argument.joinToString(separator = ", ")})"

fun formatPathToQueryString(name: String, path: String) =
    if (path.isBlank()) {
        name
    } else {
        "$path.$name"
    }
