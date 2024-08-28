package ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.ValidType

class SearchResult<T : ValidType, U : ValidType>(
    private val searchExpression: UnaliasedExpression<T>,
    private val resultExpression: UnaliasedExpression<U>,
) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val searchExpressionDopeQuery = searchExpression.toDopeQuery(manager)
        val resultExpressionDopeQuery = resultExpression.toDopeQuery(manager)
        return DopeQuery(
            queryString = "${searchExpressionDopeQuery.queryString}, ${resultExpressionDopeQuery.queryString}",
            parameters = searchExpressionDopeQuery.parameters + resultExpressionDopeQuery.parameters,
        )
    }
}

fun <T : ValidType, U : ValidType> UnaliasedExpression<T>.resultsIn(resultExpression: UnaliasedExpression<U>) =
    SearchResult(this, resultExpression)

fun UnaliasedExpression<out ValidType>.resultsIn(resultExpression: Number) =
    SearchResult(this, resultExpression.toDopeType())

fun UnaliasedExpression<out ValidType>.resultsIn(resultExpression: String) =
    SearchResult(this, resultExpression.toDopeType())

fun UnaliasedExpression<out ValidType>.resultsIn(resultExpression: Boolean) =
    SearchResult(this, resultExpression.toDopeType())

fun Number.resultsIn(resultExpression: UnaliasedExpression<out ValidType>) =
    SearchResult(this.toDopeType(), resultExpression)

fun String.resultsIn(resultExpression: UnaliasedExpression<out ValidType>) =
    SearchResult(this.toDopeType(), resultExpression)

fun Boolean.resultsIn(resultExpression: UnaliasedExpression<out ValidType>) =
    SearchResult(this.toDopeType(), resultExpression)

fun Number.resultsIn(resultExpression: Number) =
    SearchResult(this.toDopeType(), resultExpression.toDopeType())

fun Number.resultsIn(resultExpression: String) =
    SearchResult(this.toDopeType(), resultExpression.toDopeType())

fun Number.resultsIn(resultExpression: Boolean) =
    SearchResult(this.toDopeType(), resultExpression.toDopeType())

fun String.resultsIn(resultExpression: Number) =
    SearchResult(this.toDopeType(), resultExpression.toDopeType())

fun String.resultsIn(resultExpression: String) =
    SearchResult(this.toDopeType(), resultExpression.toDopeType())

fun String.resultsIn(resultExpression: Boolean) =
    SearchResult(this.toDopeType(), resultExpression.toDopeType())

fun Boolean.resultsIn(resultExpression: Number) =
    SearchResult(this.toDopeType(), resultExpression.toDopeType())

fun Boolean.resultsIn(resultExpression: String) =
    SearchResult(this.toDopeType(), resultExpression.toDopeType())

fun Boolean.resultsIn(resultExpression: Boolean) =
    SearchResult(this.toDopeType(), resultExpression.toDopeType())
