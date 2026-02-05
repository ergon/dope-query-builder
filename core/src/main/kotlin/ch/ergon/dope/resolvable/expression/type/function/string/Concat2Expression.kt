package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

data class Concat2Expression<T : StringType>(
    val separator: TypeExpression<T>,
    val string: TypeExpression<T>,
    val strings: List<TypeExpression<T>> = emptyList(),
) : FunctionExpression<T>(listOf(separator, string, *strings.toTypedArray()))

fun TypeExpression<StringType>.concat2(
    string: TypeExpression<StringType>,
    vararg strings: TypeExpression<StringType>,
) = Concat2Expression(this, string, strings.toList())

fun String.concat2(string: String, vararg strings: String) =
    toDopeType().concat2(string.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun TypeExpression<StringType>.concat2(string: String, vararg strings: String) =
    concat2(string.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun String.concat2(string: TypeExpression<StringType>, vararg strings: TypeExpression<StringType>) =
    toDopeType().concat2(string, *strings)
