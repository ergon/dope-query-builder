package ch.ergon.dope.resolvable.expression.unaliased.type.function.stringfunction

import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.validtype.StringType

class ConcatExpression(
    firstString: TypeExpression<StringType>,
    secondString: TypeExpression<StringType>,
    vararg stringTypes: TypeExpression<StringType>,
) : FunctionExpression<StringType>("CONCAT", firstString, secondString, *stringTypes)

fun concat(
    firstString: TypeExpression<StringType>,
    secondString: TypeExpression<StringType>,
    vararg strings: TypeExpression<StringType>,
) = ConcatExpression(firstString, secondString, *strings)

fun concat(firstString: String, secondString: TypeExpression<StringType>, vararg strings: TypeExpression<StringType>) =
    concat(firstString.toDopeType(), secondString, *strings)

fun concat(firstString: TypeExpression<StringType>, secondString: String, vararg strings: String) =
    concat(firstString, secondString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat(firstString: String, secondString: String, vararg strings: String) =
    concat(firstString.toDopeType(), secondString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun main() {
    println("test")
}
