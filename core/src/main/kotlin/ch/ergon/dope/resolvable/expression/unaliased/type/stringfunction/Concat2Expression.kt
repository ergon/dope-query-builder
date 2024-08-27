package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

// Separator is used as an argument and not as a formatting option
class Concat2Expression(
    private val separator: TypeExpression<StringType>,
    private val string: TypeExpression<StringType>,
    private vararg val strings: TypeExpression<StringType>,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val separatorDopeQuery = separator.toDopeQuery(manager)
        val stringDopeQuery = string.toDopeQuery(manager)
        val stringsDopeQuery = strings.map { it.toDopeQuery(manager) }
        return DopeQuery(
            queryString = toFunctionQueryString(
                symbol = "CONCAT2",
                separatorDopeQuery,
                stringDopeQuery,
                *stringsDopeQuery.toTypedArray(),
            ),
            parameters = separatorDopeQuery.parameters + stringDopeQuery.parameters + stringsDopeQuery.fold(
                emptyMap(),
            ) { stringParameters, field -> stringParameters + field.parameters },
        )
    }
}

fun concat2(separator: TypeExpression<StringType>, string: TypeExpression<StringType>, vararg strings: TypeExpression<StringType>) =
    Concat2Expression(separator, string, *strings)

fun concat2(separator: String, string: String, vararg strings: String) = concat2(
    separator.toDopeType(),
    string.toDopeType(),
    *wrapVarargsWithStringValueType(
        *strings,
    ),
)

fun concat2(separator: TypeExpression<StringType>, string: String, vararg strings: String) = concat2(
    separator,
    string.toDopeType(),
    *wrapVarargsWithStringValueType(
        *strings,
    ),
)

fun concat2(separator: String, string: TypeExpression<StringType>, vararg strings: TypeExpression<StringType>) =
    concat2(separator.toDopeType(), string, *strings)
