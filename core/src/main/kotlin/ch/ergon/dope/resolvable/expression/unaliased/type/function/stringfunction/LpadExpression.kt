package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
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
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val inStrDopeQuery = inStr.toDopeQuery(manager)
        val sizeDopeQuery = size.toDopeQuery(manager)
        val prefixDopeQuery = prefix?.toDopeQuery(manager)
        return DopeQuery(
            queryString = toFunctionQueryString(symbol = "LPAD", inStrDopeQuery, sizeDopeQuery, prefixDopeQuery),
            parameters = inStrDopeQuery.parameters + sizeDopeQuery.parameters + prefixDopeQuery?.parameters.orEmpty(),
            positionalParameters = inStrDopeQuery.positionalParameters + sizeDopeQuery.positionalParameters +
                prefixDopeQuery?.positionalParameters.orEmpty(),
        )
    }
}

fun lpad(
    inStr: TypeExpression<StringType>,
    size: TypeExpression<NumberType>,
    prefix: TypeExpression<StringType>? = null,
) = LpadExpression(inStr, size, prefix)

fun lpad(inStr: TypeExpression<StringType>, size: TypeExpression<NumberType>, prefix: String) =
    lpad(inStr, size, prefix.toDopeType())

fun lpad(inStr: TypeExpression<StringType>, size: Number, prefix: TypeExpression<StringType>) =
    lpad(inStr, size.toDopeType(), prefix)

fun lpad(inStr: String, size: TypeExpression<NumberType>, prefix: TypeExpression<StringType>) =
    lpad(inStr.toDopeType(), size, prefix)

fun lpad(inStr: TypeExpression<StringType>, size: Number) = lpad(inStr, size.toDopeType())

fun lpad(inStr: TypeExpression<StringType>, size: Number, prefix: String) =
    lpad(inStr, size.toDopeType(), prefix.toDopeType())

fun lpad(inStr: String, size: TypeExpression<NumberType>) = lpad(inStr.toDopeType(), size)

fun lpad(inStr: String, size: TypeExpression<NumberType>, prefix: String) =
    lpad(inStr.toDopeType(), size, prefix.toDopeType())

fun lpad(inStr: String, size: Number, prefix: TypeExpression<StringType>) = lpad(inStr.toDopeType(), size.toDopeType(), prefix)

fun lpad(inStr: String, size: Number) = lpad(inStr.toDopeType(), size.toDopeType())

fun lpad(inStr: String, size: Number, prefix: String) = lpad(inStr.toDopeType(), size.toDopeType(), prefix.toDopeType())
