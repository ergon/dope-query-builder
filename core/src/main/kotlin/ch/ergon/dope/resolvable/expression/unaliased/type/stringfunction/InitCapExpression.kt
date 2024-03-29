package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class InitCapExpression(
    private val inStr: TypeExpression<StringType>,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toQueryString(): String = toFunctionQueryString(symbol = "INITCAP", inStr)
}

fun initCap(inStr: TypeExpression<StringType>) = InitCapExpression(inStr)

fun initCap(inStr: String): InitCapExpression = initCap(inStr.toStringType())

fun title(inStr: String) = initCap(inStr)

// only an alias (defined by couchbase)
fun title(inStr: TypeExpression<StringType>) = initCap(inStr)
