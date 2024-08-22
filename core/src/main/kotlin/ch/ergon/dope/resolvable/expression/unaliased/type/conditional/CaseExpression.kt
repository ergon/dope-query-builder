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

class CaseClass<T : ValidType>(val case: TypeExpression<T>) : Resolvable {
    override fun toDopeQuery(): DopeQuery {
        val caseDopeQuery = case.toDopeQuery()
        return DopeQuery("$CASE ${caseDopeQuery.queryString}", caseDopeQuery.parameters)
    }
}

class SimpleCaseExpression<T : ValidType, U : ValidType>(
    val case: TypeExpression<T>,
    val firstWhenThen: SearchResult<T, out U>,
    vararg val additionalWhenThen: SearchResult<T, out U>,
) : TypeExpression<U> {
    override fun toDopeQuery(): DopeQuery {
        val caseDopeQuery = case.toDopeQuery()
        val whenThenDopeQueries =
            listOf(firstWhenThen, *additionalWhenThen).map { it.searchExpression.toDopeQuery() to it.resultExpression.toDopeQuery() }
        return DopeQuery(
            queryString = "$CASE ${caseDopeQuery.queryString} " +
                "${whenThenDopeQueries.joinToString(" ") { "$WHEN ${it.first.queryString} $THEN ${it.second.queryString}" }} " +
                END,
            parameters = caseDopeQuery.parameters + whenThenDopeQueries.fold(emptyMap()) { parameters, query ->
                parameters + query.first.parameters + query.second.parameters
            },
        )
    }
}

class SimpleElseCaseExpression<T : ValidType, U : ValidType>(
    private val case: TypeExpression<T>,
    private val firstWhenThen: SearchResult<T, out U>,
    private vararg val additionalWhenThen: SearchResult<T, out U>,
    private val elseCase: TypeExpression<out U>,
) : TypeExpression<U> {
    override fun toDopeQuery(): DopeQuery {
        val caseDopeQuery = case.toDopeQuery()
        val whenThenDopeQueries =
            listOf(firstWhenThen, *additionalWhenThen).map { it.searchExpression.toDopeQuery() to it.resultExpression.toDopeQuery() }
        val elseCaseDopeQuery = elseCase.toDopeQuery()
        return DopeQuery(
            queryString = "$CASE ${caseDopeQuery.queryString} " +
                whenThenDopeQueries.joinToString(" ") { "$WHEN ${it.first.queryString} $THEN ${it.second.queryString}" } +
                "${elseCaseDopeQuery.let { " $ELSE ${elseCaseDopeQuery.queryString}" }} " +
                END,
            parameters = caseDopeQuery.parameters + whenThenDopeQueries.fold(emptyMap()) { parameters, query ->
                parameters + query.first.parameters + query.second.parameters
            } + elseCaseDopeQuery.parameters,
        )
    }
}

fun <T : ValidType> case(case: TypeExpression<T>) = CaseClass(case)

fun <T : ValidType, U : ValidType> CaseClass<T>.whenThen(whenThenExpression: SearchResult<T, U>) =
    SimpleCaseExpression(case, whenThenExpression)

@JvmName("whenThenWithGeneric")
fun <T : ValidType, U : ValidType> SimpleCaseExpression<T, U>.whenThen(whenThenExpression: SearchResult<T, U>) =
    SimpleCaseExpression(case, firstWhenThen, *additionalWhenThen, whenThenExpression)

@JvmName("whenThenWithoutGeneric")
fun <T : ValidType> SimpleCaseExpression<T, out ValidType>.whenThen(whenThenExpression: SearchResult<T, out ValidType>) =
    SimpleCaseExpression(case, firstWhenThen, *additionalWhenThen, whenThenExpression)

@JvmName("otherwiseWithGeneric")
fun <T : ValidType, U : ValidType> SimpleCaseExpression<T, U>.otherwise(elseCase: TypeExpression<U>) =
    SimpleElseCaseExpression(case, firstWhenThen, *additionalWhenThen, elseCase = elseCase)

@JvmName("otherwiseWithoutGeneric")
fun <T : ValidType> SimpleCaseExpression<T, out ValidType>.otherwise(elseCase: TypeExpression<out ValidType>) =
    SimpleElseCaseExpression(case, firstWhenThen, *additionalWhenThen, elseCase = elseCase)

class SearchedCaseExpression<U : ValidType>(
    val firstWhenThen: SearchResult<BooleanType, out U>,
    vararg val additionalWhenThen: SearchResult<BooleanType, out U>,
) : TypeExpression<U> {
    override fun toDopeQuery(): DopeQuery {
        val whenThenDopeQueries =
            listOf(firstWhenThen, *additionalWhenThen).map { it.searchExpression.toDopeQuery() to it.resultExpression.toDopeQuery() }
        return DopeQuery(
            queryString = "$CASE " +
                "${whenThenDopeQueries.joinToString(" ") { "$WHEN ${it.first.queryString} $THEN ${it.second.queryString}" }} " +
                END,
            parameters = whenThenDopeQueries.fold(emptyMap()) { parameters, query ->
                parameters + query.first.parameters + query.second.parameters
            },
        )
    }
}

class SearchedElseCaseExpression<U : ValidType>(
    private val firstWhenThen: SearchResult<BooleanType, out U>,
    private vararg val additionalWhenThen: SearchResult<BooleanType, out U>,
    private val elseCase: UnaliasedExpression<out U>,
) : TypeExpression<U> {
    override fun toDopeQuery(): DopeQuery {
        val elseCaseDopeQuery = elseCase.toDopeQuery()
        val whenThenDopeQueries =
            listOf(firstWhenThen, *additionalWhenThen).map { it.searchExpression.toDopeQuery() to it.resultExpression.toDopeQuery() }
        return DopeQuery(
            queryString = "$CASE " +
                "${whenThenDopeQueries.joinToString(" ") { "$WHEN ${it.first.queryString} $THEN ${it.second.queryString}" }} " +
                "$ELSE ${elseCaseDopeQuery.queryString} " +
                END,
            parameters = whenThenDopeQueries.fold(emptyMap<String, Any>()) { parameters, query ->
                parameters + query.first.parameters + query.second.parameters
            } + elseCaseDopeQuery.parameters,
        )
    }
}

fun <U : ValidType> whenThen(whenThenCondition: SearchResult<BooleanType, U>) =
    SearchedCaseExpression(whenThenCondition)

@JvmName("whenThenWithGeneric")
fun <U : ValidType> SearchedCaseExpression<U>.whenThen(whenThenCondition: SearchResult<BooleanType, U>) =
    SearchedCaseExpression(firstWhenThen, *additionalWhenThen, whenThenCondition)

@JvmName("whenThenWithoutGeneric")
fun SearchedCaseExpression<out ValidType>.whenThen(whenThenCondition: SearchResult<BooleanType, out ValidType>) =
    SearchedCaseExpression(firstWhenThen, *additionalWhenThen, whenThenCondition)

@JvmName("otherwiseWithGeneric")
fun <U : ValidType> SearchedCaseExpression<U>.otherwise(elseCase: TypeExpression<U>) =
    SearchedElseCaseExpression(firstWhenThen, *additionalWhenThen, elseCase = elseCase)

@JvmName("otherwiseWithoutGeneric")
fun SearchedCaseExpression<out ValidType>.otherwise(elseCase: TypeExpression<out ValidType>) =
    SearchedElseCaseExpression(firstWhenThen, *additionalWhenThen, elseCase = elseCase)
