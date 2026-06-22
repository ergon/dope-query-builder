package ch.ergon.dope.mongo.resolver

import ch.ergon.dope.mongo.MongoDopeQuery
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.type.StringPrimitive
import ch.ergon.dope.resolvable.expression.type.relational.BetweenExpression
import ch.ergon.dope.resolvable.expression.type.relational.EqualsExpression
import ch.ergon.dope.resolvable.expression.type.relational.GreaterOrEqualThanExpression
import ch.ergon.dope.resolvable.expression.type.relational.GreaterThanExpression
import ch.ergon.dope.resolvable.expression.type.relational.IsMissingExpression
import ch.ergon.dope.resolvable.expression.type.relational.IsNotMissingExpression
import ch.ergon.dope.resolvable.expression.type.relational.IsNotNullExpression
import ch.ergon.dope.resolvable.expression.type.relational.IsNotValuedExpression
import ch.ergon.dope.resolvable.expression.type.relational.IsNullExpression
import ch.ergon.dope.resolvable.expression.type.relational.IsValuedExpression
import ch.ergon.dope.resolvable.expression.type.relational.LessOrEqualThanExpression
import ch.ergon.dope.resolvable.expression.type.relational.LessThanExpression
import ch.ergon.dope.resolvable.expression.type.relational.LikeExpression
import ch.ergon.dope.resolvable.expression.type.relational.NotBetweenExpression
import ch.ergon.dope.resolvable.expression.type.relational.NotEqualsExpression
import ch.ergon.dope.resolvable.expression.type.relational.NotLikeExpression

internal fun ExpressionResolver.resolveRelational(expression: Expression<*>): MongoDopeQuery? =
    when (expression) {
        is EqualsExpression<*> -> binaryOperator("\$eq", expression.left, expression.right)
        is NotEqualsExpression<*> -> binaryOperator("\$ne", expression.left, expression.right)
        is GreaterThanExpression<*> -> binaryOperator("\$gt", expression.left, expression.right)
        is GreaterOrEqualThanExpression<*> -> binaryOperator("\$gte", expression.left, expression.right)
        is LessThanExpression<*> -> binaryOperator("\$lt", expression.left, expression.right)
        is LessOrEqualThanExpression<*> -> binaryOperator("\$lte", expression.left, expression.right)

        is BetweenExpression<*> -> {
            val value = render(expression.expression)
            val start = render(expression.start)
            val end = render(expression.end)
            fragment(
                "{ \"\$and\": [ { \"\$gte\": [${value.queryString}, ${start.queryString}] }, " +
                    "{ \"\$lte\": [${value.queryString}, ${end.queryString}] } ] }",
            )
        }

        is NotBetweenExpression<*> -> {
            val value = render(expression.expression)
            val start = render(expression.start)
            val end = render(expression.end)
            fragment(
                "{ \"\$or\": [ { \"\$lt\": [${value.queryString}, ${start.queryString}] }, " +
                    "{ \"\$gt\": [${value.queryString}, ${end.queryString}] } ] }",
            )
        }

        is LikeExpression -> regexMatch(expression.left, expression.right, negate = false)
        is NotLikeExpression -> regexMatch(expression.left, expression.right, negate = true)

        is IsNullExpression -> typeCheck(expression.field.name, "\$eq", "null")
        is IsNotNullExpression -> typeCheck(expression.field.name, "\$ne", "null")
        is IsMissingExpression -> typeCheck(expression.field.name, "\$eq", "missing")
        is IsNotMissingExpression -> typeCheck(expression.field.name, "\$ne", "missing")
        is IsValuedExpression ->
            fragment("{ \"\$not\": [ { \"\$in\": [ { \"\$type\": ${fieldPath(expression.field.name)} }, [\"null\", \"missing\"] ] } ] }")
        is IsNotValuedExpression ->
            fragment("{ \"\$in\": [ { \"\$type\": ${fieldPath(expression.field.name)} }, [\"null\", \"missing\"] ] }")

        else -> null
    }

private fun ExpressionResolver.regexMatch(
    left: Expression<*>,
    right: Expression<*>,
    negate: Boolean,
): MongoDopeQuery.ExpressionFragment {
    val pattern = right as? StringPrimitive
        ?: error("Mongo LIKE requires a string literal pattern so wildcards can be translated, got $right")
    val input = render(left)
    val regex = "\"${escapeJsonString(likePatternToRegex(pattern.value))}\""
    val match = "{ \"\$regexMatch\": { \"input\": ${input.queryString}, \"regex\": $regex } }"
    val queryString = if (negate) "{ \"\$not\": [ $match ] }" else match
    return fragment(queryString)
}

private fun typeCheck(fieldName: String, operator: String, type: String): MongoDopeQuery.ExpressionFragment =
    fragment("{ \"$operator\": [ { \"\$type\": ${fieldPath(fieldName)} }, \"$type\" ] }")
