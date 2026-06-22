package ch.ergon.dope.mongo.resolver

import ch.ergon.dope.mongo.MongoDopeQuery
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.type.collection.AnyAndEverySatisfiesExpression
import ch.ergon.dope.resolvable.expression.type.collection.AnySatisfiesExpression
import ch.ergon.dope.resolvable.expression.type.collection.EverySatisfiesExpression
import ch.ergon.dope.resolvable.expression.type.collection.ExistsExpression
import ch.ergon.dope.resolvable.expression.type.collection.InExpression
import ch.ergon.dope.resolvable.expression.type.collection.Iterator
import ch.ergon.dope.resolvable.expression.type.collection.NotInExpression
import ch.ergon.dope.resolvable.expression.type.collection.SatisfiesExpression
import ch.ergon.dope.validtype.ValidType

internal fun ExpressionResolver.resolveCollection(expression: Expression<*>): MongoDopeQuery? =
    when (expression) {
        is Iterator<*> -> fragment("\"\$\$${escapeJsonString(expression.variable)}\"")

        is ExistsExpression<*> -> {
            val array = render(expression.array)
            fragment(
                "{ \"\$gt\": [ { \"\$size\": { \"\$ifNull\": [ ${array.queryString}, [] ] } }, 0 ] }",
            )
        }

        is InExpression<*> -> {
            val value = render(expression.value)
            val collection = render(expression.collection)
            fragment(
                "{ \"\$in\": [ ${value.queryString}, ${collection.queryString} ] }",
            )
        }

        is NotInExpression<*> -> {
            val value = render(expression.value)
            val collection = render(expression.collection)
            fragment(
                "{ \"\$not\": [ { \"\$in\": [ ${value.queryString}, ${collection.queryString} ] } ] }",
            )
        }

        is AnySatisfiesExpression<*> -> {
            val mapFragment = renderSatisfiesMap(expression)
            fragment(
                "{ \"\$anyElementTrue\": [ ${mapFragment.queryString} ] }",
            )
        }

        is EverySatisfiesExpression<*> -> {
            val mapFragment = renderSatisfiesMap(expression)
            fragment(
                "{ \"\$allElementsTrue\": [ ${mapFragment.queryString} ] }",
            )
        }

        is AnyAndEverySatisfiesExpression<*> -> {
            val array = render(expression.arrayExpression)
            val mapFragment = renderSatisfiesMap(expression)
            fragment(
                "{ \"\$and\": [ " +
                    "{ \"\$gt\": [ { \"\$size\": { \"\$ifNull\": [ ${array.queryString}, [] ] } }, 0 ] }, " +
                    "{ \"\$allElementsTrue\": [ ${mapFragment.queryString} ] } ] }",
            )
        }

        else -> null
    }

private fun <T : ValidType> ExpressionResolver.renderSatisfiesMap(
    expression: SatisfiesExpression<T>,
): MongoDopeQuery.ExpressionFragment {
    val variableName = expression.iteratorName ?: "iterator"
    val input = render(expression.arrayExpression)
    val predicate = render(expression.predicate(Iterator(variableName)))
    return fragment(
        "{ \"\$map\": { \"input\": ${input.queryString}, " +
            "\"as\": ${fieldKey(variableName)}, \"in\": ${predicate.queryString} } }",
    )
}
