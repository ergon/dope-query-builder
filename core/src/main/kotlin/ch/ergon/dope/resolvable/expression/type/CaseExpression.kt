package ch.ergon.dope.resolvable.expression.type

import ch.ergon.dope.DopeParameters
import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.type.function.conditional.SearchResult
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

private const val CASE = "CASE"
private const val WHEN = "WHEN"
private const val THEN = "THEN"
private const val ELSE = "ELSE"
private const val END = "END"

class CaseClass<T : ValidType>(private val case: TypeExpression<T>? = null) : Resolvable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val caseDopeQuery = case?.toDopeQuery(manager)
        return DopeQuery(
            queryString = "$CASE${caseDopeQuery?.queryString?.let { " $it" }.orEmpty()}",
            parameters = caseDopeQuery?.parameters ?: DopeParameters(),
        )
    }
}

class CaseExpression<T : ValidType, U : ValidType>(
    val case: CaseClass<T>,
    val firstSearchResult: SearchResult<T, out U>,
    vararg val additionalSearchResult: SearchResult<T, out U>,
) : TypeExpression<U> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val caseDopeQuery = case.toDopeQuery(manager)
        val conditionDopeQueries = listOf(firstSearchResult, *additionalSearchResult).map {
            it.searchExpression.toDopeQuery(manager) to it.resultExpression.toDopeQuery(manager)
        }
        return DopeQuery(
            queryString = caseDopeQuery.queryString +
                conditionDopeQueries.joinToString(separator = " ", prefix = " ", postfix = " ") {
                    "$WHEN ${it.first.queryString} $THEN ${it.second.queryString}"
                } + END,
            parameters = caseDopeQuery.parameters.merge(
                *conditionDopeQueries.map { it.first.parameters.merge(it.second.parameters) }.toTypedArray(),
            ),
        )
    }
}

class ElseCaseExpression<T : ValidType, U : ValidType>(
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
                conditionDopeQueries.joinToString(separator = " ", prefix = " ", postfix = " ") {
                    "$WHEN ${it.first.queryString} $THEN ${it.second.queryString}"
                } + "$ELSE ${elseCaseDopeQuery.queryString} " +
                END,
            parameters = caseDopeQuery.parameters.merge(
                *conditionDopeQueries.map { it.first.parameters.merge(it.second.parameters) }.toTypedArray(),
            ),
        )
    }
}

fun case() = CaseClass<BooleanType>()

fun <T : ValidType> case(case: TypeExpression<T>) = CaseClass(case)

@JvmName("simpleCaseCondition")
fun <T : ValidType, U : ValidType> CaseClass<T>.condition(conditionExpression: SearchResult<T, U>) =
    CaseExpression(this, conditionExpression)

@JvmName("simpleCaseConditionWithGeneric")
fun <T : ValidType, U : ValidType> CaseExpression<T, U>.condition(conditionExpression: SearchResult<T, U>) =
    CaseExpression(case, firstSearchResult, *additionalSearchResult, conditionExpression)

@JvmName("simpleCaseConditionWithOutGeneric")
fun <T : ValidType> CaseExpression<T, out ValidType>.condition(conditionExpression: SearchResult<T, out ValidType>) =
    CaseExpression(case, firstSearchResult, *additionalSearchResult, conditionExpression)

@JvmName("simpleCaseOtherwiseWithGeneric")
fun <T : ValidType, U : ValidType> CaseExpression<T, U>.otherwise(elseCase: TypeExpression<U>) =
    ElseCaseExpression(case, firstSearchResult, *additionalSearchResult, elseCase = elseCase)

@JvmName("simpleCaseOtherwiseWithOutGeneric")
fun <T : ValidType> CaseExpression<T, out ValidType>.otherwise(elseCase: TypeExpression<out ValidType>) =
    ElseCaseExpression(case, firstSearchResult, *additionalSearchResult, elseCase = elseCase)

@JvmName("searchedCaseCondition")
fun <U : ValidType> CaseClass<BooleanType>.condition(conditicondition: SearchResult<BooleanType, U>) =
    CaseExpression(this, conditicondition)

@JvmName("searchedCaseConditionWithGeneric")
fun <U : ValidType> CaseExpression<BooleanType, U>.condition(conditicondition: SearchResult<BooleanType, U>) =
    CaseExpression(case, firstSearchResult, *additionalSearchResult, conditicondition)

@JvmName("searchedCaseConditionWithOutGeneric")
fun CaseExpression<BooleanType, out ValidType>.condition(conditicondition: SearchResult<BooleanType, out ValidType>) =
    CaseExpression(case, firstSearchResult, *additionalSearchResult, conditicondition)

@JvmName("searchedCaseOtherwiseWithGeneric")
fun <U : ValidType> CaseExpression<BooleanType, U>.otherwise(elseCase: TypeExpression<U>) =
    ElseCaseExpression(case, firstSearchResult, *additionalSearchResult, elseCase = elseCase)

@JvmName("searchedCaseOtherwiseWithOutGeneric")
fun CaseExpression<BooleanType, out ValidType>.otherwise(elseCase: TypeExpression<out ValidType>) =
    ElseCaseExpression(case, firstSearchResult, *additionalSearchResult, elseCase = elseCase)
