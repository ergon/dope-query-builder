package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

class LtrimExpression(
    private val inStr: TypeExpression<StringType>,
    private val char: TypeExpression<StringType>? = null,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val inStrDopeQuery = inStr.toDopeQuery(manager)
        val charDopeQuery = char?.toDopeQuery(manager)
        return DopeQuery(
            queryString = toFunctionQueryString(symbol = "LTRIM", inStrDopeQuery, charDopeQuery),
            parameters = inStrDopeQuery.parameters + charDopeQuery?.parameters.orEmpty(),
            manager = manager,
        )
    }
}

fun ltrim(inStr: TypeExpression<StringType>, char: TypeExpression<StringType>? = null) =
    LtrimExpression(inStr, char)

fun ltrim(inStr: TypeExpression<StringType>, char: String) = ltrim(inStr, char.toDopeType())

fun ltrim(inStr: String, char: TypeExpression<StringType>) = ltrim(inStr.toDopeType(), char)

fun ltrim(inStr: String, char: String) = ltrim(inStr.toDopeType(), char.toDopeType())

fun ltrim(inStr: String) = ltrim(inStr.toDopeType())
