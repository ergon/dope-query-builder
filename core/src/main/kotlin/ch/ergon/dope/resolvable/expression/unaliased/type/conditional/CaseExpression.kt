package ch.ergon.dope.resolvable.expression.unaliased.type.conditional

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.UnaliasedExpression
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

private const val CASE = "CASE"
private const val WHEN = "WHEN"
private const val THEN = "THEN"
private const val END = "END"
private const val ELSE = "ELSE"

class SimpleCaseClass<T : ValidType>(private val case: TypeExpression<T>) : Resolvable {
    override fun toDopeQuery(): DopeQuery {
        val caseDopeQuery = case.toDopeQuery()
        return DopeQuery("$CASE ${caseDopeQuery.queryString}", caseDopeQuery.parameters)
    }
}

class SimpleCaseExpression<T : ValidType, U : ValidType>(
    val case: SimpleCaseClass<T>,
    val firstSearchResult: SearchResult<T, out U>,
    vararg val additionalSearchResult: SearchResult<T, out U>,
) : TypeExpression<U> {
    override fun toDopeQuery(): DopeQuery {
        val caseDopeQuery = case.toDopeQuery()
        val whenThenDopeQueries =
            listOf(firstSearchResult, *additionalSearchResult).map { it.searchExpression.toDopeQuery() to it.resultExpression.toDopeQuery() }
        return DopeQuery(
            queryString = caseDopeQuery.queryString +
                whenThenDopeQueries.joinToString(" ", " ", " ") { "$WHEN ${it.first.queryString} $THEN ${it.second.queryString}" } +
                END,
            parameters = caseDopeQuery.parameters + whenThenDopeQueries.fold(emptyMap()) { parameters, query ->
                parameters + query.first.parameters + query.second.parameters
            },
        )
    }
}

class SimpleElseCaseExpression<T : ValidType, U : ValidType>(
    private val case: SimpleCaseClass<T>,
    private val firstSearchResult: SearchResult<T, out U>,
    private vararg val additionalSearchResult: SearchResult<T, out U>,
    private val elseCase: TypeExpression<out U>,
) : TypeExpression<U> {
    override fun toDopeQuery(): DopeQuery {
        val caseDopeQuery = case.toDopeQuery()
        val whenThenDopeQueries =
            listOf(firstSearchResult, *additionalSearchResult).map { it.searchExpression.toDopeQuery() to it.resultExpression.toDopeQuery() }
        val elseCaseDopeQuery = elseCase.toDopeQuery()
        return DopeQuery(
            queryString = caseDopeQuery.queryString +
                whenThenDopeQueries.joinToString(" ", " ", " ") { "$WHEN ${it.first.queryString} $THEN ${it.second.queryString}" } +
                "$ELSE ${elseCaseDopeQuery.queryString} " +
                END,
            parameters = caseDopeQuery.parameters + whenThenDopeQueries.fold(emptyMap()) { parameters, query ->
                parameters + query.first.parameters + query.second.parameters
            } + elseCaseDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> case(case: TypeExpression<T>) = SimpleCaseClass(case)

fun <T : ValidType, U : ValidType> SimpleCaseClass<T>.whenThen(whenThenExpression: SearchResult<T, U>) =
    SimpleCaseExpression(this, whenThenExpression)

@JvmName("whenThenWithGeneric")
fun <T : ValidType, U : ValidType> SimpleCaseExpression<T, U>.whenThen(whenThenExpression: SearchResult<T, U>) =
    SimpleCaseExpression(case, firstSearchResult, *additionalSearchResult, whenThenExpression)

@JvmName("whenThenWithoutGeneric")
fun <T : ValidType> SimpleCaseExpression<T, out ValidType>.whenThen(whenThenExpression: SearchResult<T, out ValidType>) =
    SimpleCaseExpression(case, firstSearchResult, *additionalSearchResult, whenThenExpression)

@JvmName("otherwiseWithGeneric")
fun <T : ValidType, U : ValidType> SimpleCaseExpression<T, U>.otherwise(elseCase: TypeExpression<U>) =
    SimpleElseCaseExpression(case, firstSearchResult, *additionalSearchResult, elseCase = elseCase)

@JvmName("otherwiseWithoutGeneric")
fun <T : ValidType> SimpleCaseExpression<T, out ValidType>.otherwise(elseCase: TypeExpression<out ValidType>) =
    SimpleElseCaseExpression(case, firstSearchResult, *additionalSearchResult, elseCase = elseCase)

class SearchedCaseExpression<U : ValidType>(
    val case: SearchedCaseClass,
    val firstSearchResult: SearchResult<BooleanType, out U>,
    vararg val additionalSearchResult: SearchResult<BooleanType, out U>,
) : TypeExpression<U> {
    override fun toDopeQuery(): DopeQuery {
        val caseDopeQuery = case.toDopeQuery()
        val whenThenDopeQueries =
            listOf(firstSearchResult, *additionalSearchResult).map { it.searchExpression.toDopeQuery() to it.resultExpression.toDopeQuery() }
        return DopeQuery(
            queryString = caseDopeQuery.queryString +
                whenThenDopeQueries.joinToString(" ", " ", " ") { "$WHEN ${it.first.queryString} $THEN ${it.second.queryString}" } +
                END,
            parameters = whenThenDopeQueries.fold(emptyMap()) { parameters, query ->
                parameters + query.first.parameters + query.second.parameters
            },
        )
    }
}

class SearchedCaseClass : Resolvable {
    override fun toDopeQuery() = DopeQuery(queryString = CASE, parameters = emptyMap())
}

class SearchedElseCaseExpression<U : ValidType>(
    private val case: SearchedCaseClass,
    private val firstSearchResult: SearchResult<BooleanType, out U>,
    private vararg val additionalSearchResult: SearchResult<BooleanType, out U>,
    private val elseCase: UnaliasedExpression<out U>,
) : TypeExpression<U> {
    override fun toDopeQuery(): DopeQuery {
        val caseDopeQuery = case.toDopeQuery()
        val whenThenDopeQueries =
            listOf(firstSearchResult, *additionalSearchResult).map { it.searchExpression.toDopeQuery() to it.resultExpression.toDopeQuery() }
        val elseCaseDopeQuery = elseCase.toDopeQuery()
        return DopeQuery(
            queryString = caseDopeQuery.queryString +
                whenThenDopeQueries.joinToString(" ", " ", " ") { "$WHEN ${it.first.queryString} $THEN ${it.second.queryString}" } +
                "$ELSE ${elseCaseDopeQuery.queryString} " +
                END,
            parameters = whenThenDopeQueries.fold(emptyMap<String, Any>()) { parameters, query ->
                parameters + query.first.parameters + query.second.parameters
            } + elseCaseDopeQuery.parameters,
        )
    }
}

fun case() = SearchedCaseClass()

fun <U : ValidType> SearchedCaseClass.whenThen(whenThenCondition: SearchResult<BooleanType, U>) =
    SearchedCaseExpression(this, whenThenCondition)

@JvmName("whenThenWithGeneric")
fun <U : ValidType> SearchedCaseExpression<U>.whenThen(whenThenCondition: SearchResult<BooleanType, U>) =
    SearchedCaseExpression(case, firstSearchResult, *additionalSearchResult, whenThenCondition)

@JvmName("whenThenWithoutGeneric")
fun SearchedCaseExpression<out ValidType>.whenThen(whenThenCondition: SearchResult<BooleanType, out ValidType>) =
    SearchedCaseExpression(case, firstSearchResult, *additionalSearchResult, whenThenCondition)

@JvmName("otherwiseWithGeneric")
fun <U : ValidType> SearchedCaseExpression<U>.otherwise(elseCase: TypeExpression<U>) =
    SearchedElseCaseExpression(case, firstSearchResult, *additionalSearchResult, elseCase = elseCase)

@JvmName("otherwiseWithoutGeneric")
fun SearchedCaseExpression<out ValidType>.otherwise(elseCase: TypeExpression<out ValidType>) =
    SearchedElseCaseExpression(case, firstSearchResult, *additionalSearchResult, elseCase = elseCase)
