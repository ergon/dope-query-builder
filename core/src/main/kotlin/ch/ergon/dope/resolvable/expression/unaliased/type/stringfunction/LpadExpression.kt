package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toNumberType
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class LpadExpression(
    private val inStr: TypeExpression<StringType>,
    private val size: TypeExpression<NumberType>,
    private val prefix: TypeExpression<StringType>? = null,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val inStrDopeQuery = inStr.toDopeQuery()
        val sizeDopeQuery = size.toDopeQuery()
        val prefixDopeQuery = prefix?.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString(symbol = "LPAD", inStrDopeQuery, sizeDopeQuery, extra = prefixDopeQuery),
            parameters = inStrDopeQuery.parameters + sizeDopeQuery.parameters + (prefixDopeQuery?.parameters ?: emptyMap()),
        )
    }
}

fun lpad(
    inStr: TypeExpression<StringType>,
    size: TypeExpression<NumberType>,
    prefix: TypeExpression<StringType>? = null,
): LpadExpression =
    LpadExpression(inStr, size, prefix)

fun lpad(inStr: TypeExpression<StringType>, size: TypeExpression<NumberType>, prefix: String): LpadExpression =
    lpad(inStr, size, prefix.toStringType())

fun lpad(inStr: TypeExpression<StringType>, size: Number, prefix: TypeExpression<StringType>): LpadExpression =
    lpad(inStr, size.toNumberType(), prefix)

fun lpad(inStr: String, size: TypeExpression<NumberType>, prefix: TypeExpression<StringType>): LpadExpression =
    lpad(inStr.toStringType(), size, prefix)

fun lpad(inStr: TypeExpression<StringType>, size: Number): LpadExpression = lpad(inStr, size.toNumberType())

fun lpad(inStr: TypeExpression<StringType>, size: Number, prefix: String): LpadExpression =
    lpad(inStr, size.toNumberType(), prefix.toStringType())

fun lpad(inStr: String, size: TypeExpression<NumberType>): LpadExpression = lpad(inStr.toStringType(), size)

fun lpad(inStr: String, size: TypeExpression<NumberType>, prefix: String): LpadExpression =
    lpad(inStr.toStringType(), size, prefix.toStringType())

fun lpad(inStr: String, size: Number, prefix: TypeExpression<StringType>) = lpad(inStr.toStringType(), size.toNumberType(), prefix)

fun lpad(inStr: String, size: Number): LpadExpression = lpad(inStr.toStringType(), size.toNumberType())

fun lpad(inStr: String, size: Number, prefix: String): LpadExpression = lpad(inStr.toStringType(), size.toNumberType(), prefix.toStringType())
