package ch.ergon.dope.resolvable.expression.type

import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.expression.type.function.conditional.SearchResult
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

data class CaseClass<T : ValidType>(val case: TypeExpression<T>? = null) : Resolvable

data class CaseExpression<T : ValidType, U : ValidType>(
    val case: CaseClass<T>,
    val firstSearchResult: SearchResult<T, out U>,
    val additionalSearchResults: List<SearchResult<T, out U>> = emptyList(),
) : TypeExpression<U>

data class ElseCaseExpression<T : ValidType, U : ValidType>(
    val case: CaseClass<T>,
    val firstSearchResult: SearchResult<T, out U>,
    val additionalSearchResults: List<SearchResult<T, out U>> = emptyList(),
    val elseCase: TypeExpression<out U>,
) : TypeExpression<U>

fun case() = CaseClass<BooleanType>()

fun <T : ValidType> case(case: TypeExpression<T>) = CaseClass(case)

@JvmName("simpleCaseCondition")
fun <T : ValidType, U : ValidType> CaseClass<T>.condition(conditionExpression: SearchResult<T, U>) =
    CaseExpression(this, conditionExpression)

@JvmName("simpleCaseConditionWithGeneric")
fun <T : ValidType, U : ValidType> CaseExpression<T, U>.condition(conditionExpression: SearchResult<T, U>) =
    CaseExpression(case, firstSearchResult, listOf(*additionalSearchResults.toTypedArray(), conditionExpression))

@JvmName("simpleCaseConditionWithOutGeneric")
fun <T : ValidType> CaseExpression<T, out ValidType>.condition(conditionExpression: SearchResult<T, out ValidType>) =
    CaseExpression(case, firstSearchResult, listOf(*additionalSearchResults.toTypedArray(), conditionExpression))

@JvmName("simpleCaseOtherwiseWithGeneric")
fun <T : ValidType, U : ValidType> CaseExpression<T, U>.otherwise(elseCase: TypeExpression<U>) =
    ElseCaseExpression(case, firstSearchResult, additionalSearchResults, elseCase = elseCase)

@JvmName("simpleCaseOtherwiseWithOutGeneric")
fun <T : ValidType> CaseExpression<T, out ValidType>.otherwise(elseCase: TypeExpression<out ValidType>) =
    ElseCaseExpression(case, firstSearchResult, additionalSearchResults, elseCase = elseCase)

@JvmName("searchedCaseCondition")
fun <U : ValidType> CaseClass<BooleanType>.condition(condition: SearchResult<BooleanType, U>) =
    CaseExpression(this, condition)

@JvmName("searchedCaseConditionWithGeneric")
fun <U : ValidType> CaseExpression<BooleanType, U>.condition(condition: SearchResult<BooleanType, U>) =
    CaseExpression(case, firstSearchResult, listOf(*additionalSearchResults.toTypedArray(), condition))

@JvmName("searchedCaseConditionWithOutGeneric")
fun CaseExpression<BooleanType, out ValidType>.condition(condition: SearchResult<BooleanType, out ValidType>) =
    CaseExpression(case, firstSearchResult, listOf(*additionalSearchResults.toTypedArray(), condition))

@JvmName("searchedCaseOtherwiseWithGeneric")
fun <U : ValidType> CaseExpression<BooleanType, U>.otherwise(elseCase: TypeExpression<U>) =
    ElseCaseExpression(case, firstSearchResult, additionalSearchResults, elseCase = elseCase)

@JvmName("searchedCaseOtherwiseWithOutGeneric")
fun CaseExpression<BooleanType, out ValidType>.otherwise(elseCase: TypeExpression<out ValidType>) =
    ElseCaseExpression(case, firstSearchResult, additionalSearchResults, elseCase = elseCase)
