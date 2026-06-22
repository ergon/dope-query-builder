package ch.ergon.dope.mongo.resolver

import ch.ergon.dope.mongo.MongoDopeQuery
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.type.function.type.IsArrayExpression
import ch.ergon.dope.resolvable.expression.type.function.type.IsAtomExpression
import ch.ergon.dope.resolvable.expression.type.function.type.IsBooleanExpression
import ch.ergon.dope.resolvable.expression.type.function.type.IsNumberExpression
import ch.ergon.dope.resolvable.expression.type.function.type.IsObjectExpression
import ch.ergon.dope.resolvable.expression.type.function.type.IsStringExpression
import ch.ergon.dope.resolvable.expression.type.function.type.ToArrayExpression
import ch.ergon.dope.resolvable.expression.type.function.type.ToBooleanExpression
import ch.ergon.dope.resolvable.expression.type.function.type.ToNumberExpression
import ch.ergon.dope.resolvable.expression.type.function.type.ToObjectExpression
import ch.ergon.dope.resolvable.expression.type.function.type.ToStringExpression
import ch.ergon.dope.resolvable.expression.type.function.type.TypeOfExpression

internal fun ExpressionResolver.resolveTypeFunction(expression: Expression<*>): MongoDopeQuery? =
    when (expression) {
        is IsArrayExpression<*> -> {
            val inner = render(expression.expression)
            fragment("{ \"\$isArray\": [ ${inner.queryString} ] }")
        }

        is IsNumberExpression<*> -> {
            val inner = render(expression.expression)
            fragment("{ \"\$isNumber\": ${inner.queryString} }")
        }

        is IsBooleanExpression<*> -> typeEquals(expression.expression, "bool")

        is IsObjectExpression<*> -> typeEquals(expression.expression, "object")

        is IsStringExpression<*> -> typeEquals(expression.expression, "string")

        is ToBooleanExpression<*> -> {
            val inner = render(expression.expression)
            fragment("{ \"\$toBool\": ${inner.queryString} }")
        }

        is ToNumberExpression<*> ->

            if (expression.filterChars == null) {
                val inner = render(expression.expression)
                fragment("{ \"\$toDouble\": ${inner.queryString} }")
            } else {
                null
            }

        is ToStringExpression<*> -> {
            val inner = render(expression.expression)
            fragment("{ \"\$toString\": ${inner.queryString} }")
        }

        is TypeOfExpression<*> -> {
            val inner = render(expression.expression)
            fragment("{ \"\$type\": ${inner.queryString} }")
        }

        is IsAtomExpression<*> -> {
            val inner = render(expression.expression)
            fragment(
                "{ \"\$let\": { \"vars\": { \"v\": ${inner.queryString} }, \"in\": { \"\$switch\": { " +
                    "\"branches\": [ " +
                    "{ \"case\": { \"\$eq\": [ { \"\$type\": \"\$\$v\" }, \"missing\" ] }, \"then\": \"\$\$REMOVE\" }, " +
                    "{ \"case\": { \"\$eq\": [ { \"\$type\": \"\$\$v\" }, \"null\" ] }, \"then\": null } ], " +
                    "\"default\": { \"\$in\": [ { \"\$type\": \"\$\$v\" }, " +
                    "[ \"bool\", \"double\", \"int\", \"long\", \"decimal\", \"string\" ] ] } } } } }",
            )
        }

        is ToArrayExpression<*> -> {
            val inner = render(expression.expression)
            fragment(
                "{ \"\$let\": { \"vars\": { \"v\": ${inner.queryString} }, \"in\": { \"\$switch\": { " +
                    "\"branches\": [ " +
                    "{ \"case\": { \"\$eq\": [ { \"\$type\": \"\$\$v\" }, \"missing\" ] }, \"then\": \"\$\$REMOVE\" }, " +
                    "{ \"case\": { \"\$eq\": [ { \"\$type\": \"\$\$v\" }, \"null\" ] }, \"then\": null }, " +
                    "{ \"case\": { \"\$isArray\": \"\$\$v\" }, \"then\": \"\$\$v\" } ], " +
                    "\"default\": [ \"\$\$v\" ] } } } }",
            )
        }

        is ToObjectExpression<*> -> {
            val inner = render(expression.expression)
            fragment(
                "{ \"\$let\": { \"vars\": { \"v\": ${inner.queryString} }, \"in\": { \"\$switch\": { " +
                    "\"branches\": [ " +
                    "{ \"case\": { \"\$eq\": [ { \"\$type\": \"\$\$v\" }, \"missing\" ] }, \"then\": \"\$\$REMOVE\" }, " +
                    "{ \"case\": { \"\$eq\": [ { \"\$type\": \"\$\$v\" }, \"null\" ] }, \"then\": null }, " +
                    "{ \"case\": { \"\$eq\": [ { \"\$type\": \"\$\$v\" }, \"object\" ] }, \"then\": \"\$\$v\" } ], " +
                    "\"default\": { \"\$literal\": {} } } } } }",
            )
        }

        else -> null
    }

private fun ExpressionResolver.typeEquals(
    expression: Expression<*>,
    bsonType: String,
): MongoDopeQuery.ExpressionFragment {
    val inner = render(expression)
    return fragment(
        "{ \"\$eq\": [ { \"\$type\": ${inner.queryString} }, \"$bsonType\" ] }",
    )
}
