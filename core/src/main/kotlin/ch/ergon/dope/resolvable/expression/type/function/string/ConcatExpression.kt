package ch.ergon.dope.resolvable.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.FunctionExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.StringType

data class ConcatExpression<T : StringType>(
    val firstString: TypeExpression<T>,
    val secondString: TypeExpression<T>,
    val stringTypes: List<TypeExpression<T>> = emptyList(),
) : FunctionExpression<T>(
    listOf(firstString, secondString, *stringTypes.toTypedArray()),
)

fun concat(
    firstString: TypeExpression<StringType>,
    secondString: TypeExpression<StringType>,
    vararg strings: TypeExpression<StringType>,
) = ConcatExpression(firstString, secondString, strings.toList())

fun concat(firstString: String, secondString: TypeExpression<StringType>, vararg strings: TypeExpression<StringType>) =
    concat(firstString.toDopeType(), secondString, *strings)

fun concat(firstString: TypeExpression<StringType>, secondString: String, vararg strings: TypeExpression<StringType>) =
    concat(firstString, secondString.toDopeType(), *strings)

fun concat(
    firstString: TypeExpression<StringType>,
    secondString: TypeExpression<StringType>,
    thirdString: String,
    vararg strings: String,
) =
    concat(firstString, secondString, thirdString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat(firstString: String, secondString: String, vararg strings: TypeExpression<StringType>) =
    concat(firstString.toDopeType(), secondString.toDopeType(), *strings)

fun concat(firstString: TypeExpression<StringType>, secondString: String, thirdString: String, vararg strings: String) =
    concat(
        firstString,
        secondString.toDopeType(),
        thirdString.toDopeType(),
        *strings.map { it.toDopeType() }.toTypedArray(),
    )

fun concat(firstString: String, secondString: TypeExpression<StringType>, thirdString: String, vararg strings: String) =
    concat(
        firstString.toDopeType(),
        secondString,
        thirdString.toDopeType(),
        *strings.map { it.toDopeType() }.toTypedArray(),
    )

fun concat(firstString: String, secondString: String, thirdString: String, vararg strings: String) =
    concat(
        firstString.toDopeType(),
        secondString.toDopeType(),
        thirdString.toDopeType(),
        *strings.map { it.toDopeType() }.toTypedArray(),
    )
