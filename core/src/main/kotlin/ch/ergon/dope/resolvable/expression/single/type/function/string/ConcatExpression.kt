package ch.ergon.dope.resolvable.expression.single.type.function.string

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.validtype.StringType

class ConcatExpression<T : StringType>(
    firstString: TypeExpression<T>,
    secondString: TypeExpression<T>,
    vararg stringTypes: TypeExpression<T>,
) : FunctionExpression<T>(
    "CONCAT",
    firstString,
    secondString,
    *stringTypes,
)

fun concat(
    firstString: TypeExpression<StringType>,
    secondString: TypeExpression<StringType>,
    vararg strings: TypeExpression<StringType>,
) = ConcatExpression(firstString, secondString, *strings)

fun concat(firstString: String, secondString: TypeExpression<StringType>, vararg strings: TypeExpression<StringType>) =
    concat(firstString.toDopeType(), secondString, *strings)

fun concat(firstString: TypeExpression<StringType>, secondString: String, vararg strings: TypeExpression<StringType>) =
    concat(firstString, secondString.toDopeType(), *strings)

fun concat(firstString: TypeExpression<StringType>, secondString: TypeExpression<StringType>, thirdString: String, vararg strings: String) =
    concat(firstString, secondString, thirdString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat(firstString: String, secondString: String, vararg strings: TypeExpression<StringType>) =
    concat(firstString.toDopeType(), secondString.toDopeType(), *strings)

fun concat(firstString: TypeExpression<StringType>, secondString: String, thirdString: String, vararg strings: String) =
    concat(firstString, secondString.toDopeType(), thirdString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat(firstString: String, secondString: TypeExpression<StringType>, thirdString: String, vararg strings: String) =
    concat(firstString.toDopeType(), secondString, thirdString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat(firstString: String, secondString: String, thirdString: String, vararg strings: String) =
    concat(firstString.toDopeType(), secondString.toDopeType(), thirdString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())
