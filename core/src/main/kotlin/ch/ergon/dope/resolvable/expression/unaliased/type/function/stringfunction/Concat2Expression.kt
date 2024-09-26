package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.StringType

// Separator is used as an argument and not as a formatting option
class Concat2Expression(
    separator: TypeExpression<StringType>,
    string: TypeExpression<StringType>,
    vararg strings: TypeExpression<StringType>,
) : FunctionExpression<StringType>("CONCAT2", separator, string, *strings)

fun concat2(
    separator: TypeExpression<StringType>,
    string: TypeExpression<StringType>,
    vararg strings: TypeExpression<StringType>,
) = Concat2Expression(separator, string, *strings)

fun concat2(separator: String, string: String, vararg strings: String) =
    Concat2Expression(separator.toDopeType(), string.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat2(separator: TypeExpression<StringType>, string: String, vararg strings: String) =
    Concat2Expression(separator, string.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat2(separator: String, string: TypeExpression<StringType>, vararg strings: TypeExpression<StringType>) =
    Concat2Expression(separator.toDopeType(), string, *strings)
