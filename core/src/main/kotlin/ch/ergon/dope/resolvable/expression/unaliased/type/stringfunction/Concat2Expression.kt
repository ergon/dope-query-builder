package ch.ergon.dope.resolvable.expression.unaliased.type.stringfunction

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toStringType
import ch.ergon.dope.resolvable.operator.FunctionOperator
import ch.ergon.dope.validtype.StringType

// Separator is used as an argument and not as a formatting option
class Concat2Expression(
    private val separator: TypeExpression<StringType>,
    private val string: TypeExpression<StringType>,
    private vararg val strings: TypeExpression<StringType>,
) : TypeExpression<StringType>, FunctionOperator {
    override fun toDopeQuery(): DopeQuery {
        val stringsDopeQuery = strings.map { it.toDopeQuery() }
        val separatorDopeQuery = separator.toDopeQuery()
        val stringDopeQuery = string.toDopeQuery()
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

fun concat2(separator: String, string: String, vararg strings: String): Concat2Expression = concat2(
    separator.toStringType(),
    string.toStringType(),
    *wrapVarargsWithStringValueType(
        *strings,
    ),
)

fun concat2(separator: TypeExpression<StringType>, string: String, vararg strings: String): Concat2Expression = concat2(
    separator,
    string.toStringType(),
    *wrapVarargsWithStringValueType(
        *strings,
    ),
)

fun concat2(separator: String, string: TypeExpression<StringType>, vararg strings: TypeExpression<StringType>): Concat2Expression =
    concat2(separator.toStringType(), string, *strings)
