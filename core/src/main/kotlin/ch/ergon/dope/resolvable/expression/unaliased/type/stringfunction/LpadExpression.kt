package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class LpadExpression(
    private val inStr: TypeExpression<StringType>,
    private val size: TypeExpression<NumberType>,
    private val extra: TypeExpression<StringType> = " ".toStringType(),
) : TypeExpression<StringType>, FunctionOperator {
    override fun toQueryString(): String = toFunctionQueryString(symbol = "LPAD", inStr, size, extra = extra)
}

fun lpad(
    inStr: TypeExpression<StringType>,
    size: TypeExpression<NumberType>,
    extra: TypeExpression<StringType> = " ".toStringType(),
): LpadExpression =
    LpadExpression(inStr, size, extra)

fun lpad(inStr: TypeExpression<StringType>, size: TypeExpression<NumberType>, extra: String): LpadExpression =
    lpad(inStr, size, extra.toStringType())

fun lpad(inStr: TypeExpression<StringType>, size: Number, extra: TypeExpression<StringType>): LpadExpression =
    lpad(inStr, size.toNumberType(), extra)

fun lpad(inStr: String, size: TypeExpression<NumberType>, extra: TypeExpression<StringType>): LpadExpression =
    lpad(inStr.toStringType(), size, extra)

fun lpad(inStr: TypeExpression<StringType>, size: Number): LpadExpression = lpad(inStr, size.toNumberType())

fun lpad(inStr: TypeExpression<StringType>, size: Number, extra: String): LpadExpression = lpad(inStr, size.toNumberType(), extra.toStringType())

fun lpad(inStr: String, size: TypeExpression<NumberType>): LpadExpression = lpad(inStr.toStringType(), size)

fun lpad(inStr: String, size: TypeExpression<NumberType>, extra: String): LpadExpression = lpad(inStr.toStringType(), size, extra.toStringType())

fun lpad(inStr: String, size: Number, extra: TypeExpression<StringType>) = lpad(inStr.toStringType(), size.toNumberType(), extra)

fun lpad(inStr: String, size: Number): LpadExpression = lpad(inStr.toStringType(), size.toNumberType())

fun lpad(inStr: String, size: Number, extra: String): LpadExpression = lpad(inStr.toStringType(), size.toNumberType(), extra.toStringType())
