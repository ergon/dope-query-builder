package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

// Argument is called char, but is a string...
class TrimExpression(
    private val inStr: TypeExpression<StringType>,
    private val char: TypeExpression<StringType>? = null,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val inStrDopeQuery = inStr.toDopeQuery(manager)
        val charDopeQuery = char?.toDopeQuery(manager)
        return DopeQuery(
            queryString = toFunctionQueryString(symbol = "TRIM", inStrDopeQuery, charDopeQuery),
            parameters = inStrDopeQuery.parameters + charDopeQuery?.parameters.orEmpty(),
            positionalParameters = inStrDopeQuery.positionalParameters + charDopeQuery?.positionalParameters.orEmpty(),
        )
    }
}

fun trim(inStr: TypeExpression<StringType>, char: TypeExpression<StringType>? = null) = TrimExpression(inStr, char)

fun trim(inStr: TypeExpression<StringType>, char: Char) = trim(inStr, char.toString().toDopeType())

fun trim(inStr: String, char: TypeExpression<StringType>? = null) = trim(inStr.toDopeType(), char)

fun trim(inStr: String, char: String) = trim(inStr.toDopeType(), char.toDopeType())

fun trim(inStr: String, char: Char) = trim(inStr.toDopeType(), char.toString().toDopeType())
