package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.string.concat
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun CMJsonField<String>.concat(
    secondString: CMJsonField<String>,
    vararg strings: CMJsonField<String>,
) = toDopeType().concat(secondString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun TypeExpression<StringType>.concat(
    secondString: CMJsonField<String>,
    vararg strings: CMJsonField<String>,
) = concat(secondString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun TypeExpression<StringType>.concat(
    secondString: TypeExpression<StringType>,
    vararg strings: CMJsonField<String>,
) = concat(secondString, *strings.map { it.toDopeType() }.toTypedArray())

fun CMJsonField<String>.concat(
    secondString: TypeExpression<StringType>,
    vararg strings: CMJsonField<String>,
) = toDopeType().concat(secondString, *strings.map { it.toDopeType() }.toTypedArray())

fun CMJsonField<String>.concat(
    secondString: CMJsonField<String>,
    vararg strings: TypeExpression<StringType>,
) = toDopeType().concat(secondString.toDopeType(), *strings)

fun CMJsonField<String>.concat(
    secondString: TypeExpression<StringType>,
    vararg strings: TypeExpression<StringType>,
) = toDopeType().concat(secondString, *strings)

fun TypeExpression<StringType>.concat(
    secondString: CMJsonField<String>,
    vararg strings: TypeExpression<StringType>,
) = concat(secondString.toDopeType(), *strings)

fun String.concat(
    secondString: CMJsonField<String>,
    vararg strings: CMJsonField<String>,
) = toDopeType().concat(secondString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun String.concat(
    secondString: String,
    vararg strings: CMJsonField<String>,
) = toDopeType().concat(secondString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun CMJsonField<String>.concat(
    secondString: String,
    vararg strings: CMJsonField<String>,
) = toDopeType().concat(secondString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun CMJsonField<String>.concat(
    secondString: String,
    vararg strings: String,
) = toDopeType().concat(secondString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun CMJsonField<String>.concat(
    secondString: CMJsonField<String>,
    vararg strings: String,
) = toDopeType().concat(secondString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun String.concat(
    secondString: CMJsonField<String>,
    vararg strings: String,
) = toDopeType().concat(secondString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun CMJsonField<String>.concat(
    secondString: String,
    vararg strings: TypeExpression<StringType>,
) = toDopeType().concat(secondString.toDopeType(), *strings)

fun CMJsonField<String>.concat(
    secondString: TypeExpression<StringType>,
    vararg strings: String,
) = toDopeType().concat(secondString, *strings.map { it.toDopeType() }.toTypedArray())

fun String.concat(
    secondString: TypeExpression<StringType>,
    vararg strings: CMJsonField<String>,
) = toDopeType().concat(secondString, *strings.map { it.toDopeType() }.toTypedArray())

fun TypeExpression<StringType>.concat(
    secondString: String,
    vararg strings: CMJsonField<String>,
) = concat(secondString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun String.concat(
    secondString: CMJsonField<String>,
    vararg strings: TypeExpression<StringType>,
) = toDopeType().concat(secondString.toDopeType(), *strings)

fun TypeExpression<StringType>.concat(
    secondString: CMJsonField<String>,
    vararg strings: String,
) = concat(secondString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())
