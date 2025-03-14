package ch.ergon.dope.resolvable.expression.type.function.search

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.operator.FunctionOperator
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.ValidType

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
    SearchDependencyFunctionExpression<ObjectType>(SearchFunctionType.SEARCH_META, outName)

class SearchScoreFunctionExpression(outName: String? = null) :
    SearchDependencyFunctionExpression<NumberType>(SearchFunctionType.SEARCH_SCORE, outName)

fun fullTextSearchMeta(outName: String? = null) = SearchMetaFunctionExpression(outName)

fun fullTextSearchScore(outName: String? = null) = SearchScoreFunctionExpression(outName)
