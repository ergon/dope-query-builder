package ch.ergon.dope.resolvable.expression.unaliased.type.conditional

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.ValidType

class SearchResultExpression<T : ValidType, U : ValidType>(
    private val searchExpression: UnaliasedExpression<T>,
    private val resultExpression: UnaliasedExpression<U>,
) {
    fun toDopeQuery(): DopeQuery {
        val searchExpressionDopeQuery = searchExpression.toDopeQuery()
        val resultExpressionDopeQuery = resultExpression.toDopeQuery()
        return DopeQuery(
            queryString = "${searchExpressionDopeQuery.queryString}, ${resultExpressionDopeQuery.queryString}",
            parameters = searchExpressionDopeQuery.parameters + resultExpressionDopeQuery.parameters,
        )
    }
}

fun <T : ValidType, U : ValidType> UnaliasedExpression<T>.resultsIn(resultExpression: UnaliasedExpression<U>) =
    SearchResultExpression(this, resultExpression)

fun UnaliasedExpression<out ValidType>.resultsIn(resultExpression: Number) =
    SearchResultExpression(this, resultExpression.toDopeType())

fun UnaliasedExpression<out ValidType>.resultsIn(resultExpression: String) =
    SearchResultExpression(this, resultExpression.toDopeType())

fun UnaliasedExpression<out ValidType>.resultsIn(resultExpression: Boolean) =
    SearchResultExpression(this, resultExpression.toDopeType())

fun Number.resultsIn(resultExpression: UnaliasedExpression<out ValidType>) =
    SearchResultExpression(this.toDopeType(), resultExpression)

fun String.resultsIn(resultExpression: UnaliasedExpression<out ValidType>) =
    SearchResultExpression(this.toDopeType(), resultExpression)

fun Boolean.resultsIn(resultExpression: UnaliasedExpression<out ValidType>) =
    SearchResultExpression(this.toDopeType(), resultExpression)

fun Number.resultsIn(resultExpression: Number) =
    SearchResultExpression(this.toDopeType(), resultExpression.toDopeType())

fun Number.resultsIn(resultExpression: String) =
    SearchResultExpression(this.toDopeType(), resultExpression.toDopeType())

fun Number.resultsIn(resultExpression: Boolean) =
    SearchResultExpression(this.toDopeType(), resultExpression.toDopeType())

fun String.resultsIn(resultExpression: Number) =
    SearchResultExpression(this.toDopeType(), resultExpression.toDopeType())

fun String.resultsIn(resultExpression: String) =
    SearchResultExpression(this.toDopeType(), resultExpression.toDopeType())

fun String.resultsIn(resultExpression: Boolean) =
    SearchResultExpression(this.toDopeType(), resultExpression.toDopeType())

fun Boolean.resultsIn(resultExpression: Number) =
    SearchResultExpression(this.toDopeType(), resultExpression.toDopeType())

fun Boolean.resultsIn(resultExpression: String) =
    SearchResultExpression(this.toDopeType(), resultExpression.toDopeType())

fun Boolean.resultsIn(resultExpression: Boolean) =
    SearchResultExpression(this.toDopeType(), resultExpression.toDopeType())
