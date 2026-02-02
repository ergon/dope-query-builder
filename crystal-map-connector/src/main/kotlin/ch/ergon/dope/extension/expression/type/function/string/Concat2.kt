package ch.ergon.dope.extension.expression.type.function.string

import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.function.string.concat2
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.StringType
import com.schwarz.crystalapi.schema.CMJsonField

fun CMJsonField<String>.concat2(
    string: CMJsonField<String>,
    vararg strings: CMJsonField<String>,
) = toDopeType().concat2(string.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun TypeExpression<StringType>.concat2(
    string: CMJsonField<String>,
    vararg strings: CMJsonField<String>,
) = concat2(string.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun TypeExpression<StringType>.concat2(
    string: TypeExpression<StringType>,
    vararg strings: CMJsonField<String>,
) = concat2(string, *strings.map { it.toDopeType() }.toTypedArray())

fun CMJsonField<String>.concat2(
    string: TypeExpression<StringType>,
    vararg strings: CMJsonField<String>,
) = toDopeType().concat2(string, *strings.map { it.toDopeType() }.toTypedArray())

fun CMJsonField<String>.concat2(
    string: CMJsonField<String>,
    vararg strings: TypeExpression<StringType>,
) = toDopeType().concat2(string.toDopeType(), *strings)

fun CMJsonField<String>.concat2(
    string: TypeExpression<StringType>,
    vararg strings: TypeExpression<StringType>,
) = toDopeType().concat2(string, *strings)

fun TypeExpression<StringType>.concat2(
    string: CMJsonField<String>,
    vararg strings: TypeExpression<StringType>,
) = concat2(string.toDopeType(), *strings)

fun String.concat2(
    string: CMJsonField<String>,
    vararg strings: CMJsonField<String>,
) = toDopeType().concat2(string.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun String.concat2(
    string: String,
    vararg strings: CMJsonField<String>,
) = toDopeType().concat2(string.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun CMJsonField<String>.concat2(
    string: String,
    vararg strings: CMJsonField<String>,
) = toDopeType().concat2(string.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun CMJsonField<String>.concat2(
    string: String,
    vararg strings: String,
) = toDopeType().concat2(string.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun CMJsonField<String>.concat2(
    string: CMJsonField<String>,
    vararg strings: String,
) = toDopeType().concat2(string.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun String.concat2(
    string: CMJsonField<String>,
    vararg strings: String,
) = toDopeType().concat2(string.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun CMJsonField<String>.concat2(
    string: String,
    vararg strings: TypeExpression<StringType>,
) = toDopeType().concat2(string.toDopeType(), *strings)

fun CMJsonField<String>.concat2(
    string: TypeExpression<StringType>,
    vararg strings: String,
) = toDopeType().concat2(string, *strings.map { it.toDopeType() }.toTypedArray())

fun String.concat2(
    string: TypeExpression<StringType>,
    vararg strings: CMJsonField<String>,
) = toDopeType().concat2(string, *strings.map { it.toDopeType() }.toTypedArray())

fun TypeExpression<StringType>.concat2(
    string: String,
    vararg strings: CMJsonField<String>,
) = concat2(string.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())

fun String.concat2(
    string: CMJsonField<String>,
    vararg strings: TypeExpression<StringType>,
) = toDopeType().concat2(string.toDopeType(), *strings)

fun TypeExpression<StringType>.concat2(
    string: CMJsonField<String>,
    vararg strings: String,
) = concat2(string.toDopeType(), *strings.map { it.toDopeType() }.toTypedArray())
