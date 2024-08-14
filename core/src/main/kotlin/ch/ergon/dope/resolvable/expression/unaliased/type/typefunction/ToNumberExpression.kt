package ch.ergon.dope.resolvable.expression.unaliased.type.typefunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType

class ToNumberExpression<T : ValidType>(
    private val expression: TypeExpression<T>,
    private val filterChars: TypeExpression<StringType>? = null,
) : TypeExpression<NumberType>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val expressionDopeQuery = expression.toDopeQuery()
        val filterCharsDopeQuery = filterChars?.toDopeQuery()
        return DopeQuery(
            queryString = toFunctionQueryString("TONUMBER", expressionDopeQuery, filterCharsDopeQuery),
            parameters = expressionDopeQuery.parameters + filterCharsDopeQuery?.parameters.orEmpty(),
        )
    }
}

fun <T : ValidType> TypeExpression<T>.toNumber() = ToNumberExpression(this)

fun String.toNumber() = ToNumberExpression(this.toDopeType())

fun Boolean.toNumber() = ToNumberExpression(this.toDopeType())

fun TypeExpression<StringType>.toNumber(filterChars: TypeExpression<StringType>) =
    ToNumberExpression(this, filterChars)

fun TypeExpression<StringType>.toNumber(filterChars: String) =
    ToNumberExpression(this, filterChars.toDopeType())

fun String.toNumber(filterChars: TypeExpression<StringType>) =
    ToNumberExpression(this.toDopeType(), filterChars)

fun String.toNumber(filterChars: String) =
    ToNumberExpression(this.toDopeType(), filterChars.toDopeType())
