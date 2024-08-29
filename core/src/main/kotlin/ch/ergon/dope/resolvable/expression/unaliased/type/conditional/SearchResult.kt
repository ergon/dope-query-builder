package ch.ergon.dope.resolvable.expression.unaliased.type.conditional

import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.ValidType

data class SearchResult<T : ValidType, U : ValidType>(
    val searchExpression: UnaliasedExpression<T>,
    val resultExpression: UnaliasedExpression<U>,
)

fun <T : ValidType, U : ValidType> UnaliasedExpression<T>.resultsIn(resultExpression: UnaliasedExpression<U>) =
    SearchResult(this, resultExpression)

fun <T : ValidType> UnaliasedExpression<T>.resultsIn(resultExpression: Number) =
    SearchResult(this, resultExpression.toDopeType())

fun <T : ValidType> UnaliasedExpression<T>.resultsIn(resultExpression: String) =
    SearchResult(this, resultExpression.toDopeType())

fun <T : ValidType> UnaliasedExpression<T>.resultsIn(resultExpression: Boolean) =
    SearchResult(this, resultExpression.toDopeType())

fun <T : ValidType> Number.resultsIn(resultExpression: UnaliasedExpression<T>) =
    SearchResult(toDopeType(), resultExpression)

fun <T : ValidType> String.resultsIn(resultExpression: UnaliasedExpression<T>) =
    SearchResult(toDopeType(), resultExpression)

fun <T : ValidType> Boolean.resultsIn(resultExpression: UnaliasedExpression<T>) =
    SearchResult(toDopeType(), resultExpression)

fun Number.resultsIn(resultExpression: Number) =
    SearchResult(toDopeType(), resultExpression.toDopeType())

fun Number.resultsIn(resultExpression: String) =
    SearchResult(toDopeType(), resultExpression.toDopeType())

fun Number.resultsIn(resultExpression: Boolean) =
    SearchResult(toDopeType(), resultExpression.toDopeType())

fun String.resultsIn(resultExpression: Number) =
    SearchResult(toDopeType(), resultExpression.toDopeType())

fun String.resultsIn(resultExpression: String) =
    SearchResult(toDopeType(), resultExpression.toDopeType())

fun String.resultsIn(resultExpression: Boolean) =
    SearchResult(toDopeType(), resultExpression.toDopeType())

fun Boolean.resultsIn(resultExpression: Number) =
    SearchResult(toDopeType(), resultExpression.toDopeType())

fun Boolean.resultsIn(resultExpression: String) =
    SearchResult(toDopeType(), resultExpression.toDopeType())

fun Boolean.resultsIn(resultExpression: Boolean) =
    SearchResult(toDopeType(), resultExpression.toDopeType())
