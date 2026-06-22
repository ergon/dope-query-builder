package ch.ergon.dope.mongo.resolver

import ch.ergon.dope.mongo.MongoDopeQuery
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectAddExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectConcatExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectFieldExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectLengthExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectNamesExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectPairsExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectPutExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectRemoveExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectRenameExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectReplaceExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectUnwrapExpression
import ch.ergon.dope.resolvable.expression.type.function.objects.ObjectValuesExpression

internal fun ExpressionResolver.resolveObjectFunction(expression: Expression<*>): MongoDopeQuery? =
    when (expression) {
        is ObjectAddExpression -> {
            val input = render(expression.objectExpression)
            val field = render(expression.objectEntryPrimitive.key)
            val value = render(expression.objectEntryPrimitive.value)
            fragment(
                "{ \"\$setField\": { \"field\": ${field.queryString}, " +
                    "\"input\": ${input.queryString}, \"value\": ${value.queryString} } }",
            )
        }

        is ObjectConcatExpression -> {
            val first = render(expression.firstObjectExpression)
            val second = render(expression.secondObjectExpression)
            val additional = expression.additionalObjectExpression.map { render(it) }
            val operands = listOf(first, second) + additional
            fragment(
                "{ \"\$mergeObjects\": [ " + operands.joinToString(", ") { it.queryString } + " ] }",
            )
        }

        is ObjectFieldExpression -> {
            val field = render(expression.attributeKey)
            val input = render(expression.objectExpression)
            fragment(
                "{ \"\$getField\": { \"field\": ${field.queryString}, \"input\": ${input.queryString} } }",
            )
        }

        is ObjectLengthExpression -> {
            val input = render(expression.objectExpression)
            fragment(
                "{ \"\$size\": { \"\$objectToArray\": ${input.queryString} } }",
            )
        }

        is ObjectNamesExpression -> {
            val input = render(expression.objectExpression)
            fragment(
                "{ \"\$sortArray\": { \"input\": { \"\$map\": { " +
                    "\"input\": { \"\$objectToArray\": ${input.queryString} }, \"in\": \"\$\$this.k\" } }, " +
                    "\"sortBy\": 1 } }",
            )
        }

        is ObjectPairsExpression -> {
            val input = render(expression.objectExpression)
            fragment(
                "{ \"\$sortArray\": { \"input\": { \"\$map\": { " +
                    "\"input\": { \"\$objectToArray\": ${input.queryString} }, " +
                    "\"in\": { \"name\": \"\$\$this.k\", \"val\": \"\$\$this.v\" } } }, " +
                    "\"sortBy\": { \"name\": 1 } } }",
            )
        }

        is ObjectPutExpression -> {
            val field = render(expression.attributeKey)
            val input = render(expression.objectExpression)
            val value = render(expression.attributeValue)
            fragment(
                "{ \"\$setField\": { \"field\": ${field.queryString}, " +
                    "\"input\": ${input.queryString}, \"value\": ${value.queryString} } }",
            )
        }

        is ObjectRemoveExpression -> {
            val field = render(expression.attributeKey)
            val input = render(expression.objectExpression)
            fragment(
                "{ \"\$unsetField\": { \"field\": ${field.queryString}, \"input\": ${input.queryString} } }",
            )
        }

        is ObjectRenameExpression -> {
            val input = render(expression.objectExpression)
            val oldField = render(expression.oldFieldName)
            val newField = render(expression.newFieldName)
            fragment(
                "{ \"\$setField\": { \"field\": ${newField.queryString}, " +
                    "\"input\": { \"\$unsetField\": { \"field\": ${oldField.queryString}, " +
                    "\"input\": ${input.queryString} } }, " +
                    "\"value\": { \"\$getField\": { \"field\": ${oldField.queryString}, " +
                    "\"input\": ${input.queryString} } } } }",
            )
        }

        is ObjectReplaceExpression -> {
            val input = render(expression.objectExpression)
            val oldValue = render(expression.oldValue)
            val newValue = render(expression.newValue)
            fragment(
                "{ \"\$arrayToObject\": { \"\$map\": { " +
                    "\"input\": { \"\$objectToArray\": ${input.queryString} }, " +
                    "\"in\": { \"k\": \"\$\$this.k\", \"v\": { \"\$cond\": [ " +
                    "{ \"\$eq\": [ \"\$\$this.v\", ${oldValue.queryString} ] }, " +
                    "${newValue.queryString}, \"\$\$this.v\" ] } } } } }",
            )
        }

        is ObjectUnwrapExpression -> {
            val input = render(expression.objectExpression)
            fragment(
                "{ \"\$arrayElemAt\": [ { \"\$map\": { " +
                    "\"input\": { \"\$objectToArray\": ${input.queryString} }, \"in\": \"\$\$this.v\" } }, 0 ] }",
            )
        }

        is ObjectValuesExpression -> {
            val input = render(expression.objectExpression)
            fragment(
                "{ \"\$map\": { \"input\": { \"\$sortArray\": { " +
                    "\"input\": { \"\$objectToArray\": ${input.queryString} }, \"sortBy\": { \"k\": 1 } } }, " +
                    "\"in\": \"\$\$this.v\" } }",
            )
        }

        else -> null
    }
