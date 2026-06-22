package ch.ergon.dope.mongo.resolver

import ch.ergon.dope.mongo.MongoDopeQuery
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.type.function.comparison.GreatestExpression
import ch.ergon.dope.resolvable.expression.type.function.comparison.LeastExpression

internal fun ExpressionResolver.resolveComparisonFunction(expression: Expression<*>): MongoDopeQuery? =
    when (expression) {
        is GreatestExpression<*> ->
            comparisonAccumulator(
                "\$max",
                expression.firstExpression,
                expression.secondExpression,
                expression.additionalExpressions,
            )

        is LeastExpression<*> ->
            comparisonAccumulator(
                "\$min",
                expression.firstExpression,
                expression.secondExpression,
                expression.additionalExpressions,
            )

        else -> null
    }

private fun ExpressionResolver.comparisonAccumulator(
    operator: String,
    firstExpression: Expression<*>,
    secondExpression: Expression<*>,
    additionalExpressions: List<Expression<*>>,
): MongoDopeQuery.ExpressionFragment {
    val operands = (listOf(firstExpression, secondExpression) + additionalExpressions).map { render(it) }
    val joined = operands.joinToString(separator = ", ") { it.queryString }
    return fragment("{ \"$operator\": [$joined] }")
}
