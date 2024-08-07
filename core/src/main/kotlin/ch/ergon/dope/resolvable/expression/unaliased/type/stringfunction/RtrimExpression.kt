package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class RtrimExpression(
    private val inStr: TypeExpression<StringType>,
    private val char: TypeExpression<StringType>? = null,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val inStrDopeQuery = inStr.toDopeQuery()
        val charDopeQuery = char?.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString(symbol = "RTRIM", inStrDopeQuery, charDopeQuery),
            parameters = inStrDopeQuery.parameters + charDopeQuery?.parameters.orEmpty(),
        )
    }
}

fun rtrim(inStr: TypeExpression<StringType>, char: TypeExpression<StringType>? = null) = RtrimExpression(inStr, char)

fun rtrim(inStr: TypeExpression<StringType>, char: String) = rtrim(inStr, char.toDopeType())

fun rtrim(inStr: String, char: TypeExpression<StringType>? = null) = rtrim(inStr.toDopeType(), char)

fun rtrim(inStr: String, char: Char) = rtrim(inStr.toDopeType(), char.toString().toDopeType())

fun rtrim(inStr: String, char: String) = rtrim(inStr.toDopeType(), char.toDopeType())

fun rtrim(inStr: TypeExpression<StringType>, char: Char) = rtrim(inStr, char.toString().toDopeType())
