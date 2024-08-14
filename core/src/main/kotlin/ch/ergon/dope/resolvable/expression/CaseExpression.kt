package ch.ergon.dope.resolvable.expression

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.WhenThenCondition
import ch.ergon.dope.validtype.ValidType

sealed class CaseExpression(
    private val unaliasedExpression: UnaliasedExpression<out ValidType>? = null,
    private val whenThenCondition: WhenThenCondition,
    private vararg val additionalWhenThenConditions: WhenThenCondition,
    private val elseCase: UnaliasedExpression<out ValidType>? = null,
) : Expression {
    internal fun getCaseExpressionDopeQuery(alias: String? = null): DopeQuery {
        val unaliasedExpressionDopeQuery = unaliasedExpression?.toDopeQuery()
        val whenThenConditionDopeQuery = whenThenCondition.toDopeQuery()
        val additionalWhenThenConditionsDopeQuery = additionalWhenThenConditions.map { it.toDopeQuery() }
        val elseCaseDopeQuery = elseCase?.toDopeQuery()

        val queryString = buildString {
            append("CASE ")
            unaliasedExpressionDopeQuery?.let {
                append(it.queryString).append(" ")
            }

            append(whenThenConditionDopeQuery.queryString).append(" ")

            if (additionalWhenThenConditionsDopeQuery.isNotEmpty()) {
                append(additionalWhenThenConditionsDopeQuery.joinToString(separator = " ") { it.queryString }).append(" ")
            }

            elseCaseDopeQuery?.let {
                append("ELSE ").append(it.queryString).append(" ")
            }

            append("END")

            alias?.let {
                append(" AS `$it`")
            }
        }

        return DopeQuery(
            queryString = queryString,
            parameters = unaliasedExpressionDopeQuery?.parameters.orEmpty() +
                whenThenConditionDopeQuery.parameters +
                additionalWhenThenConditionsDopeQuery.fold(emptyMap()) { additionalParameters, it -> additionalParameters + it.parameters } +
                elseCaseDopeQuery?.parameters.orEmpty(),
        )
    }
}

class UnaliasedCaseExpression(
    private val expression: UnaliasedExpression<out ValidType>? = null,
    private val whenThenCondition: WhenThenCondition,
    private vararg val additionalWhenThenConditions: WhenThenCondition,
    private val elseCase: UnaliasedExpression<out ValidType>? = null,
) : CaseExpression(expression, whenThenCondition, *additionalWhenThenConditions, elseCase = elseCase) {
    override fun toDopeQuery() = getCaseExpressionDopeQuery()

    fun alias(alias: String) = AliasedCaseExpression(alias, expression, whenThenCondition, *additionalWhenThenConditions, elseCase = elseCase)
}

class AliasedCaseExpression(
    private val alias: String,
    expression: UnaliasedExpression<out ValidType>? = null,
    whenThenCondition: WhenThenCondition,
    vararg additionalWhenThenConditions: WhenThenCondition,
    elseCase: UnaliasedExpression<out ValidType>? = null,
) : CaseExpression(expression, whenThenCondition, *additionalWhenThenConditions, elseCase = elseCase) {
    override fun toDopeQuery() = getCaseExpressionDopeQuery(alias)
}

fun case(
    expression: UnaliasedExpression<out ValidType>,
    whenThenCondition: WhenThenCondition,
    vararg additionalWhenThenConditions: WhenThenCondition,
    elseCase: UnaliasedExpression<out ValidType>? = null,
) = UnaliasedCaseExpression(
    expression = expression,
    whenThenCondition = whenThenCondition,
    additionalWhenThenConditions = additionalWhenThenConditions,
    elseCase = elseCase,
)

fun case(
    whenThenCondition: WhenThenCondition,
    vararg additionalWhenThenConditions: WhenThenCondition,
    elseCase: UnaliasedExpression<out ValidType>? = null,
) = UnaliasedCaseExpression(
    whenThenCondition = whenThenCondition,
    additionalWhenThenConditions = additionalWhenThenConditions,
    elseCase = elseCase,
)
