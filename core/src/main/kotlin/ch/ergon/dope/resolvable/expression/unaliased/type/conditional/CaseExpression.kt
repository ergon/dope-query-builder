package ch.ergon.dope.resolvable.expression.unaliased.type.conditional

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.SearchResult
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

private const val CASE = "CASE"
private const val WHEN = "WHEN"
private const val THEN = "THEN"
private const val END = "END"
private const val ELSE = "ELSE"

class CaseClass<T : ValidType>(private val case: TypeExpression<T>? = null) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val caseDopeQuery = case?.toDopeQuery(manager)
        return DopeQuery("$CASE${caseDopeQuery?.queryString?.let { " $it" }.orEmpty()}", caseDopeQuery?.parameters.orEmpty())
    }
}

open class SimpleCaseExpression<T : ValidType, U : ValidType>(
    val case: CaseClass<T>,
    val firstSearchResult: SearchResult<T, out U>,
    open vararg val additionalSearchResult: SearchResult<T, out U>,
) : TypeExpression<U> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val caseDopeQuery = case.toDopeQuery(manager)
        val conditionDopeQueries = listOf(firstSearchResult, *additionalSearchResult).map {
            it.searchExpression.toDopeQuery(manager) to it.resultExpression.toDopeQuery(manager)
        }
        return DopeQuery(
            queryString = caseDopeQuery.queryString +
                conditionDopeQueries.joinToString(" ", " ", " ") { "$WHEN ${it.first.queryString} $THEN ${it.second.queryString}" } +
                END,
            parameters = caseDopeQuery.parameters + conditionDopeQueries.fold(emptyMap()) { parameters, query ->
                parameters + query.first.parameters + query.second.parameters
            },
        )
    }
}

open class SimpleElseCaseExpression<T : ValidType, U : ValidType>(
    private val case: CaseClass<T>,
    private val firstSearchResult: SearchResult<T, out U>,
    private vararg val additionalSearchResult: SearchResult<T, out U>,
    private val elseCase: TypeExpression<out U>,
) : TypeExpression<U> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val caseDopeQuery = case.toDopeQuery(manager)
        val conditionDopeQueries = listOf(firstSearchResult, *additionalSearchResult).map {
            it.searchExpression.toDopeQuery(manager) to it.resultExpression.toDopeQuery(manager)
        }
        val elseCaseDopeQuery = elseCase.toDopeQuery(manager)
        return DopeQuery(
            queryString = caseDopeQuery.queryString +
                conditionDopeQueries.joinToString(" ", " ", " ") { "$WHEN ${it.first.queryString} $THEN ${it.second.queryString}" } +
                "$ELSE ${elseCaseDopeQuery.queryString} " +
                END,
            parameters = caseDopeQuery.parameters + conditionDopeQueries.fold(emptyMap()) { parameters, query ->
                parameters + query.first.parameters + query.second.parameters
            } + elseCaseDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> case(case: TypeExpression<T>) = CaseClass(case)

fun <T : ValidType, U : ValidType> CaseClass<T>.condition(conditionExpression: SearchResult<T, U>) =
    SimpleCaseExpression(this, conditionExpression)

@JvmName("conditionWithGeneric")
fun <T : ValidType, U : ValidType> SimpleCaseExpression<T, U>.condition(conditionExpression: SearchResult<T, U>) =
    SimpleCaseExpression(case, firstSearchResult, *additionalSearchResult, conditionExpression)

@JvmName("conditionWithoutGeneric")
fun <T : ValidType> SimpleCaseExpression<T, out ValidType>.condition(conditionExpression: SearchResult<T, out ValidType>) =
    SimpleCaseExpression(case, firstSearchResult, *additionalSearchResult, conditionExpression)

@JvmName("otherwiseWithGeneric")
fun <T : ValidType, U : ValidType> SimpleCaseExpression<T, U>.otherwise(elseCase: TypeExpression<U>) =
    SimpleElseCaseExpression(case, firstSearchResult, *additionalSearchResult, elseCase = elseCase)

@JvmName("otherwiseWithoutGeneric")
fun <T : ValidType> SimpleCaseExpression<T, out ValidType>.otherwise(elseCase: TypeExpression<out ValidType>) =
    SimpleElseCaseExpression(case, firstSearchResult, *additionalSearchResult, elseCase = elseCase)

class SearchedCaseExpression<U : ValidType>(
    case: CaseClass<BooleanType>,
    firstSearchResult: SearchResult<BooleanType, out U>,
    vararg additionalSearchResult: SearchResult<BooleanType, out U>,
) : SimpleCaseExpression<BooleanType, U>(case, firstSearchResult, *additionalSearchResult)

class SearchedElseCaseExpression<U : ValidType>(
    case: CaseClass<BooleanType>,
    firstSearchResult: SearchResult<BooleanType, out U>,
    vararg additionalSearchResult: SearchResult<BooleanType, out U>,
    elseCase: TypeExpression<out U>,
) : SimpleElseCaseExpression<BooleanType, U>(case, firstSearchResult, *additionalSearchResult, elseCase = elseCase)

fun case() = CaseClass<BooleanType>()

fun <U : ValidType> CaseClass<BooleanType>.condition(conditionCondition: SearchResult<BooleanType, U>) =
    SearchedCaseExpression(this, conditionCondition)

@JvmName("conditionWithGeneric")
fun <U : ValidType> SearchedCaseExpression<U>.condition(conditionCondition: SearchResult<BooleanType, U>) =
    SearchedCaseExpression(case, firstSearchResult, *additionalSearchResult, conditionCondition)

@JvmName("conditionWithOutGeneric")
fun SearchedCaseExpression<out ValidType>.condition(conditionCondition: SearchResult<BooleanType, out ValidType>) =
    SearchedCaseExpression(case, firstSearchResult, *additionalSearchResult, conditionCondition)

@JvmName("otherwiseWithGeneric")
fun <U : ValidType> SearchedCaseExpression<U>.otherwise(elseCase: TypeExpression<U>) =
    SearchedElseCaseExpression(case, firstSearchResult, *additionalSearchResult, elseCase = elseCase)

@JvmName("otherwiseWithOutGeneric")
fun SearchedCaseExpression<out ValidType>.otherwise(elseCase: TypeExpression<out ValidType>) =
    SearchedElseCaseExpression(case, firstSearchResult, *additionalSearchResult, elseCase = elseCase)
