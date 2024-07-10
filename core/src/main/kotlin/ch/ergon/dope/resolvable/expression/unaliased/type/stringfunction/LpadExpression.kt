package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
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
            parameters = inStrDopeQuery.parameters + sizeDopeQuery.parameters + prefixDopeQuery?.parameters.orEmpty(),
        )
    }
}

fun lpad(
    inStr: TypeExpression<StringType>,
    size: TypeExpression<NumberType>,
    prefix: TypeExpression<StringType>? = null,
): LpadExpression = LpadExpression(inStr, size, prefix)

fun lpad(inStr: TypeExpression<StringType>, size: TypeExpression<NumberType>, prefix: String): LpadExpression =
    lpad(inStr, size, prefix.toDopeType())

fun lpad(inStr: TypeExpression<StringType>, size: Number, prefix: TypeExpression<StringType>): LpadExpression =
    lpad(inStr, size.toDopeType(), prefix)

fun lpad(inStr: String, size: TypeExpression<NumberType>, prefix: TypeExpression<StringType>): LpadExpression =
    lpad(inStr.toDopeType(), size, prefix)

fun lpad(inStr: TypeExpression<StringType>, size: Number): LpadExpression = lpad(inStr, size.toDopeType())

fun lpad(inStr: TypeExpression<StringType>, size: Number, prefix: String): LpadExpression =
    lpad(inStr, size.toDopeType(), prefix.toDopeType())

fun lpad(inStr: String, size: TypeExpression<NumberType>): LpadExpression = lpad(inStr.toDopeType(), size)

fun lpad(inStr: String, size: TypeExpression<NumberType>, prefix: String): LpadExpression =
    lpad(inStr.toDopeType(), size, prefix.toDopeType())

fun lpad(inStr: String, size: Number, prefix: TypeExpression<StringType>) = lpad(inStr.toDopeType(), size.toDopeType(), prefix)

fun lpad(inStr: String, size: Number): LpadExpression = lpad(inStr.toDopeType(), size.toDopeType())

fun lpad(inStr: String, size: Number, prefix: String): LpadExpression = lpad(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())
