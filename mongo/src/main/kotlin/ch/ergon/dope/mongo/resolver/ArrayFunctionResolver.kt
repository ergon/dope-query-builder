package ch.ergon.dope.mongo.resolver

import ch.ergon.dope.mongo.MongoDopeQuery
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayAppendExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayAverageExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayConcatExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayContainsExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayCountExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayDistinctExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayExceptExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayIfNullExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayInsertExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayIntersectExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayLengthExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayMaxExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayMinExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayMoveExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayPositionExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayPrependExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayPutExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayRangeExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayRemoveExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayRepeatExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayReplaceExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayReverseExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArraySortExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArraySumExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArraySymmetricDifference1Expression
import ch.ergon.dope.resolvable.expression.type.function.array.ArraySymmetricDifferenceExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArraySymmetricDifferenceNExpression
import ch.ergon.dope.resolvable.expression.type.function.array.ArrayUnionExpression

internal fun ExpressionResolver.resolveArrayFunction(expression: Expression<*>): MongoDopeQuery? =
    when (expression) {
        is ArrayAppendExpression<*> -> {
            val array = render(expression.array)
            val valueFragments = renderAll(listOf(expression.value) + expression.additionalValues)
            val valuesArray = inlineArray(valueFragments)
            fragment("{ \"\$concatArrays\": [${array.queryString}, $valuesArray] }")
        }

        is ArrayPrependExpression<*> -> {
            val array = render(expression.array)
            val valueFragments = renderAll(listOf(expression.value) + expression.additionalValues)
            val valuesArray = inlineArray(valueFragments)
            fragment("{ \"\$concatArrays\": [$valuesArray, ${array.queryString}] }")
        }

        is ArrayConcatExpression<*> -> {
            val arrays = renderAll(listOf(expression.firstArray, expression.secondArray) + expression.additionalArrays)
            fragment("{ \"\$concatArrays\": [${joinQueryStrings(arrays)}] }")
        }

        is ArrayUnionExpression<*> -> {
            val arrays = renderAll(listOf(expression.firstArray, expression.secondArray) + expression.additionalArrays)
            fragment("{ \"\$setUnion\": [${joinQueryStrings(arrays)}] }")
        }

        is ArrayIntersectExpression<*> -> {
            val arrays = renderAll(listOf(expression.firstArray, expression.secondArray) + expression.additionalArrays)
            fragment("{ \"\$setIntersection\": [${joinQueryStrings(arrays)}] }")
        }

        is ArrayExceptExpression<*> -> {
            val array = render(expression.array)
            val except = render(expression.except)
            fragment("{ \"\$setDifference\": [${array.queryString}, ${except.queryString}] }")
        }

        is ArrayDistinctExpression<*> -> {
            val array = render(expression.array)
            fragment("{ \"\$setUnion\": [${array.queryString}] }")
        }

        is ArrayAverageExpression<*> -> {
            val array = render(expression.array)
            fragment("{ \"\$avg\": ${array.queryString} }")
        }

        is ArraySumExpression<*> -> {
            val array = render(expression.array)
            fragment("{ \"\$sum\": ${array.queryString} }")
        }

        is ArrayMaxExpression<*> -> {
            val array = render(expression.array)
            fragment("{ \"\$max\": ${array.queryString} }")
        }

        is ArrayMinExpression<*> -> {
            val array = render(expression.array)
            fragment("{ \"\$min\": ${array.queryString} }")
        }

        is ArrayCountExpression<*> -> {
            val array = render(expression.array)
            fragment("{ \"\$size\": ${array.queryString} }")
        }

        is ArrayLengthExpression<*> -> {
            val array = render(expression.array)
            fragment("{ \"\$size\": ${array.queryString} }")
        }

        is ArrayContainsExpression<*> -> {
            val array = render(expression.array)
            val value = render(expression.value)
            fragment("{ \"\$in\": [${value.queryString}, ${array.queryString}] }")
        }

        is ArrayPositionExpression<*> -> {
            val array = render(expression.array)
            val value = render(expression.value)
            fragment("{ \"\$indexOfArray\": [${array.queryString}, ${value.queryString}] }")
        }

        is ArrayReverseExpression<*> -> {
            val array = render(expression.array)
            fragment("{ \"\$reverseArray\": ${array.queryString} }")
        }

        is ArraySortExpression<*> -> {
            val array = render(expression.array)
            fragment("{ \"\$sortArray\": { \"input\": ${array.queryString}, \"sortBy\": 1 } }")
        }

        is ArrayRangeExpression -> {
            val start = render(expression.start)
            val end = render(expression.end)
            val step = expression.step?.let { render(it) }
            val operands = listOfNotNull(start, end, step)
            fragment("{ \"\$range\": [${joinQueryStrings(operands)}] }")
        }

        is ArrayRemoveExpression<*> -> {
            val array = render(expression.array)
            val valueFragments = renderAll(listOf(expression.value) + expression.additionalValues)
            val valuesArray = inlineArray(valueFragments)
            fragment(
                "{ \"\$filter\": { \"input\": ${array.queryString}, \"as\": \"item\", " +
                    "\"cond\": { \"\$not\": [ { \"\$in\": [\"\$\$item\", $valuesArray] } ] } } }",
            )
        }

        is ArrayIfNullExpression<*> -> {
            val array = render(expression.array)
            fragment(
                "{ \"\$let\": { \"vars\": { \"nonNull\": { \"\$filter\": { \"input\": ${array.queryString}, " +
                    "\"as\": \"e\", \"cond\": { \"\$ne\": [ \"\$\$e\", null ] } } } }, " +
                    "\"in\": { \"\$cond\": [ { \"\$gt\": [ { \"\$size\": \"\$\$nonNull\" }, 0 ] }, " +
                    "{ \"\$arrayElemAt\": [ \"\$\$nonNull\", 0 ] }, null ] } } }",
            )
        }

        is ArrayInsertExpression<*> -> {
            val array = render(expression.array)
            val position = render(expression.position)
            val valueFragments = renderAll(listOf(expression.value) + expression.additionalValues)
            val valuesArray = inlineArray(valueFragments)
            fragment(
                "{ \"\$concatArrays\": [ { \"\$slice\": [ ${array.queryString}, 0, ${position.queryString} ] }, " +
                    "$valuesArray, " +
                    "{ \"\$let\": { \"vars\": { \"n\": { \"\$size\": ${array.queryString} } }, " +
                    "\"in\": { \"\$slice\": [ ${array.queryString}, ${position.queryString}, " +
                    "{ \"\$max\": [ 0, { \"\$subtract\": [ \"\$\$n\", ${position.queryString} ] } ] } ] } } } ] }",
            )
        }

        is ArrayMoveExpression<*> -> {
            val array = render(expression.array)
            val from = render(expression.from)
            val to = render(expression.to)
            fragment(
                "{ \"\$let\": { \"vars\": { " +
                    "\"elem\": { \"\$arrayElemAt\": [ ${array.queryString}, ${from.queryString} ] }, " +
                    "\"removed\": { \"\$concatArrays\": [ " +
                    "{ \"\$cond\": [ { \"\$lte\": [ ${from.queryString}, 0 ] }, [], " +
                    "{ \"\$slice\": [ ${array.queryString}, 0, ${from.queryString} ] } ] }, " +
                    "{ \"\$slice\": [ ${array.queryString}, { \"\$add\": [ ${from.queryString}, 1 ] }, " +
                    "{ \"\$size\": ${array.queryString} } ] } ] } }, " +
                    "\"in\": { \"\$concatArrays\": [ " +
                    "{ \"\$cond\": [ { \"\$lte\": [ ${to.queryString}, 0 ] }, [], " +
                    "{ \"\$slice\": [ \"\$\$removed\", 0, ${to.queryString} ] } ] }, " +
                    "[ \"\$\$elem\" ], " +
                    "{ \"\$slice\": [ \"\$\$removed\", ${to.queryString}, { \"\$size\": \"\$\$removed\" } ] } ] } } }",
            )
        }

        is ArrayPutExpression<*> -> {
            val array = render(expression.array)
            val valueFragments = renderAll(listOf(expression.value) + expression.additionalValues)
            val valuesArray = inlineArray(valueFragments)
            fragment(
                "{ \"\$reduce\": { \"input\": $valuesArray, \"initialValue\": ${array.queryString}, " +
                    "\"in\": { \"\$cond\": [ { \"\$in\": [ \"\$\$this\", \"\$\$value\" ] }, \"\$\$value\", " +
                    "{ \"\$concatArrays\": [ \"\$\$value\", [ \"\$\$this\" ] ] } ] } } }",
            )
        }

        is ArrayRepeatExpression<*> -> {
            val value = render(expression.value)
            val repetitions = render(expression.repetitions)
            fragment(
                "{ \"\$map\": { \"input\": { \"\$range\": [ 0, { \"\$max\": [ 0, ${repetitions.queryString} ] } ] }, " +
                    "\"as\": \"i\", \"in\": ${value.queryString} } }",
            )
        }

        is ArrayReplaceExpression<*> -> {
            val array = render(expression.array)
            val toReplace = render(expression.toReplace)
            val replaceWith = render(expression.replaceWith)
            val max = expression.max?.let { render(it) }
            arrayReplaceFragment(array, toReplace, replaceWith, max)
        }

        is ArraySymmetricDifferenceExpression<*> -> {
            val arrays = renderAll(listOf(expression.firstArray, expression.secondArray) + expression.additionalArrays)
            symmetricDifferenceFragment(arrays, parity = false)
        }

        is ArraySymmetricDifference1Expression<*> -> {
            val arrays = renderAll(listOf(expression.firstArray, expression.secondArray) + expression.additionalArrays)
            symmetricDifferenceFragment(arrays, parity = false)
        }

        is ArraySymmetricDifferenceNExpression<*> -> {
            val arrays = renderAll(listOf(expression.firstArray, expression.secondArray) + expression.additionalArrays)
            symmetricDifferenceFragment(arrays, parity = true)
        }

        else -> null
    }

private fun ExpressionResolver.renderAll(expressions: List<Expression<*>>): List<MongoDopeQuery.ExpressionFragment> =
    expressions.map { render(it) }

private fun joinQueryStrings(fragments: List<MongoDopeQuery.ExpressionFragment>): String =
    fragments.joinToString(", ") { it.queryString }

private fun inlineArray(fragments: List<MongoDopeQuery.ExpressionFragment>): String =
    "[${joinQueryStrings(fragments)}]"

private fun arrayReplaceFragment(
    array: MongoDopeQuery.ExpressionFragment,
    toReplace: MongoDopeQuery.ExpressionFragment,
    replaceWith: MongoDopeQuery.ExpressionFragment,
    max: MongoDopeQuery.ExpressionFragment?,
): MongoDopeQuery.ExpressionFragment {
    val matchCondition = if (max == null) {
        "{ \"\$eq\": [ \"\$\$this\", ${toReplace.queryString} ] }"
    } else {
        "{ \"\$and\": [ { \"\$eq\": [ \"\$\$this\", ${toReplace.queryString} ] }, " +
            "{ \"\$or\": [ { \"\$lt\": [ ${max.queryString}, 0 ] }, " +
            "{ \"\$lt\": [ \"\$\$value.done\", ${max.queryString} ] } ] } ] }"
    }
    val queryString = "{ \"\$let\": { \"vars\": { \"r\": { \"\$reduce\": { \"input\": ${array.queryString}, " +
        "\"initialValue\": { \"out\": [], \"done\": 0 }, " +
        "\"in\": { \"\$cond\": [ $matchCondition, " +
        "{ \"out\": { \"\$concatArrays\": [ \"\$\$value.out\", [ ${replaceWith.queryString} ] ] }, " +
        "\"done\": { \"\$add\": [ \"\$\$value.done\", 1 ] } }, " +
        "{ \"out\": { \"\$concatArrays\": [ \"\$\$value.out\", [ \"\$\$this\" ] ] }, " +
        "\"done\": \"\$\$value.done\" } ] } } } }, \"in\": \"\$\$r.out\" } }"
    return fragment(queryString)
}

private fun symmetricDifferenceFragment(
    arrays: List<MongoDopeQuery.ExpressionFragment>,
    parity: Boolean,
): MongoDopeQuery.ExpressionFragment {
    val union = "{ \"\$setUnion\": [ ${joinQueryStrings(arrays)} ] }"
    val membershipCounts = arrays.joinToString(", ") { array ->
        "{ \"\$cond\": [ { \"\$in\": [ \"\$\$e\", ${array.queryString} ] }, 1, 0 ] }"
    }
    val count = "{ \"\$add\": [ $membershipCounts ] }"
    val comparand = if (parity) "{ \"\$mod\": [ $count, 2 ] }" else count
    val queryString = "{ \"\$let\": { \"vars\": { \"u\": $union }, " +
        "\"in\": { \"\$filter\": { \"input\": \"\$\$u\", \"as\": \"e\", " +
        "\"cond\": { \"\$eq\": [ $comparand, 1 ] } } } } }"
    return fragment(queryString)
}
