package ch.ergon.dope.mongo.resolver

import ch.ergon.dope.mongo.MongoDopeQuery
import ch.ergon.dope.resolvable.Selectable
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.type.AliasedTypeExpression
import ch.ergon.dope.resolvable.expression.type.ArrayPrimitive
import ch.ergon.dope.resolvable.expression.type.BooleanPrimitive
import ch.ergon.dope.resolvable.expression.type.FALSE
import ch.ergon.dope.resolvable.expression.type.IField
import ch.ergon.dope.resolvable.expression.type.NULL
import ch.ergon.dope.resolvable.expression.type.NumberPrimitive
import ch.ergon.dope.resolvable.expression.type.ObjectPrimitive
import ch.ergon.dope.resolvable.expression.type.StringPrimitive
import ch.ergon.dope.resolvable.expression.type.TRUE

internal fun AbstractMongoResolver.render(expression: Expression<*>): MongoDopeQuery.ExpressionFragment {
    val resolved = expression.toDopeQuery(this)
    return resolved as? MongoDopeQuery.ExpressionFragment
        ?: error("Expected an expression fragment, got $resolved")
}

internal fun fragment(queryString: String): MongoDopeQuery.ExpressionFragment =
    MongoDopeQuery.ExpressionFragment(queryString = queryString)

internal fun AbstractMongoResolver.binaryOperator(
    operator: String,
    left: Expression<*>,
    right: Expression<*>,
): MongoDopeQuery.ExpressionFragment {
    val leftFragment = render(left)
    val rightFragment = render(right)
    return fragment("{ \"$operator\": [${leftFragment.queryString}, ${rightFragment.queryString}] }")
}

internal fun fieldPath(name: String): String = "\"\$${escapeJsonString(name)}\""

internal fun fieldKey(name: String): String = "\"${escapeJsonString(name)}\""

internal fun stringLiteral(value: String): String {
    val escaped = escapeJsonString(value)
    return if (value.startsWith("$")) "{ \"\$literal\": \"$escaped\" }" else "\"$escaped\""
}

internal fun escapeJsonString(value: String): String =
    value.map { character ->
        when (character) {
            '"' -> "\\\""
            '\\' -> "\\\\"
            '\b' -> "\\b"
            '\u000C' -> "\\f"
            '\n' -> "\\n"
            '\r' -> "\\r"
            '\t' -> "\\t"
            else -> if (character < ' ') "\\u%04x".format(character.code) else character.toString()
        }
    }.joinToString(separator = "")

internal fun likePatternToRegex(pattern: String): String =
    pattern.map { character ->
        when (character) {
            '%' -> ".*"
            '_' -> "."
            '.', '*', '+', '?', '(', ')', '[', ']', '{', '}', '^', '$', '|', '\\' -> "\\$character"
            else -> character.toString()
        }
    }.joinToString(separator = "", prefix = "^", postfix = "$")

internal fun constantJson(expression: Expression<*>): String? =
    when (expression) {
        is NumberPrimitive -> expression.value.toString()
        is StringPrimitive -> "\"${escapeJsonString(expression.value)}\""
        is BooleanPrimitive -> expression.value.toString()
        TRUE -> "true"
        FALSE -> "false"
        NULL -> "null"
        is ArrayPrimitive<*> -> {
            val elements = expression.collection.map { constantJson(it) }
            if (elements.any { it == null }) null else "[ " + elements.joinToString(", ") + " ]"
        }
        is ObjectPrimitive -> {
            val entries = expression.entries.map { entry ->
                val key = (entry.key as? StringPrimitive)?.value
                val value = constantJson(entry.value)
                if (key == null || value == null) null else "${fieldKey(key)}: $value"
            }
            if (entries.any { it == null }) null else "{ " + entries.joinToString(", ") + " }"
        }
        else -> null
    }

internal fun AbstractMongoResolver.projectionEntry(selectable: Selectable): MongoDopeQuery.ExpressionFragment =
    when (selectable) {
        is AliasedTypeExpression<*> -> render(selectable)
        is IField<*> -> fragment("${fieldKey(selectable.name)}: 1")
        is Expression<*> -> {
            val rendered = render(selectable)
            error(
                "Mongo projection requires an alias for computed select expression " +
                    "(${rendered.queryString}); use .alias(\"name\").",
            )
        }
        else -> error("Mongo cannot project selectable: $selectable")
    }

internal fun selectableFieldName(selectable: Selectable): String =
    when (selectable) {
        is IField<*> -> selectable.name
        is AliasedTypeExpression<*> -> (selectable.typeExpression as? IField<*>)?.name
            ?: error("Mongo requires a plain field here, got aliased expression ${selectable.alias}")
        else -> error("Mongo requires a plain field here, got: $selectable")
    }
