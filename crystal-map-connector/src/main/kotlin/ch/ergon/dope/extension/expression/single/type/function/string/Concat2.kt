package ch.ergon.dope.extension.expression.single.type.function.string

import ch.ergon.dope.resolvable.expression.single.type.TypeExpression
import ch.ergon.dope.resolvable.expression.single.type.function.string.concat2
import ch.ergon.dope.resolvable.expression.single.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun concat2(
    separator: CMJsonField<String>,
    string: CMJsonField<String>,
    vararg strings: CMJsonField<String>,
) = concat2(separator.toDopeType(), string.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat2(
    separator: TypeExpression<StringType>,
    string: CMJsonField<String>,
    vararg strings: CMJsonField<String>,
) = concat2(separator, string.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat2(
    separator: TypeExpression<StringType>,
    string: TypeExpression<StringType>,
    vararg strings: CMJsonField<String>,
) = concat2(separator, string, *strings.map { it.toDopeType() }.toTypedArray())

fun concat2(
    separator: CMJsonField<String>,
    string: TypeExpression<StringType>,
    vararg strings: CMJsonField<String>,
) = concat2(separator.toDopeType(), string, *strings.map { it.toDopeType() }.toTypedArray())

fun concat2(
    separator: CMJsonField<String>,
    string: CMJsonField<String>,
    vararg strings: TypeExpression<StringType>,
) = concat2(separator.toDopeType(), string.toDopeType(), *strings)

fun concat2(
    separator: CMJsonField<String>,
    string: TypeExpression<StringType>,
    vararg strings: TypeExpression<StringType>,
) = concat2(separator.toDopeType(), string, *strings)

fun concat2(
    separator: TypeExpression<StringType>,
    string: CMJsonField<String>,
    vararg strings: TypeExpression<StringType>,
) = concat2(separator, string.toDopeType(), *strings)

fun concat2(
    separator: String,
    string: CMJsonField<String>,
    vararg strings: CMJsonField<String>,
) = concat2(separator.toDopeType(), string.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat2(
    separator: String,
    string: String,
    vararg strings: CMJsonField<String>,
) = concat2(separator.toDopeType(), string.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat2(
    separator: CMJsonField<String>,
    string: String,
    vararg strings: CMJsonField<String>,
) = concat2(separator.toDopeType(), string.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat2(
    separator: CMJsonField<String>,
    string: String,
    vararg strings: String,
) = concat2(separator.toDopeType(), string.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat2(
    separator: CMJsonField<String>,
    string: CMJsonField<String>,
    vararg strings: String,
) = concat2(separator.toDopeType(), string.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat2(
    separator: String,
    string: CMJsonField<String>,
    vararg strings: String,
) = concat2(separator.toDopeType(), string.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat2(
    separator: CMJsonField<String>,
    string: String,
    vararg strings: TypeExpression<StringType>,
) = concat2(separator.toDopeType(), string.toDopeType(), *strings)

fun concat2(
    separator: CMJsonField<String>,
    string: TypeExpression<StringType>,
    vararg strings: String,
) = concat2(separator.toDopeType(), string, *strings.map { it.toDopeType() }.toTypedArray())

fun concat2(
    separator: String,
    string: TypeExpression<StringType>,
    vararg strings: CMJsonField<String>,
) = concat2(separator.toDopeType(), string, *strings.map { it.toDopeType() }.toTypedArray())

fun concat2(
    separator: TypeExpression<StringType>,
    string: String,
    vararg strings: CMJsonField<String>,
) = concat2(separator, string.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun concat2(
    separator: String,
    string: CMJsonField<String>,
    vararg strings: TypeExpression<StringType>,
) = concat2(separator.toDopeType(), string.toDopeType(), *strings)

fun concat2(
    separator: TypeExpression<StringType>,
    string: CMJsonField<String>,
    vararg strings: String,
) = concat2(separator, string.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())
