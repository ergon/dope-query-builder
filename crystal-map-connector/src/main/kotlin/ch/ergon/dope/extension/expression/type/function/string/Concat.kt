package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.string.concat
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun concat(
    firstString: CMJsonField<String>,
    secondString: CMJsonField<String>,
    vararg strings: CMJsonField<String>,
) = concat(firstString.toDopeType(), secondString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat(
    firstString: TypeExpression<StringType>,
    secondString: CMJsonField<String>,
    vararg strings: CMJsonField<String>,
) = concat(firstString, secondString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat(
    firstString: TypeExpression<StringType>,
    secondString: TypeExpression<StringType>,
    vararg strings: CMJsonField<String>,
) = concat(firstString, secondString, *strings.map { it.toDopeType() }.toTypedArray())

fun concat(
    firstString: CMJsonField<String>,
    secondString: TypeExpression<StringType>,
    vararg strings: CMJsonField<String>,
) = concat(firstString.toDopeType(), secondString, *strings.map { it.toDopeType() }.toTypedArray())

fun concat(
    firstString: CMJsonField<String>,
    secondString: CMJsonField<String>,
    vararg strings: TypeExpression<StringType>,
) = concat(firstString.toDopeType(), secondString.toDopeType(), *strings)

fun concat(
    firstString: CMJsonField<String>,
    secondString: TypeExpression<StringType>,
    vararg strings: TypeExpression<StringType>,
) = concat(firstString.toDopeType(), secondString, *strings)

fun concat(
    firstString: TypeExpression<StringType>,
    secondString: CMJsonField<String>,
    vararg strings: TypeExpression<StringType>,
) = concat(firstString, secondString.toDopeType(), *strings)

fun concat(
    firstString: String,
    secondString: CMJsonField<String>,
    vararg strings: CMJsonField<String>,
) = concat(firstString.toDopeType(), secondString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat(
    firstString: String,
    secondString: String,
    vararg strings: CMJsonField<String>,
) = concat(firstString.toDopeType(), secondString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat(
    firstString: CMJsonField<String>,
    secondString: String,
    vararg strings: CMJsonField<String>,
) = concat(firstString.toDopeType(), secondString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat(
    firstString: CMJsonField<String>,
    secondString: String,
    vararg strings: String,
) = concat(firstString.toDopeType(), secondString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat(
    firstString: CMJsonField<String>,
    secondString: CMJsonField<String>,
    vararg strings: String,
) = concat(firstString.toDopeType(), secondString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat(
    firstString: String,
    secondString: CMJsonField<String>,
    vararg strings: String,
) = concat(firstString.toDopeType(), secondString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat(
    firstString: CMJsonField<String>,
    secondString: String,
    vararg strings: TypeExpression<StringType>,
) = concat(firstString.toDopeType(), secondString.toDopeType(), *strings)

fun concat(
    firstString: CMJsonField<String>,
    secondString: TypeExpression<StringType>,
    vararg strings: String,
) = concat(firstString.toDopeType(), secondString, *strings.map { it.toDopeType() }.toTypedArray())

fun concat(
    firstString: String,
    secondString: TypeExpression<StringType>,
    vararg strings: CMJsonField<String>,
) = concat(firstString.toDopeType(), secondString, *strings.map { it.toDopeType() }.toTypedArray())

fun concat(
    firstString: TypeExpression<StringType>,
    secondString: String,
    vararg strings: CMJsonField<String>,
) = concat(firstString, secondString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat(
    firstString: String,
    secondString: CMJsonField<String>,
    vararg strings: TypeExpression<StringType>,
) = concat(firstString.toDopeType(), secondString.toDopeType(), *strings)

fun concat(
    firstString: TypeExpression<StringType>,
    secondString: CMJsonField<String>,
    vararg strings: String,
) = concat(firstString, secondString.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())
