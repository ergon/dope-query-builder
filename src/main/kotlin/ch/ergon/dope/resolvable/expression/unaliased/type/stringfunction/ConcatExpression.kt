package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class ConcatExpression(
    private val firstString: TypeExpression<StringType>,
    private val secondString: TypeExpression<StringType>,
    private vararg val stringTypes: TypeExpression<StringType>,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toQueryString(): String = toFunctionQueryString(symbol = "CONCAT", firstString, secondString, *stringTypes)
}

fun concat(firstString: TypeExpression<StringType>, secondString: TypeExpression<StringType>, vararg strings: TypeExpression<StringType>) =
    ConcatExpression(firstString, secondString, *strings)

fun concat(firstString: String, secondString: String, vararg strings: String): ConcatExpression =
    concat(firstString.toStringType(), secondString.toStringType(), *wrapVarargsWithStringValueType(*strings))

internal fun wrapVarargsWithStringValueType(vararg strings: String) = strings.map { it.toStringType() }.toTypedArray()
