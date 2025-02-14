package ch.ergon.dope.resolvable.expression.single.type.function.string

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.StringType

// Separator is used as an argument and not as a formatting option
class Concat2Expression<T : StringType>(
    separator: TypeExpression<T>,
    string: TypeExpression<T>,
    vararg strings: TypeExpression<T>,
) : FunctionExpression<T>("CONCAT2", separator, string, *strings)

fun concat2(
    separator: TypeExpression<StringType>,
    string: TypeExpression<StringType>,
    vararg strings: TypeExpression<StringType>,
) = Concat2Expression(separator, string, *strings)

fun concat2(separator: String, string: String, vararg strings: String) =
    concat2(separator.toDopeType(), string.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat2(separator: TypeExpression<StringType>, string: String, vararg strings: String) =
    concat2(separator, string.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat2(separator: String, string: TypeExpression<StringType>, vararg strings: TypeExpression<StringType>) =
    concat2(separator.toDopeType(), string, *strings)
