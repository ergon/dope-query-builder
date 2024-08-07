package ch.ergon.dope.resolvable.expression

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.WhenThenCondition

sealed class CaseExpression(
    private val expression: Expression? = null,
    private val whenThenCondition: WhenThenCondition,
    private vararg val additionalWhenThenConditions: WhenThenCondition,
    private val elseCase: Expression? = null,
) : Expression {
    internal fun getCaseExpressionDopeQuery(alias: String? = null): DopeQuery {
        val expressionDope = expression?.toDopeQuery()
        val whenThenConditionDopeQuery = whenThenCondition.toDopeQuery()
        val additionalWhenThenConditionsDopeQuery = additionalWhenThenConditions.map { it.toDopeQuery() }
        val elseCaseDopeQuery = elseCase?.toDopeQuery()

        val queryString = buildString {
            append("CASE ")
            expressionDope?.let {
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
            queryString,
            expressionDope?.parameters.orEmpty() + whenThenConditionDopeQuery.parameters + additionalWhenThenConditionsDopeQuery.fold(
                emptyMap(),
            ) { additionalParameters, it -> additionalParameters + it.parameters } + elseCaseDopeQuery?.parameters.orEmpty(),
        )
    }
}

class UnaliasedCaseExpression(
    private val expression: Expression? = null,
    private val whenThenCondition: WhenThenCondition,
    private vararg val additionalWhenThenConditions: WhenThenCondition,
    private val elseCase: Expression? = null,
) : CaseExpression(expression, whenThenCondition, *additionalWhenThenConditions, elseCase = elseCase) {
    override fun toDopeQuery() = getCaseExpressionDopeQuery()

    fun alias(alias: String) = AliasedCaseExpression(alias, expression, whenThenCondition, *additionalWhenThenConditions, elseCase = elseCase)
}

class AliasedCaseExpression(
    private val alias: String,
    expression: Expression? = null,
    whenThenCondition: WhenThenCondition,
    vararg additionalWhenThenConditions: WhenThenCondition,
    elseCase: Expression? = null,
) : CaseExpression(expression, whenThenCondition, *additionalWhenThenConditions, elseCase = elseCase) {
    override fun toDopeQuery() = getCaseExpressionDopeQuery(alias)
}

fun case(
    expression: Expression?,
    whenThenCondition: WhenThenCondition,
    vararg additionalWhenThenConditions: WhenThenCondition,
    elseCase: Expression? = null,
) = UnaliasedCaseExpression(
    expression = expression,
    whenThenCondition = whenThenCondition,
    additionalWhenThenConditions = additionalWhenThenConditions,
    elseCase = elseCase,
)

fun case(
    whenThenCondition: WhenThenCondition,
    vararg additionalWhenThenConditions: WhenThenCondition,
    elseCase: Expression? = null,
) = UnaliasedCaseExpression(
    whenThenCondition = whenThenCondition,
    additionalWhenThenConditions = additionalWhenThenConditions,
    elseCase = elseCase,
)
