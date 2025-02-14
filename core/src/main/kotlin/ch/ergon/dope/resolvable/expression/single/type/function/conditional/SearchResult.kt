package ch.ergon.dope.resolvable.expression.single.type.function.conditional

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.ValidType

data class SearchResult<T : ValidType, U : ValidType>(
    val searchExpression: TypeExpression<T>,
    val resultExpression: TypeExpression<U>,
)

fun <T : ValidType, U : ValidType> TypeExpression<T>.resultsIn(resultExpression: TypeExpression<U>) =
    SearchResult(this, resultExpression)

fun <T : ValidType> TypeExpression<T>.resultsIn(resultExpression: Number) =
    SearchResult(this, resultExpression.toDopeType())

fun <T : ValidType> TypeExpression<T>.resultsIn(resultExpression: String) =
    SearchResult(this, resultExpression.toDopeType())

fun <T : ValidType> TypeExpression<T>.resultsIn(resultExpression: Boolean) =
    SearchResult(this, resultExpression.toDopeType())

fun <T : ValidType> Number.resultsIn(resultExpression: TypeExpression<T>) =
    SearchResult(toDopeType(), resultExpression)

fun <T : ValidType> String.resultsIn(resultExpression: TypeExpression<T>) =
    SearchResult(toDopeType(), resultExpression)

fun <T : ValidType> Boolean.resultsIn(resultExpression: TypeExpression<T>) =
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
