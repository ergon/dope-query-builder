package ch.ergon.dope.resolvable.expression.type.function.search

import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.ValidType

sealed class SearchDependencyFunctionExpression<T : ValidType>(
    val searchFunctionType: SearchFunctionType,
    open val outName: String? = null,
) : TypeExpression<T>, FunctionOperator

data class SearchMetaFunctionExpression(override val outName: String? = null) :
    SearchDependencyFunctionExpression<ObjectType>(SearchFunctionType.SEARCH_META, outName)

data class SearchScoreFunctionExpression(override val outName: String? = null) :
    SearchDependencyFunctionExpression<NumberType>(SearchFunctionType.SEARCH_SCORE, outName)

fun fullTextSearchMeta(outName: String? = null) = SearchMetaFunctionExpression(outName)

fun fullTextSearchScore(outName: String? = null) = SearchScoreFunctionExpression(outName)
