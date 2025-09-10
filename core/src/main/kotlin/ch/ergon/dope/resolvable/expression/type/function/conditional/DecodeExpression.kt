package ch.ergon.dope.resolvable.expression.type.function.conditional

import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.ValidType

data class DecodeExpression<T : ValidType, U : ValidType>(
    val decodeExpression: TypeExpression<T>,
    val searchResult: SearchResult<T, out U>,
    val searchResults: List<SearchResult<T, out U>> = emptyList(),
    val default: TypeExpression<out U>? = null,
) : TypeExpression<U>, FunctionOperator

@JvmName("decodeWithGeneric")
fun <T : ValidType, U : ValidType> decode(
    decodeExpression: TypeExpression<T>,
    searchResult: SearchResult<T, U>,
    vararg searchResults: SearchResult<T, U>,
) = DecodeExpression(decodeExpression, searchResult, searchResults.toList())

fun <T : ValidType, U : ValidType> decode(
    decodeExpression: TypeExpression<T>,
    searchResult: SearchResult<T, U>,
    vararg searchResults: SearchResult<T, U>,
    default: TypeExpression<U>,
) = DecodeExpression(decodeExpression, searchResult, searchResults.toList(), default = default)

@JvmName("decodeWithoutGeneric")
fun <T : ValidType> decode(
    decodeExpression: TypeExpression<T>,
    searchResult: SearchResult<T, out ValidType>,
    vararg searchResults: SearchResult<T, out ValidType>,
    default: TypeExpression<out ValidType>? = null,
) = DecodeExpression(decodeExpression, searchResult, searchResults.toList(), default = default)
