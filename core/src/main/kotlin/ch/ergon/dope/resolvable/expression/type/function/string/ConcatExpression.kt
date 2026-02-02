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

fun TypeExpression<StringType>.concat(
    secondString: TypeExpression<StringType>,
    vararg strings: TypeExpression<StringType>,
) = ConcatExpression(this, secondString, strings.toList())

fun TypeExpression<StringType>.concat(
    secondString: String,
    vararg strings: TypeExpression<StringType>,
) = concat(secondString.toDopeType(), *strings)

fun TypeExpression<StringType>.concat(
    secondString: TypeExpression<StringType>,
    thirdString: String,
    vararg strings: String,
) = concat(secondString, thirdString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun TypeExpression<StringType>.concat(
    secondString: String,
    thirdString: String,
    vararg strings: String,
) = concat(secondString.toDopeType(), thirdString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun String.concat(
    secondString: TypeExpression<StringType>,
    vararg strings: TypeExpression<StringType>,
) = toDopeType().concat(secondString, *strings)

fun String.concat(
    secondString: String,
    vararg strings: TypeExpression<StringType>,
) = toDopeType().concat(secondString.toDopeType(), *strings)

fun String.concat(
    secondString: String,
) = toDopeType().concat(secondString.toDopeType())

fun String.concat(
    secondString: TypeExpression<StringType>,
    thirdString: String,
    vararg strings: String,
) = toDopeType().concat(secondString, thirdString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun String.concat(
    secondString: String,
    thirdString: String,
    vararg strings: String,
) = toDopeType().concat(
    secondString.toDopeType(),
    thirdString.toDopeType(),
    *strings.map { it.toDopeType() }.toTypedArray(),
)
