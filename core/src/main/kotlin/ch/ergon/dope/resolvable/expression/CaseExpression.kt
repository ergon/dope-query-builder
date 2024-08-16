package ch.ergon.dope.resolvable.expression

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.ValidType

private const val CASE = "CASE"
private const val WHEN = "WHEN"
private const val THEN = "THEN"
private const val END = "END"
private const val ELSE = "ELSE"

sealed interface CaseExpression : Expression

sealed interface UnaliasedCaseExpression : CaseExpression {
    fun alias(alias: String) = AliasedCaseExpression(alias, this)
}

class AliasedCaseExpression(
    private val alias: String,
    private val unaliasedCaseExpression: UnaliasedCaseExpression,
) : CaseExpression {
    override fun toDopeQuery(): DopeQuery {
        val caseDopeQuery = unaliasedCaseExpression.toDopeQuery()

        val queryString = caseDopeQuery.queryString.trimEnd()
            .removeSuffix(END)
            .plus("AS `$alias` $END")

        return DopeQuery(queryString, caseDopeQuery.parameters)
    }
}

class CaseClass<T : ValidType>(val case: UnaliasedExpression<T>) : Resolvable {
    override fun toDopeQuery(): DopeQuery {
        val caseDopeQuery = case.toDopeQuery()
        return DopeQuery("$CASE ${caseDopeQuery.queryString}", caseDopeQuery.parameters)
    }
}

class SimpleCaseExpression<T : ValidType>(
    val case: UnaliasedExpression<T>,
    val firstWhenThen: Pair<TypeExpression<T>, UnaliasedExpression<out ValidType>>,
    vararg val additionalWhenThen: Pair<TypeExpression<T>, UnaliasedExpression<out ValidType>>,
) : UnaliasedCaseExpression {
    override fun toDopeQuery(): DopeQuery {
        val caseDopeQuery = case.toDopeQuery()
        val dopeQueries = (mapOf(firstWhenThen) + additionalWhenThen).map { it.key.toDopeQuery() to it.value.toDopeQuery() }
        return DopeQuery(
            queryString = "$CASE ${caseDopeQuery.queryString} ${dopeQueries.joinToString(" ")
            { "$WHEN ${it.first.queryString} $THEN ${it.second.queryString}" }
            } $END",
            parameters = emptyMap(),
        )
    }
}

class SimpleElseCaseExpression<T : ValidType>(
    private val case: UnaliasedExpression<T>,
    private val firstWhenThen: Pair<TypeExpression<T>, UnaliasedExpression<out ValidType>>,
    private vararg val additionalWhenThen: Pair<TypeExpression<T>, UnaliasedExpression<out ValidType>>,
    private val elseCase: UnaliasedExpression<out ValidType>,
) : UnaliasedCaseExpression {
    override fun toDopeQuery(): DopeQuery {
        val caseDopeQuery = case.toDopeQuery()
        val dopeQueries = (mapOf(firstWhenThen) + additionalWhenThen).map { it.key.toDopeQuery() to it.value.toDopeQuery() }
        val elseCaseDopeQuery = elseCase.toDopeQuery()
        return DopeQuery(
            queryString = "$CASE ${caseDopeQuery.queryString} ${dopeQueries.joinToString(" ")
            { "$WHEN ${it.first.queryString} $THEN ${it.second.queryString}" }
            }${elseCaseDopeQuery.let { " $ELSE ${elseCaseDopeQuery.queryString}" }
            } $END",
            parameters = emptyMap(),
        )
    }
}

fun <T : ValidType> case(case: UnaliasedExpression<T>) = CaseClass(case)

fun <T : ValidType> CaseClass<T>.`when`(whenCondition: TypeExpression<T>, thenExpression: UnaliasedExpression<out ValidType>) =
    SimpleCaseExpression(case, whenCondition to thenExpression)

fun <T : ValidType> SimpleCaseExpression<T>.`when`(whenExpression: TypeExpression<T>, thenExpression: UnaliasedExpression<out ValidType>) =
    SimpleCaseExpression(case, firstWhenThen, *additionalWhenThen, whenExpression to thenExpression)

fun <T : ValidType> SimpleCaseExpression<T>.`else`(elseCase: UnaliasedExpression<out ValidType>) =
    SimpleElseCaseExpression(case, firstWhenThen, *additionalWhenThen, elseCase = elseCase)

class SearchedCaseExpression(
    val firstWhenThen: Pair<TypeExpression<BooleanType>, UnaliasedExpression<out ValidType>>,
    vararg val additionalWhenThen: Pair<TypeExpression<BooleanType>, UnaliasedExpression<out ValidType>>,
) : UnaliasedCaseExpression {
    override fun toDopeQuery(): DopeQuery {
        val dopeQueries = (mapOf(firstWhenThen) + additionalWhenThen).map { it.key.toDopeQuery() to it.value.toDopeQuery() }
        return DopeQuery(
            queryString = "$CASE ${dopeQueries.joinToString(" ")
            { "$WHEN ${it.first.queryString} $THEN ${it.second.queryString}" }
            } $END",
            parameters = dopeQueries.fold(emptyMap()) { parameters, it -> parameters + it.first.parameters + it.second.parameters },
        )
    }
}

class SearchedElseCaseExpression(
    private val firstWhenThen: Pair<TypeExpression<BooleanType>, UnaliasedExpression<out ValidType>>,
    private vararg val additionalWhenThen: Pair<TypeExpression<BooleanType>, UnaliasedExpression<out ValidType>>,
    private val elseCase: UnaliasedExpression<out ValidType>,
) : UnaliasedCaseExpression {
    override fun toDopeQuery(): DopeQuery {
        val dopeQueries = (mapOf(firstWhenThen) + additionalWhenThen).map { it.key.toDopeQuery() to it.value.toDopeQuery() }
        val elseCaseDopeQuery = elseCase.toDopeQuery()
        return DopeQuery(
            queryString = "$CASE ${dopeQueries.joinToString(" ")
            { "$WHEN ${it.first.queryString} $THEN ${it.second.queryString}" }
            }${elseCaseDopeQuery.let { " $ELSE ${elseCaseDopeQuery.queryString}" }
            } $END",
            parameters = dopeQueries.fold(emptyMap<String, Any>()) { parameters, it ->
                parameters + it.first.parameters + it.second.parameters
            } + elseCaseDopeQuery.parameters,
        )
    }
}

fun `when`(whenCondition: TypeExpression<BooleanType>, thenExpression: UnaliasedExpression<out ValidType>) =
    SearchedCaseExpression(whenCondition to thenExpression)

fun SearchedCaseExpression.`when`(whenCondition: TypeExpression<BooleanType>, thenExpression: UnaliasedExpression<out ValidType>) =
    SearchedCaseExpression(firstWhenThen, *additionalWhenThen, whenCondition to thenExpression)

fun SearchedCaseExpression.`else`(elseCase: UnaliasedExpression<out ValidType>) =
    SearchedElseCaseExpression(firstWhenThen, *additionalWhenThen, elseCase = elseCase)
