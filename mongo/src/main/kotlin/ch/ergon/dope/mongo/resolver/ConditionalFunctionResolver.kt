package ch.ergon.dope.mongo.resolver

import ch.ergon.dope.mongo.MongoDopeQuery
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.type.function.conditional.CoalesceExpression
import ch.ergon.dope.resolvable.expression.type.function.conditional.DecodeExpression
import ch.ergon.dope.resolvable.expression.type.function.conditional.IfMissingExpression
import ch.ergon.dope.resolvable.expression.type.function.conditional.IfMissingOrNullExpression
import ch.ergon.dope.resolvable.expression.type.function.conditional.IfNullExpression
import ch.ergon.dope.resolvable.expression.type.function.conditional.Nvl2Expression
import ch.ergon.dope.resolvable.expression.type.function.conditional.NvlExpression
import ch.ergon.dope.resolvable.expression.type.function.conditional.SearchResult

internal fun ExpressionResolver.resolveConditionalFunction(expression: Expression<*>): MongoDopeQuery? =
    when (expression) {
        is NvlExpression<*> -> {
            val initial = render(expression.initialExpression)
            val substitute = render(expression.substituteExpression)
            fragment(
                "{ \"\$ifNull\": [${initial.queryString}, ${substitute.queryString}] }",
            )
        }

        is IfNullExpression<*> ->
            ifNull(expression.firstExpression, expression.secondExpression, expression.additionalExpressions)

        is IfMissingOrNullExpression<*> ->
            ifNull(expression.firstExpression, expression.secondExpression, expression.additionalExpressions)

        is CoalesceExpression<*> ->
            ifNull(expression.firstExpression, expression.secondExpression, expression.additionalExpressions)

        is Nvl2Expression<*> -> {
            val initial = render(expression.initialExpression)
            val valueIfExists = render(expression.valueIfExists)
            val valueIfNotExists = render(expression.valueIfNotExists)
            fragment(
                "{ \"\$cond\": [ { \"\$ne\": [ { \"\$ifNull\": [${initial.queryString}, null] }, null ] }, " +
                    "${valueIfExists.queryString}, ${valueIfNotExists.queryString} ] }",
            )
        }

        is IfMissingExpression<*> ->
            ifMissing(expression.firstExpression, expression.secondExpression, expression.additionalExpressions)

        is DecodeExpression<*, *> -> {
            val subject = render(expression.decodeExpression)
            val branchFragments = listOf(expression.searchResult, *expression.searchResults.toTypedArray())
                .map { switchBranch(subject, it) }
            val defaultFragment = expression.default?.let { render(it) }
            val defaultQueryString = defaultFragment?.queryString ?: "null"
            val branchesQueryString = branchFragments.joinToString(", ") { it.queryString }
            fragment(
                "{ \"\$switch\": { \"branches\": [ $branchesQueryString ], \"default\": $defaultQueryString } }",
            )
        }

        else -> null
    }

private fun ExpressionResolver.ifNull(
    firstExpression: Expression<*>,
    secondExpression: Expression<*>,
    additionalExpressions: List<Expression<*>>,
): MongoDopeQuery.ExpressionFragment {
    val operandFragments = (listOf(firstExpression, secondExpression) + additionalExpressions).map { render(it) }
    return fragment(
        "{ \"\$ifNull\": [" + operandFragments.joinToString(", ") { it.queryString } + "] }",
    )
}

private fun ExpressionResolver.ifMissing(
    firstExpression: Expression<*>,
    secondExpression: Expression<*>,
    additionalExpressions: List<Expression<*>>,
): MongoDopeQuery.ExpressionFragment {
    val operandFragments = (listOf(firstExpression, secondExpression) + additionalExpressions).map { render(it) }
    val branchesQueryString = operandFragments.joinToString(", ") { operand ->
        "{ \"case\": { \"\$ne\": [ { \"\$type\": ${operand.queryString} }, \"missing\" ] }, " +
            "\"then\": ${operand.queryString} }"
    }
    return fragment(
        "{ \"\$switch\": { \"branches\": [ $branchesQueryString ], \"default\": \"\$\$REMOVE\" } }",
    )
}

private fun ExpressionResolver.switchBranch(
    subject: MongoDopeQuery.ExpressionFragment,
    searchResult: SearchResult<*, *>,
): MongoDopeQuery.ExpressionFragment {
    val search = render(searchResult.searchExpression)
    val result = render(searchResult.resultExpression)
    return fragment(
        "{ \"case\": { \"\$eq\": [${subject.queryString}, ${search.queryString}] }, \"then\": ${result.queryString} }",
    )
}
