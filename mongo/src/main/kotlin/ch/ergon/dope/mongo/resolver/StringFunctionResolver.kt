package ch.ergon.dope.mongo.resolver

import ch.ergon.dope.mongo.MongoDopeQuery
import ch.ergon.dope.resolvable.expression.Expression
import ch.ergon.dope.resolvable.expression.type.NumberPrimitive
import ch.ergon.dope.resolvable.expression.type.function.string.Concat2Expression
import ch.ergon.dope.resolvable.expression.type.function.string.ConcatExpression
import ch.ergon.dope.resolvable.expression.type.function.string.ContainsExpression
import ch.ergon.dope.resolvable.expression.type.function.string.LengthExpression
import ch.ergon.dope.resolvable.expression.type.function.string.LowerExpression
import ch.ergon.dope.resolvable.expression.type.function.string.LpadExpression
import ch.ergon.dope.resolvable.expression.type.function.string.LtrimExpression
import ch.ergon.dope.resolvable.expression.type.function.string.MBLengthExpression
import ch.ergon.dope.resolvable.expression.type.function.string.MBLpadExpression
import ch.ergon.dope.resolvable.expression.type.function.string.MBPosition1Expression
import ch.ergon.dope.resolvable.expression.type.function.string.MBPositionExpression
import ch.ergon.dope.resolvable.expression.type.function.string.MBRpadExpression
import ch.ergon.dope.resolvable.expression.type.function.string.MBSubstring1Expression
import ch.ergon.dope.resolvable.expression.type.function.string.MBSubstringExpression
import ch.ergon.dope.resolvable.expression.type.function.string.Position1Expression
import ch.ergon.dope.resolvable.expression.type.function.string.PositionExpression
import ch.ergon.dope.resolvable.expression.type.function.string.RepeatExpression
import ch.ergon.dope.resolvable.expression.type.function.string.ReplaceExpression
import ch.ergon.dope.resolvable.expression.type.function.string.RpadExpression
import ch.ergon.dope.resolvable.expression.type.function.string.RtrimExpression
import ch.ergon.dope.resolvable.expression.type.function.string.SplitExpression
import ch.ergon.dope.resolvable.expression.type.function.string.Substring1Expression
import ch.ergon.dope.resolvable.expression.type.function.string.SubstringExpression
import ch.ergon.dope.resolvable.expression.type.function.string.SuffixesExpression
import ch.ergon.dope.resolvable.expression.type.function.string.TrimExpression
import ch.ergon.dope.resolvable.expression.type.function.string.UpperExpression

internal fun ExpressionResolver.resolveStringFunction(expression: Expression<*>): MongoDopeQuery? =
    when (expression) {
        is ConcatExpression<*> -> {
            val first = render(expression.firstString)
            val second = render(expression.secondString)
            val rest = expression.stringTypes.map { render(it) }
            val operands = listOf(first, second) + rest
            fragment(
                "{ \"\$concat\": [${operands.joinToString(", ") { it.queryString }}] }",
            )
        }

        is ContainsExpression -> {
            val inStr = render(expression.inStr)
            val searchStr = render(expression.searchStr)
            fragment(
                "{ \"\$gte\": [ { \"\$indexOfCP\": [${inStr.queryString}, ${searchStr.queryString}] }, 0 ] }",
            )
        }

        is LengthExpression -> {
            val inStr = render(expression.inStr)
            fragment("{ \"\$strLenCP\": ${inStr.queryString} }")
        }

        is MBLengthExpression -> {
            val inStr = render(expression.inStr)
            fragment("{ \"\$strLenCP\": ${inStr.queryString} }")
        }

        is LowerExpression -> {
            val inStr = render(expression.inStr)
            fragment("{ \"\$toLower\": ${inStr.queryString} }")
        }

        is UpperExpression -> {
            val inStr = render(expression.inStr)
            fragment("{ \"\$toUpper\": ${inStr.queryString} }")
        }

        is TrimExpression -> trim("\$trim", expression.inStr, expression.char)
        is LtrimExpression -> trim("\$ltrim", expression.inStr, expression.char)
        is RtrimExpression -> trim("\$rtrim", expression.inStr, expression.char)

        is SubstringExpression ->
            substringCodePoint(expression.inStr, expression.startPos, expression.length, oneBased = false)

        is Substring1Expression ->
            substringCodePoint(expression.inStr, expression.startPos, expression.length, oneBased = true)

        is MBSubstringExpression ->
            substringCodePoint(expression.inStr, expression.startPos, expression.length, oneBased = false)

        is MBSubstring1Expression ->
            substringCodePoint(expression.inStr, expression.startPos, expression.length, oneBased = true)

        is PositionExpression -> indexOfCodePoint(expression.inStr, expression.searchStr)
        is MBPositionExpression -> indexOfCodePoint(expression.inStr, expression.searchStr)

        is Position1Expression -> indexOfCodePointOneBased(expression.inStr, expression.searchStr)
        is MBPosition1Expression -> indexOfCodePointOneBased(expression.inStr, expression.searchStr)

        is ReplaceExpression -> resolveReplace(expression)

        is SplitExpression -> {
            val inSubstring = expression.inSubstring
            if (inSubstring == null) {
                null
            } else {
                val inStr = render(expression.inStr)
                val delimiter = render(inSubstring)
                fragment(
                    "{ \"\$split\": [${inStr.queryString}, ${delimiter.queryString}] }",
                )
            }
        }

        is Concat2Expression<*> -> {
            val separator = render(expression.separator)
            val values = listOf(render(expression.string)) + expression.strings.map { render(it) }
            val inputList = values.joinToString(", ") { it.queryString }
            fragment(
                "{ \"\$reduce\": { \"input\": { \"\$filter\": { \"input\": [ $inputList ], " +
                    "\"as\": \"s\", \"cond\": { \"\$ne\": [ \"\$\$s\", null ] } } }, " +
                    "\"initialValue\": null, \"in\": { \"\$cond\": [ { \"\$eq\": [ \"\$\$value\", null ] }, " +
                    "\"\$\$this\", { \"\$concat\": [ \"\$\$value\", ${separator.queryString}, \"\$\$this\" ] } ] } } }",
            )
        }

        is LpadExpression ->
            leftPad(
                expression.inStr,
                expression.size,
                expression.prefix,
                "{ \"\$add\": [ { \"\$ceil\": { \"\$divide\": [ \"\$\$need\", \"\$\$padLen\" ] } }, 0 ] }",
            )

        is MBLpadExpression ->
            leftPad(
                expression.inStr,
                expression.size,
                expression.prefix,
                "{ \"\$ceil\": { \"\$divide\": [ \"\$\$need\", \"\$\$padLen\" ] } }",
            )

        is RpadExpression -> rightPad(expression.inStr, expression.size, expression.char)
        is MBRpadExpression -> rightPad(expression.inStr, expression.size, expression.postfix)

        is RepeatExpression -> {
            val inStr = render(expression.inStr)
            val repetitions = render(expression.repetitions)
            fragment(
                "{ \"\$reduce\": { \"input\": { \"\$range\": [ 0, ${repetitions.queryString} ] }, " +
                    "\"initialValue\": \"\", \"in\": { \"\$concat\": [ \"\$\$value\", ${inStr.queryString} ] } } }",
            )
        }

        is SuffixesExpression -> {
            val inStr = render(expression.inStr)
            fragment(
                "{ \"\$let\": { \"vars\": { \"len\": { \"\$strLenCP\": ${inStr.queryString} } }, " +
                    "\"in\": { \"\$map\": { \"input\": { \"\$range\": [ 0, \"\$\$len\" ] }, \"as\": \"i\", " +
                    "\"in\": { \"\$substrCP\": [ ${inStr.queryString}, \"\$\$i\", \"\$\$len\" ] } } } } }",
            )
        }

        else -> null
    }

private fun ExpressionResolver.leftPad(
    inStrExpression: Expression<*>,
    sizeExpression: Expression<*>,
    padExpression: Expression<*>?,
    rangeUpperBound: String,
): MongoDopeQuery.ExpressionFragment {
    val inStr = render(inStrExpression)
    val size = render(sizeExpression)
    val pad = padExpression?.let { render(it) }
    val padStr = pad?.queryString ?: "\" \""
    return fragment(
        "{ \"\$let\": { \"vars\": { \"inLen\": { \"\$strLenCP\": ${inStr.queryString} }, " +
            "\"padStr\": { \"\$ifNull\": [ $padStr, \" \" ] }, \"sz\": ${size.queryString} }, " +
            "\"in\": { \"\$cond\": [ { \"\$gte\": [ \"\$\$inLen\", \"\$\$sz\" ] }, " +
            "{ \"\$substrCP\": [ ${inStr.queryString}, 0, \"\$\$sz\" ] }, " +
            "{ \"\$let\": { \"vars\": { \"need\": { \"\$subtract\": [ \"\$\$sz\", \"\$\$inLen\" ] }, " +
            "\"padLen\": { \"\$strLenCP\": \"\$\$padStr\" } }, " +
            "\"in\": { \"\$concat\": [ { \"\$substrCP\": [ { \"\$reduce\": { \"input\": { \"\$range\": " +
            "[ 0, $rangeUpperBound ] }, " +
            "\"initialValue\": \"\", \"in\": { \"\$concat\": [ \"\$\$value\", \"\$\$padStr\" ] } } }, " +
            "0, \"\$\$need\" ] }, ${inStr.queryString} ] } } } ] } } }",
    )
}

private fun ExpressionResolver.rightPad(
    inStrExpression: Expression<*>,
    sizeExpression: Expression<*>,
    padExpression: Expression<*>?,
): MongoDopeQuery.ExpressionFragment {
    val inStr = render(inStrExpression)
    val size = render(sizeExpression)
    val pad = padExpression?.let { render(it) }
    val padStr = pad?.queryString ?: "\" \""
    return fragment(
        "{ \"\$let\": { \"vars\": { \"inLen\": { \"\$strLenCP\": ${inStr.queryString} }, " +
            "\"padStr\": { \"\$ifNull\": [ $padStr, \" \" ] }, \"sz\": ${size.queryString} }, " +
            "\"in\": { \"\$cond\": [ { \"\$gte\": [ \"\$\$inLen\", \"\$\$sz\" ] }, " +
            "{ \"\$substrCP\": [ ${inStr.queryString}, 0, \"\$\$sz\" ] }, " +
            "{ \"\$let\": { \"vars\": { \"need\": { \"\$subtract\": [ \"\$\$sz\", \"\$\$inLen\" ] }, " +
            "\"padLen\": { \"\$strLenCP\": \"\$\$padStr\" } }, " +
            "\"in\": { \"\$concat\": [ ${inStr.queryString}, { \"\$substrCP\": [ { \"\$reduce\": { " +
            "\"input\": { \"\$range\": [ 0, { \"\$ceil\": { \"\$divide\": [ \"\$\$need\", \"\$\$padLen\" ] } } ] }, " +
            "\"initialValue\": \"\", \"in\": { \"\$concat\": [ \"\$\$value\", \"\$\$padStr\" ] } } }, " +
            "0, \"\$\$need\" ] } ] } } } ] } } }",
    )
}

private fun ExpressionResolver.trim(
    operator: String,
    inStrExpression: Expression<*>,
    charExpression: Expression<*>?,
): MongoDopeQuery.ExpressionFragment {
    val inStr = render(inStrExpression)
    return if (charExpression == null) {
        fragment("{ \"$operator\": { \"input\": ${inStr.queryString} } }")
    } else {
        val chars = render(charExpression)
        fragment(
            "{ \"$operator\": { \"input\": ${inStr.queryString}, \"chars\": ${chars.queryString} } }",
        )
    }
}

private fun ExpressionResolver.indexOfCodePoint(
    inStrExpression: Expression<*>,
    searchStrExpression: Expression<*>,
): MongoDopeQuery.ExpressionFragment {
    val inStr = render(inStrExpression)
    val searchStr = render(searchStrExpression)
    return fragment(
        "{ \"\$indexOfCP\": [${inStr.queryString}, ${searchStr.queryString}] }",
    )
}

private fun ExpressionResolver.indexOfCodePointOneBased(
    inStrExpression: Expression<*>,
    searchStrExpression: Expression<*>,
): MongoDopeQuery.ExpressionFragment {
    val inStr = render(inStrExpression)
    val searchStr = render(searchStrExpression)
    val indexOf = "{ \"\$indexOfCP\": [${inStr.queryString}, ${searchStr.queryString}] }"
    return fragment(
        "{ \"\$cond\": [ { \"\$eq\": [ $indexOf, -1 ] }, 0, { \"\$add\": [ $indexOf, 1 ] } ] }",
    )
}

private fun ExpressionResolver.substringCodePoint(
    inStrExpression: Expression<*>,
    startPosExpression: Expression<*>,
    lengthExpression: Expression<*>?,
    oneBased: Boolean,
): MongoDopeQuery.ExpressionFragment {
    val inStr = render(inStrExpression)
    val startPos = render(startPosExpression)
    val startIndex = if (oneBased) {
        "{ \"\$subtract\": [${startPos.queryString}, 1] }"
    } else {
        startPos.queryString
    }
    val count = if (lengthExpression == null) {
        "{ \"\$subtract\": [ { \"\$strLenCP\": ${inStr.queryString} }, $startIndex ] }"
    } else {
        val length = render(lengthExpression)
        length.queryString
    }
    return fragment("{ \"\$substrCP\": [${inStr.queryString}, $startIndex, $count] }")
}

private fun ExpressionResolver.resolveReplace(expression: ReplaceExpression): MongoDopeQuery? {
    val numberOfInstances = expression.numberOfInstances
    val operator = when {
        numberOfInstances == null -> "\$replaceAll"
        numberOfInstances is NumberPrimitive && numberOfInstances.value.toInt() == 1 -> "\$replaceOne"

        else -> return null
    }
    val inStr = render(expression.inStr)
    val searchStr = render(expression.searchStr)
    val replace = render(expression.replace)
    return fragment(
        "{ \"$operator\": { \"input\": ${inStr.queryString}, \"find\": ${searchStr.queryString}, " +
            "\"replacement\": ${replace.queryString} } }",
    )
}
