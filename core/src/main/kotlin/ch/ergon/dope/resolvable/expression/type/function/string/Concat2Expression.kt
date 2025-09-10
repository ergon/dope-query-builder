package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

// Separator is used as an argument and not as a formatting option
data class Concat2Expression<T : StringType>(
    val separator: TypeExpression<T>,
    val string: TypeExpression<T>,
    val strings: List<TypeExpression<T>> = emptyList(),
) : FunctionExpression<T>("CONCAT2", listOf(separator, string, *strings.toTypedArray()))

fun concat2(
    separator: TypeExpression<StringType>,
    string: TypeExpression<StringType>,
    vararg strings: TypeExpression<StringType>,
) = Concat2Expression(separator, string, strings.toList())

fun concat2(separator: String, string: String, vararg strings: String) =
    concat2(separator.toDopeType(), string.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat2(separator: TypeExpression<StringType>, string: String, vararg strings: String) =
    concat2(separator, string.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat2(separator: String, string: TypeExpression<StringType>, vararg strings: TypeExpression<StringType>) =
    concat2(separator.toDopeType(), string, *strings)
